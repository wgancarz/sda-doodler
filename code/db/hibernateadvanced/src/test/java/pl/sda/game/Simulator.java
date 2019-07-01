package pl.sda.game;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sda.configuration.JdbcConnectionManager;
import pl.sda.configuration.PropertyReader;
import pl.sda.configuration.TestUtil;
import pl.sda.domain.Event;
import pl.sda.domain.Player;
import pl.sda.domain.Stadium;
import pl.sda.domain.Team;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class Simulator {

    private SessionFactory sessionFactory;

    @BeforeEach
    void setUp() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();
        final Metadata metadata = new MetadataSources(registry)
                .buildMetadata();
        sessionFactory = metadata.buildSessionFactory();
    }

    @AfterEach
    void tearDown() throws Exception {
        sessionFactory.close();

        JdbcConnectionManager jdbcConnectionManager = new JdbcConnectionManager(PropertyReader.loadConfiguration());
        TestUtil.cleanUpDatabase(jdbcConnectionManager);
    }

    @Test
    void playTheGame() {
        Team fcBarcelona = createFCBarcelona();
        Team realMadryt = createRealMadryt();

        Session setupMatch = sessionFactory.openSession();
        setupMatch.beginTransaction();
        setupMatch.persist(fcBarcelona);
        setupMatch.persist(realMadryt);
        setupMatch.getTransaction().commit();
        setupMatch.close();

        Session scoreAGoal = sessionFactory.openSession();
        scoreAGoal.beginTransaction();

        Query<Player> goalQuery = scoreAGoal.createQuery("from Player where lastName = :playerOne or lastName = :playerTwo", Player.class);
        goalQuery.setParameter("playerOne", "Messi");
        goalQuery.setParameter("playerTwo", "Cillessen");
        List<Player> players = goalQuery.list();

        Event goal = new Event();
        goal.setRecordedAt(LocalDateTime.now());
        goal.setType("GOAL!");
        goal.setValue("1");
        players.forEach(goal::addPlayer);

        scoreAGoal.persist(goal);
        scoreAGoal.getTransaction().commit();
        scoreAGoal.close();

        Session verify = sessionFactory.openSession();
        List<Team> teams = verify.createQuery("FROM Team", Team.class).list();
        assertThat(teams).hasSize(2);
        verify.close();
    }

    private Team createRealMadryt() {
        Team rm = createSampleTeam("RM", "Madryt", "Concha Espina");
        rm.addPlayer(createPlayer("Gareth", "Bale", "striker"));
        rm.addPlayer(createPlayer("Sergio", "Ramos", "defender"));
        rm.addPlayer(createPlayer("Thibaut", "Courtois", "goalkeeper"));
        return rm;
    }

    private Team createFCBarcelona() {
        Team fcb = createSampleTeam("FCB", "Barcelona", "Avinguda Aristides Maillol");
        fcb.addPlayer(createPlayer("Lionel", "Messi", "striker"));
        fcb.addPlayer(createPlayer("Gerard", "Pique", "defender"));
        fcb.addPlayer(createPlayer("Jasper", "Cillessen", "goalkeeper"));
        return fcb;
    }

    private Team createSampleTeam(String teamName, String city, String street) {
        Team team = new Team();
        team.setName(teamName);

        Stadium stadium = createStadium(city, street);
        team.addStadium(stadium);

        return team;
    }

    private Stadium createStadium(String city, String street) {
        Stadium stadium = new Stadium();
        stadium.setCity(city);
        stadium.setStreet(street);
        return stadium;
    }

    private Player createPlayer(String firstName, String lastName, String position) {
        Player player = new Player();
        player.setFirstName(firstName);
        player.setLastName(lastName);
        player.setPosition(position);
        return player;
    }
}

package hospital.demo;

import hospital.model.Disease;
import hospital.model.Patient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PatientGenerator {
    private static Integer ANGER_LEVEL_MAX = 10;
    Random random = new Random();

    public Patient get() {
        Patient patient = new Patient();

        patient.setFirstName(getRandomFirstName());
        patient.setLastName(getRandomLastName());
        patient.setAngerLevel(getRandomAngerLevel());
        patient.setDisease(getRandomDisease());

        return patient;
    }

    private String getRandomFirstName() {
        return getRandomLineFromResource("firstnames");
    }

    private String getRandomLastName() {
        return getRandomLineFromResource("lastnames");
    }

    private String getRandomLineFromResource(String resourcePath) {
        final List<String> lines = getLinesFromResource(resourcePath);

        if (lines.isEmpty()) return null;

        Integer randomIndex = random.nextInt(lines.size());
        return lines.get(randomIndex);
    }

    private Integer getRandomAngerLevel() {
        return random.nextInt(ANGER_LEVEL_MAX);
    }

    private Disease getRandomDisease() {
        Integer randomIndex = random.nextInt(Disease.values().length);

        return Disease.values()[randomIndex];
    }

    private List<String> getLinesFromResource(String resourcePath) {
        List<String> stringList = null;

        try {
            Path path = Paths.get(this.getClass().getClassLoader().getResource(resourcePath).toURI());
            stringList = Files.lines(path).collect(Collectors.toList());
        } catch (URISyntaxException | IOException e) {
            System.err.println("Error reading file.");
            e.printStackTrace();
        }

        return stringList;
    }
}

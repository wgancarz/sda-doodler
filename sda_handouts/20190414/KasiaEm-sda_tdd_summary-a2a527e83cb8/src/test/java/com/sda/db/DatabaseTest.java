package com.sda.db;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseTest {

    private Database database;
    private List<User> dbUsers;

    @Mock
    private Database dbMock;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        dbUsers = Arrays.asList(
                new User("u1", "pass"),
                new User("u2", "pass"),
                new User("u3", "pass"));
        database = new Database(dbUsers);
    }

    @Test
    public void testAddUser() {
        // given
        int dbPreviousSize = database.findAll().size();
        User user = new User("u4", "pass");
        // when
        database.addUser(user);
        //then
        assertThat(database.findAll().size()).isEqualTo(dbPreviousSize + 1);
        assertThat(database.findUser("u4")).isEqualToComparingFieldByFieldRecursively(user);
    }

    @Test
    public void testAddUserWithMock() {
        // test with mock
        // given
        User user = new User("u4", "pass");
        // when
        dbMock.addUser(user);
        // then
        verify(dbMock, times(1)).addUser(user);
    }

    @Test
    public void testFindUser() {
        // given
        User expected = new User("u4", "pass");
        // when
        database.addUser(expected);
        // then
        assertThat(database.findUser("u4")).isEqualToComparingFieldByFieldRecursively(expected);
    }

    @Test
    public void testFindUserWithMock() {
        // test with mock
        // given
        User expected = new User("u4", "pass");
        when(dbMock.findUser(anyString())).thenReturn(expected);
        // when
        User actualMocked = dbMock.findUser("login");
        // then
        assertThat(actualMocked).isNotNull().isEqualToComparingFieldByFieldRecursively(expected);
    }

    @Test
    public void testRemoveUser() {
        // given
        int dbPreviousSize = database.findAll().size();
        // when
        database.removeUser("u1");
        // then
        assertThat(database.findUser("u1")).isNull();
        assertThat(database.findAll().size()).isEqualTo(dbPreviousSize - 1);
    }

    @Test
    public void testAddUserNullPointerException() {
        thrown.expect(NullPointerException.class);
        database.addUser(null);
    }

    @Test
    public void testAddUserUserExistsException() {
        thrown.expect(UserExistsException.class);
        thrown.expectMessage("User already exists.");
        database.addUser(new User("u1", "pass"));
    }

    @Test
    public void testFindByLogin() {
        // given
        String login = "u";
        String loginNotExisting = "5";
        //when
        List<User> usersByLogin = database.findByLogin(login);
        //then

        assertThat(database.findByLogin(loginNotExisting).size()).isEqualTo(0);

        assertThat(usersByLogin.size()).isEqualTo(3);
        assertThat(usersByLogin)
                .extracting(user -> user.getLogin())
                .allMatch(userLogin -> userLogin.contains(login));

        for(User u:usersByLogin){
            assertThat(u.getLogin().contains(login)).isTrue();
        }
    }

    @Test
    public void testFindByLoginNullPointerException() {
        thrown.expect(NullPointerException.class);
        database.findByLogin(null);
    }

    @Test
    public void testModifyUser() {
        // given
        User modifiedUser = new User("u1", "pass2");
        User oldUser = database.findUser(modifiedUser.getLogin());
        // when
        database.modifyUser(modifiedUser);
        // then
        User newUser = database.findUser(modifiedUser.getLogin());
        assertThat(newUser).isNotNull().isEqualToComparingFieldByFieldRecursively(modifiedUser);
        assertThat(newUser.getPassword()).isNotEqualTo(oldUser.getPassword());
    }

    @Test
    public void testFindAll() {
        assertThat(database.findAll()).isNotNull().containsAll(dbUsers);
    }

    @Test
    public void testFindAllWithMock(){
        // test with mock
        // given
        User expected = new User("u4", "pass");
        List<User> userListMock = mock(List.class);
        when(dbMock.findAll()).thenReturn(userListMock);
        when(userListMock.get(anyInt())).thenReturn(expected);
        // when
        User userReturned = dbMock.findAll().get(1);
        // then
        assertThat(userReturned).isNotNull().isEqualToComparingFieldByFieldRecursively(expected);
    }

}
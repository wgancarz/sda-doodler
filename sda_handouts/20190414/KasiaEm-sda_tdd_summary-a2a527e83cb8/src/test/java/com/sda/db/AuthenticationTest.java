package com.sda.db;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthenticationTest {
    private Database database;
    private Authentication authentication;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        List<User> users = Arrays.asList(
                new User("u1", "pass"),
                new User("u2", "pass"),
                new User("u3", "pass"));
        database = new Database(users);
        authentication = new Authentication(database);
    }

    @Test
    public void testLogin() {
        // given
        User user = new User("u1", "pass");
        // when
        authentication.login(user);
        // then
        assertThat(authentication.getLoggedIn()).isNotNull().isEqualTo(user.getLogin());
    }

    @Test
    public void testLoginUserNotFoundException() {
        User user = new User("u123", "pass");
        thrown.expect(UserNotFoundException.class);
        authentication.login(user);
    }

    @Test
    public void testLoginAuthenticationException() {
        User user = new User("u1", "pass123234134");
        thrown.expect(AuthenticationException.class);
        authentication.login(user);
    }

    @Test
    public void testLogout() {
        // when
        authentication.logout();
        // then
        assertThat(authentication.getLoggedIn()).isNull();
    }
}
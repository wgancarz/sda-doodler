package com.sda.db;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class AuthenticationMockTest {
    @Mock
    Database database;

    @InjectMocks
    Authentication authentication;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup(){
        User userOK = new User("login", "pass");
        when(database.findUser("notexisting")).thenReturn(null);
        when(database.findUser("login")).thenReturn(userOK);
    }

    @Test
    public void loginUserNotFoundException(){
        thrown.expect(UserNotFoundException.class);
        User user = new User("notexisting", "pass");
        authentication.login(user);
    }

    @Test
    public void loginAuthenticationException(){
        thrown.expect(AuthenticationException.class);
        User user = new User("login", "pass123134");
        authentication.login(user);
    }

    @Test
    public void login(){
        User user = new User("login", "pass");
        authentication.login(user);
        assertThat(authentication.getLoggedIn()).isEqualTo(user.getLogin());
    }

    @Test
    public void logout(){
        authentication.logout();
        assertThat(authentication.getLoggedIn()).isNull();
    }



}

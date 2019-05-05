package com.sda.db;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationTestWithMock {
    @Mock
    private Database dbMock;
    @InjectMocks
    private Authentication authentication;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() {
        User userExisting = new User("login", "password");
        when(dbMock.findUser("login")).thenReturn(userExisting);
        //when(dbMock.findUser("fail")).thenReturn(null);
    }

    @Test
    public void testLogin() {
        // given
        User userOK = new User("login", "password");
        // when
        authentication.login(userOK);
        // then
        assertThat(authentication.getLoggedIn()).isNotNull().isEqualTo(userOK.getLogin());
    }

    @Test
    public void testLoginUserNotFoundException() {
        // given
        User userFail = new User("fail", "password");

        thrown.expect(UserNotFoundException.class);
        authentication.login(userFail);
    }

    @Test
    public void testLoginAuthenticationException() {
        // given
        User userFail = new User("login", "fail");

        thrown.expect(AuthenticationException.class);
        authentication.login(userFail);
    }

    @Test
    public void testLogout() {
        // when
        authentication.logout();
        // then
        assertThat(authentication.getLoggedIn()).isNull();
    }
}

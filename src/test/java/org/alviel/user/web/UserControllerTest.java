package org.alviel.user.web;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.alviel.user.UserIdTest;
import org.alviel.user.domain.User;
import org.alviel.user.domain.UserId;
import org.alviel.user.domain.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class UserControllerTest {

    TestRestTemplate restTemplate;

    @MockBean
    private UserService userService;
    @LocalServerPort int port;
    private URL base;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        base = new URL("http://localhost:" + port + "/users");
    }

    @Test
    public void givenCreatingUser_whenInvalidCredentials_shouldFail() throws IllegalStateException {
        restTemplate = new TestRestTemplate("user", "wrongpassword");
        givenUserCreated();

        ResponseEntity<UserIdTest> response =
            restTemplate.postForEntity(base.toString(), new CreateUserRequest(), UserIdTest.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void givenCreatingUser_whenEmptyPayload_shouldFail() throws IllegalStateException {
        restTemplate = new TestRestTemplate("user", "Abc.123$");
        givenUserCreated();

        ResponseEntity<UserIdTest> response =
            restTemplate.postForEntity(base.toString(), new CreateUserRequest(), UserIdTest.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void givenCreatingUser_whenGoodInput_shouldSucceed() throws IllegalStateException {
        restTemplate = new TestRestTemplate("user", "Abc.123$");
        givenUserCreated();

        ResponseEntity<UserIdTest> response =
            restTemplate.postForEntity(base.toString(), new CreateUserRequest("name", "username", "email@lolo.cl", "1312332"), UserIdTest.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().getId(), 1);
    }

    @Test
    public void givenListAllUsers_whenGoodInput_shouldSucceed() throws IllegalStateException {
        restTemplate = new TestRestTemplate(); // Request are without user and password
        givenUsers();

        ResponseEntity<User[]> response =
            restTemplate.getForEntity(base.toString(), User[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody()[0].getId(), 10);
    }

    private void givenUserCreated() {
        when(userService.create(any())).thenReturn(new UserId(1));
    }

    private void givenUsers() {
        User u = new User();
        u.setId(10);
        when(userService.all()).thenReturn(List.of(u));
    }


}
package org.alviel.user;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.alviel.user.domain.UserId;
import org.alviel.user.web.CreateUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Profile("local")
class UserApplicationTest {

    TestRestTemplate restTemplate;
    URL base;
    @LocalServerPort int port;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        base = new URL("http://localhost:" + port + "/users");
    }

    @Test
    public void whenLoggedUserRequestsHomePage_ThenSuccess()
        throws IllegalStateException {
        restTemplate = new TestRestTemplate("user", "Abc.123$");
        ResponseEntity<UserIdTest> response =
            restTemplate.postForEntity(base.toString(), givenCreateUserRequest(), UserIdTest.class);

        System.out.println("response = " + response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void whenUserWithWrongCredentials_thenUnauthorizedPage() {

        restTemplate = new TestRestTemplate("user", "wrongpassword");
        ResponseEntity<UserId> response =
            restTemplate.postForEntity(base.toString(), givenCreateUserRequest(), UserId.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    private CreateUserRequest givenCreateUserRequest() {
        return new CreateUserRequest("name", "useranme", "email@lolo.cl", "134183413");
    }
}
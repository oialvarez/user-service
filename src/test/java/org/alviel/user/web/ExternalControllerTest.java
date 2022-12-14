package org.alviel.user.web;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import org.alviel.user.domain.ItemService;
import org.alviel.user.domain.ItemsByCustomer;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ExternalControllerTest {

    TestRestTemplate restTemplate;

    @MockBean
    private ItemService itemService;
    @LocalServerPort int port;
    private URL base;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        base = new URL("http://localhost:" + port + "/external/{param}");
    }

    @Test
    public void testGetParam() throws IllegalStateException {
        restTemplate = new TestRestTemplate();
        givenItems();

        ResponseEntity<ItemsByCustomer> response =
            restTemplate.postForEntity(base.toString(), null, ItemsByCustomer.class, "id");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().getResult().get("registerCount"), 3);
    }

    private void givenItems() {
        ItemsByCustomer result = new ItemsByCustomer();
        result.setResult(Map.of("registerCount", 3));
        when(itemService.countItems(anyString())).thenReturn(result);
    }


}
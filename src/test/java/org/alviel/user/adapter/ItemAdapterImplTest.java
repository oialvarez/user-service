package org.alviel.user.adapter;

import java.util.Optional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class ItemAdapterImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Cipher cipher;

    @InjectMocks
    private ItemAdapterImpl itemAdapter;

    AutoCloseable autoCloseable;

    @BeforeEach
    void setup() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @SneakyThrows
    @AfterEach
    void tearDown() {
        autoCloseable.close();
    }

    @Test
    void givenGettingItems_whenProviderFails_shouldFail() {
        givenFailedToGetItems();
        givenEncrypt();

        String message = assertThrows(ExternalCommunicationException.class, this::whenGettingItems)
            .getMessage();

        assertThat(message).isEqualTo("500 INTERNAL_SERVER_ERROR \"Error found getting response from external API\"; nested exception is org.springframework.web.client.RestClientException: Failed to get Items");
    }

    @Test
    void givenGettingItems_whenProviderAvailable_shouldSucceed() {
        givenGetItems();
        givenEncrypt();

        assertDoesNotThrow(this::whenGettingItems);
    }

    private void givenEncrypt() {
        when(cipher.encrypt(anyString()))
            .thenReturn("logico");
    }

    private void givenFailedToGetItems() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(ItemResult.class), anyString()))
            .thenThrow(new RestClientException("Failed to get Items"));
    }

    private void givenGetItems() {
        ItemResult result = new ItemResult();
        result.setResponseCode(200);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(ItemResult.class), anyString()))
            .thenReturn(ResponseEntity.of(Optional.of(result)));
    }

    private void whenGettingItems() {
        itemAdapter.getItems("id");
    }

}
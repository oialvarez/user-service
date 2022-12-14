package org.alviel.user.adapter;

import javax.naming.CommunicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ItemAdapterImpl implements ItemAdapter {

    @Autowired
    private Cipher cipher;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ItemResult getItems(String id) {
        try {
            ResponseEntity<ItemResult> exchange = restTemplate.exchange("https://my.api.mockaroo.com/test-tecnico/search/{param}",
                HttpMethod.GET,
                getHeaders(),
                ItemResult.class,
                cipher.encrypt(id));
            return exchange.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExternalCommunicationException("Error found getting response from external API", e);
        }
    }

    private HttpEntity<Object> getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set("X-API-Key", "f2f719e0");
        return new HttpEntity<>(headers);
    }
}

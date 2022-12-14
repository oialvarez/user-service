package org.alviel.user.domain;

import java.util.List;
import lombok.SneakyThrows;
import org.alviel.user.adapter.ItemAdapter;
import org.alviel.user.adapter.ItemResult;
import org.alviel.user.adapter.ItemResult.Item;
import org.alviel.user.adapter.ItemResult.Result;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ItemServiceImplTest {

    @Mock
    private ItemAdapter itemAdapter;

    @InjectMocks
    private ItemServiceImpl itemService;

    private AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @SneakyThrows
    @AfterEach
    void tearDown() {
        openMocks.close();
    }

    @Test
    void testGetItems() {
        givenItems();

        ItemsByCustomer ibc = itemService.countItems("id");

        assertThat(ibc.getResult().get("registerCount")).isEqualTo(1);
    }

    private void givenItems() {
        ItemResult result = new ItemResult();
        result.setResponseCode(200);
        result.setDescription("Description");
        Result r = new Result();
        result.setResult(r);
        Item i = new Item();
        r.setItems(List.of(i));
        i.setName("Name");
        when(itemAdapter.getItems(anyString())).thenReturn(result);
    }
}
package org.alviel.user.domain;

import org.alviel.user.adapter.ItemAdapter;
import org.alviel.user.adapter.ItemResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemAdapter itemAdapter;

    @Override
    public ItemsByCustomer countItems(String customerId) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ItemResult items = itemAdapter.getItems(customerId);
        stopWatch.stop();

        ItemsByCustomer itemsByCustomer = new ItemsByCustomer();
        itemsByCustomer.setResponseCode(items.getResponseCode());
        itemsByCustomer.setDescription(items.getDescription());
        itemsByCustomer.setElapsedTime(stopWatch.getLastTaskTimeMillis());
        itemsByCustomer.addResult("registerCount", items.getItemsCount());
        return itemsByCustomer;
    }
}

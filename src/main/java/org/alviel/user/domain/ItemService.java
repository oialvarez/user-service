package org.alviel.user.domain;

public interface ItemService {
    ItemsByCustomer countItems(String customerId);
}

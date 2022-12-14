package org.alviel.user.adapter;

import java.util.List;
import lombok.Data;

@Data
public class ItemResult {

    private Integer responseCode;
    private String description;
    private Result result;

    public Integer getItemsCount() {
        return result.getItems().size();
    }

    @Data
    public static class Result {

        private List<Item> items;
    }

    @Data
    public static class Item {

        private String name;
        private Detail detail;
    }

    @Data
    private static class Detail {

        private String email;
        private String phone;
    }
}

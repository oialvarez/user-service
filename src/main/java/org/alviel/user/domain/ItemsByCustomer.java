package org.alviel.user.domain;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class ItemsByCustomer {

    private Integer responseCode;
    private String description;
    private Long elapsedTime;
    private Map<String, Object> result;

    public Object addResult(String key, Object value) {
        if (result == null) {
            result = new HashMap<>();
        }
        return result.put(key, value);
    }
}

package org.alviel.user.web;

import lombok.RequiredArgsConstructor;
import org.alviel.user.domain.ItemService;
import org.alviel.user.domain.ItemsByCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/external")
@RequiredArgsConstructor
public class ExternalController {

    private final ItemService itemService;

    @PostMapping("/{param}")
    ItemsByCustomer getParam(@PathVariable String param) {
        return itemService.countItems(param);
    }

}

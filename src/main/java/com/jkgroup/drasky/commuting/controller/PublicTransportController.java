package com.jkgroup.drasky.commuting.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("commuting/public-transport")
public class PublicTransportController {

    @PostMapping("/intent/when-bus-arrives")
    public Object getBook(@RequestBody Object request) {
        var response = new Object();

        return response;
    }
}

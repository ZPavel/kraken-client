package com.zpavel.kraken.client.controller;

import com.zpavel.kraken.client.domain.AddOrderRequest;
import com.zpavel.kraken.client.service.KrakenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private KrakenService krakenService;

    @PostMapping
    public Object addOrder() {
        AddOrderRequest request = new AddOrderRequest();
        return krakenService.addOrder(request);
    }
}

package com.zpavel.kraken.client.controller;

import com.zpavel.kraken.client.domain.BalanceRequest;
import com.zpavel.kraken.client.domain.BalanceResponse;
import com.zpavel.kraken.client.service.KrakenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    @Autowired
    private KrakenService krakenService;

    @GetMapping
    public BalanceResponse getBalance() {
        return krakenService.getBalance(new BalanceRequest());
    }
}

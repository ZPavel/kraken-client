package com.zpavel.kraken.client.service;

import com.zpavel.kraken.client.ApiException;
import com.zpavel.kraken.client.domain.BalanceRequest;
import org.junit.jupiter.api.Test;

class KrakenServiceImplTest {

    private KrakenService service = new KrakenServiceImpl();
    @Test
    void getTicker() {
    }

    @Test
    void getBalance() {
        try {
            service.getBalance(new BalanceRequest());
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addOrder() {
    }
}
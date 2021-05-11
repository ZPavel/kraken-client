package com.zpavel.kraken.client.service;

import com.zpavel.kraken.client.ApiException;
import com.zpavel.kraken.client.domain.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class KrakenServiceImplTest {

    private KrakenService service = new KrakenServiceImpl();

    @Test
    void getAssetPairs() throws ApiException {
        AssetPairsResponse response = service.getAssetPairs();
        assertNotNull(response);
    }

    @Test
    void getTicker() {
    }

    @Test
    void getBalance() throws ApiException {
        BalanceResponse response = service.getBalance(new BalanceRequest());
        assertNotNull(response);
    }

    @Test
    void getOpenOrders() throws ApiException {
        OpenOrdersResponse response = service.getOpenOrders(new OpenOrdersRequest());
        assertNotNull(response);
    }

    @Test
    void addOrder() {
    }
}
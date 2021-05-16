package com.zpavel.kraken.client.service;

import com.zpavel.kraken.client.ApiException;
import com.zpavel.kraken.client.domain.*;
import com.zpavel.kraken.client.enums.OrderOperation;
import com.zpavel.kraken.client.enums.OrderType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class KrakenServiceImplTest {

    private KrakenService service = new KrakenServiceImpl();

    @Test
    public void getAssetPairs() throws ApiException {
        AssetPairsResponse response = service.getAssetPairs();
        assertNotNull(response);
    }

    @Test
    public void getTicker() {
    }

    @Test
    public void getBalance() throws ApiException {
        BalanceResponse response = service.getBalance(new BalanceRequest());
        assertNotNull(response);
    }

    @Test
    public void getOpenOrders() throws ApiException {
        OpenOrdersResponse response = service.getOpenOrders(new OpenOrdersRequest());
        assertNotNull(response);
    }

    @Test
    public void addOrder() throws ApiException {
        AddOrderRequest request = new AddOrderRequest(
                "DOTEUR",
                OrderOperation.buy,
                OrderType.LIMIT,
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(20),
                null
        );
        AddOrderResponse response = service.addOrder(request);
        assertNotNull(response);
    }

    @Test
    public void cancelOrder() throws ApiException {
        CancelOrderRequest request = new CancelOrderRequest("O6V7IT-KGHQH-DN4QUP");
        CancelOrderResponse response = service.cancelOrder(request);
        assertNotNull(response);
    }

    @Test
    public void cancelAll() throws ApiException {
        CancelAllRequest request = new CancelAllRequest();
        CancelAllResponse response = service.cancelAll(request);
        assertNotNull(response);
    }
}
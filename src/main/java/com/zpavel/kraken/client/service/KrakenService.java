package com.zpavel.kraken.client.service;

import com.zpavel.kraken.client.domain.*;

import java.util.List;

public interface KrakenService {
    KrakenTicker getTicker(List<String> pairs);

    BalanceResponse getBalance(BalanceRequest request);

    AddOrderResponse addOrder(AddOrderRequest request);
}

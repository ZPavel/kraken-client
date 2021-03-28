package com.zpavel.kraken.client.service;

import com.zpavel.kraken.client.ApiException;
import com.zpavel.kraken.client.domain.*;

import java.util.List;

public interface KrakenService {
    KrakenTicker getTicker(List<String> pairs) throws ApiException;

    BalanceResponse getBalance(BalanceRequest request) throws ApiException;

    AddOrderResponse addOrder(AddOrderRequest request);
}

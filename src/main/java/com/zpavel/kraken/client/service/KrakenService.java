package com.zpavel.kraken.client.service;

import com.zpavel.kraken.client.domain.AddOrderRequest;
import com.zpavel.kraken.client.domain.AddOrderResponse;
import com.zpavel.kraken.client.domain.BalanceRequest;
import com.zpavel.kraken.client.domain.BalanceResponse;

public interface KrakenService {
    BalanceResponse getBalance(BalanceRequest request);
    AddOrderResponse addOrder(AddOrderRequest request);
}

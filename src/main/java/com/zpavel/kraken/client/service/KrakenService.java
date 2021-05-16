package com.zpavel.kraken.client.service;

import com.zpavel.kraken.client.ApiException;
import com.zpavel.kraken.client.domain.*;

import java.util.List;

public interface KrakenService {
    AssetPairsResponse getAssetPairs() throws ApiException;

    KrakenTickerResponse getTicker(List<String> pairs) throws ApiException;

    BalanceResponse getBalance(BalanceRequest request) throws ApiException;

    OpenOrdersResponse getOpenOrders(OpenOrdersRequest request) throws ApiException;

    AddOrderResponse addOrder(AddOrderRequest request) throws ApiException;

    CancelOrderResponse cancelOrder(CancelOrderRequest request) throws ApiException;

    CancelAllResponse cancelAll(CancelAllRequest request) throws ApiException;
}

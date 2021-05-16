package com.zpavel.kraken.client.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CancelOrderRequest extends ApiRequest {
    private String txid;
}

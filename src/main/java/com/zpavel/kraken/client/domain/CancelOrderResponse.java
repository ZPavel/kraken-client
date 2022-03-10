package com.zpavel.kraken.client.domain;

import lombok.Data;

@Data
public class CancelOrderResponse extends ApiResponse {
    private CancelOrderResult result;

    @Data
    static class CancelOrderResult {
        private Integer count;
    }
}

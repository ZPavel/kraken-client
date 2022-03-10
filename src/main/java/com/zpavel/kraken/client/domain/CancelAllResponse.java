package com.zpavel.kraken.client.domain;

import lombok.Data;

@Data
public class CancelAllResponse extends ApiResponse {
    private CancelAllResult result;

    @Data
    static class CancelAllResult {
        private Integer count;
    }
}

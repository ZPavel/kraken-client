package com.zpavel.kraken.client.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class OpenOrdersResponse extends ApiResponse {
    private Map<String, Map<String, OpenOrderResult>> result;

    @Data
    public static class OpenOrderResult {
        private String status;
        private String opentm;
        private OpenOrderDescr descr;
        private BigDecimal vol;
    }

    @Data
    public static class OpenOrderDescr {
        private String pair;
        private String type;
        private String ordertype;
        private BigDecimal price;
        private BigDecimal price2;
        private String order;
    }
}

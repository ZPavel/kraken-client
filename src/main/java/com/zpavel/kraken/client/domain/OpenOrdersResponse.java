package com.zpavel.kraken.client.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class OpenOrdersResponse extends ApiResponse {
    @Data
    class OpenOrder {
        private String status;
        private String opentm;
        private OpenOrderDescr descr;
        private BigDecimal vol;
    }
    @Data
    class OpenOrderDescr {
        private String pair;
        private String type;
        private String ordertype;
        private BigDecimal price;
    }
    private Map<String, Map<String,OpenOrder>> result;
}

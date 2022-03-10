package com.zpavel.kraken.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
public class AddOrderResponse extends ApiResponse {
    private Map<String, BigDecimal> result;

    @Data
    public class AssetPair {
        private String altname;
        @JsonProperty("aclass_base")
        private String aclassBase;
        private String base;
        @JsonProperty("aclass_quote")
        private String aclassQuote;
        private String quote;
    }
}

package com.zpavel.kraken.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class AssetPairsResponse extends ApiResponse {
    private Map<String, AssetPairResult> result;

    @Data
    static class AssetPairResult {
        private String altname;
        @JsonProperty("aclass_base")
        private String aclassBase;
        private String base;
        @JsonProperty("aclass_quote")
        private String aclassQuote;
        private String quote;
    }
}

package com.zpavel.kraken.client.domain;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Map;

@Data
public class AssetPairsResponse extends ApiResponse {
    private Map<String, AssetPairResult> result;

    @Data
    public class AssetPairResult {
        private String altname;
        @SerializedName("aclass_base")
        private String aclassBase;
        private String base;
        @SerializedName("aclass_quote")
        private String aclassQuote;
        private String quote;
    }
}

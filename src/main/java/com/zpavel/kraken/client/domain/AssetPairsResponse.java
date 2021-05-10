package com.zpavel.kraken.client.domain;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AssetPairsResponse extends ApiResponse {
    @Data
    class AssetPair {
        private String altname;
        @SerializedName("aclass_base")
        private String aclassBase;
        private String base;
        @SerializedName("aclass_quote")
        private String aclassQuote;
        private String quote;
    }
    private List<String> error;
    private Map<String, AssetPair> result;
}

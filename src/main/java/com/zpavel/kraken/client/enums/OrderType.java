package com.zpavel.kraken.client.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OrderType {
    @JsonProperty("market")
    MARKET,
    @JsonProperty("limit")
    LIMIT,
    @JsonProperty("stop-loss")
    STOP_LOSS,
    @JsonProperty("take-profit")
    TAKE_PROFIT,
    @JsonProperty("stop-loss-limit")
    STOP_LOSS_LIMIT,
    @JsonProperty("take-profit-limit")
    TAKE_PROFIT_LIMIT;
}

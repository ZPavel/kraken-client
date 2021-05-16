package com.zpavel.kraken.client.enums;

import com.google.gson.annotations.SerializedName;

public enum OrderType {
    @SerializedName("market")
    MARKET,
    @SerializedName("limit")
    LIMIT,
    @SerializedName("stop-loss")
    STOP_LOSS,
    @SerializedName("take-profit")
    TAKE_PROFIT,
    @SerializedName("stop-loss-limit")
    STOP_LOSS_LIMIT,
    @SerializedName("take-profit-limit")
    TAKE_PROFIT_LIMIT;
}

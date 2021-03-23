package com.zpavel.kraken.client.domain;

import com.zpavel.kraken.client.enums.OrderPair;
import com.zpavel.kraken.client.enums.OrderType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddOrderRequest extends ApiRequest {
    private OrderPair pair;
    private OrderType type;
    private BigDecimal volume;
}

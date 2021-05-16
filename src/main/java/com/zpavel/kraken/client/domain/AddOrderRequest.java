package com.zpavel.kraken.client.domain;

import com.zpavel.kraken.client.enums.OrderOperation;
import com.zpavel.kraken.client.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AddOrderRequest extends ApiRequest {
    private String pair;
    private OrderOperation type;
    private OrderType ordertype;
    private BigDecimal volume;
    private BigDecimal price;
    private BigDecimal price2;
}

package com.zpavel.kraken.client.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddOrderRequest extends ApiRequest {
    private String pair;
    private String type;
    private String ordertype;
    private BigDecimal volume;
}

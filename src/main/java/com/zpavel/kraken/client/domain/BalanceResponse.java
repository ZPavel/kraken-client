package com.zpavel.kraken.client.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class BalanceResponse extends ApiResponse {
    private Map<String, BigDecimal> result;
}

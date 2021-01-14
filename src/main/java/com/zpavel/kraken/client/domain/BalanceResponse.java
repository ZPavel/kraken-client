package com.zpavel.kraken.client.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class BalanceResponse {
    private List<String> error;
    private Map<String, BigDecimal> result;
}

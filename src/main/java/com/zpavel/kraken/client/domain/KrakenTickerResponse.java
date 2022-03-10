package com.zpavel.kraken.client.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class KrakenTickerResponse extends ApiResponse {
    private LocalDateTime created;
    private Map<String, PairData> result;

    @Data
    static class PairData {
        /*
        a = ask array(<price>, <whole lot volume>, <lot volume>),
        b = bid array(<price>, <whole lot volume>, <lot volume>),
        c = last trade closed array(<price>, <lot volume>),
        v = volume array(<today>, <last 24 hours>),
        p = volume weighted average price array(<today>, <last 24 hours>),
        t = number of trades array(<today>, <last 24 hours>),
        l = low array(<today>, <last 24 hours>),
        h = high array(<today>, <last 24 hours>),
        o = today's opening price
        */
        private BigDecimal[] a;
        private BigDecimal[] b;
        private BigDecimal[] c;
        private BigDecimal[] v;
        private BigDecimal[] p;
        private Integer[] t;
        private BigDecimal[] l;
        private BigDecimal[] h;
        private BigDecimal o;
    }
}

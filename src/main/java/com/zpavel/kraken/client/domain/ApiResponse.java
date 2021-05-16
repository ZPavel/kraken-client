package com.zpavel.kraken.client.domain;

import lombok.Data;

import java.util.List;

@Data
public class ApiResponse {
    private List<String> error;
}

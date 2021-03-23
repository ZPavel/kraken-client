package com.zpavel.kraken.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zpavel.kraken.client.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class KrakenServiceImpl implements KrakenService {
    private static int nonce = 0;
    private Logger logger = LoggerFactory.getLogger(KrakenServiceImpl.class);
    @Value("${kraken.api-key}")
    private String apiKey;
    @Value("${kraken.api-secret}")
    private String apiSecret;
    @Value("${kraken.api.public}")
    private String apiPublic;
    @Value("${kraken.api.private}")
    private String apiPrivate;
    @Value("${kraken.path.balance}")
    private String pathBalance;
    @Value("${kraken.path.ticker}")
    private String pathTicker;
    @Autowired
    private RestTemplate restTemplate;

    private <S> S sendGet(String url) {
        ResponseEntity<S> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    private <S, T extends ApiRequest> S sendPost(String url, String path, T req) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("API-Key", apiKey);
        ObjectMapper objectMapper = new ObjectMapper();
        String reqString = null;
        try {
            reqString = objectMapper.writeValueAsString(req);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            headers.add("API-Sign", signMessage(path, req.getNonce(), reqString));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        ResponseEntity<S> response = restTemplate.exchange(
                url + path,
                HttpMethod.POST,
                new HttpEntity<>(req, headers),
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    private String signMessage(String endpoint, String nonce, String jsonReq)
            throws NoSuchAlgorithmException, InvalidKeyException {
        String message = nonce + jsonReq;
        byte[] hash256 = MessageDigest.getInstance("SHA-256").digest(message.getBytes(StandardCharsets.UTF_8));
        byte[] secretDecoded = Base64.getDecoder().decode(apiSecret);
        Mac hmacsha512 = Mac.getInstance("HmacSHA512");
        hmacsha512.init(new SecretKeySpec(secretDecoded, "HmacSHA512"));
        hmacsha512.update(endpoint.getBytes(StandardCharsets.UTF_8));
        byte[] hash2 = hmacsha512.doFinal(hash256);
        return Base64.getEncoder().encodeToString(hash2);
    }

    private String getNonce() {
        nonce += 1;
        long timestamp = (new Date()).getTime();
        return timestamp + String.format("%04d", nonce);
    }

    @Override
    public KrakenTicker getTicker(List<String> pairs) {
        return sendGet(apiPublic + pathTicker + String.join(",", pairs));
    }

    @Override
    public BalanceResponse getBalance(BalanceRequest request) {
        request.setNonce(getNonce());
        return sendPost(apiPrivate, pathBalance, request);
    }

    @Override
    public AddOrderResponse addOrder(AddOrderRequest request) {
        return null;
    }
}

package com.zpavel.kraken.client.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zpavel.kraken.client.ApiException;
import com.zpavel.kraken.client.config.PropertyLoader;
import com.zpavel.kraken.client.domain.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class KrakenServiceImpl implements KrakenService {
    private static final Logger LOGGER = Logger.getLogger(KrakenServiceImpl.class.getName());

    public static final String KRAKEN_KEY = "kraken.key";
    public static final String KRAKEN_SECRET = "kraken.secret";
    public static final String KRAKEN_BALANCE = "kraken.balance";
    public static final String KRAKEN_TICKER = "kraken.ticker";
    private static int nonce = 0;
    private HttpClient httpClient = HttpClient.newHttpClient();
    private ObjectMapper mapper = new ObjectMapper();

    private ApiResponse sendGet(String url) throws ApiException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> httpResponse;
        try {
            httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            LOGGER.info("Status  : " + httpResponse.statusCode());
            LOGGER.info("Headers : " + httpResponse.headers());
            LOGGER.info("Body    : " + httpResponse.body());
        } catch (IOException | InterruptedException e) {
            throw new ApiException("GET failed");
        }

        ApiResponse response;
        try {
            response = mapper.readValue(httpResponse.body(), ApiResponse.class);
        } catch (JsonProcessingException e) {
            throw new ApiException("Unable to parse response");
        }
        return response;
    }

    private ApiResponse sendPost(String url, ApiRequest req) throws ApiException {
        String reqString = null;
        try {
            reqString = mapper.writeValueAsString(req);
        } catch (JsonProcessingException e) {
            LOGGER.severe(e.getMessage());
        }
        HttpRequest request;
        try {
            URI uri = new URI(url);
            request = HttpRequest.newBuilder()
                    .uri(uri)
                    .headers(
                            "Content-Type", "application/json",
                            "Accept", "application/json",
                            "API-Key", PropertyLoader.getInstance().getProperty(KRAKEN_KEY),
                            "API-Sign", signMessage(uri.getPath(), req.getNonce(), reqString)
                    )
                    .POST(HttpRequest.BodyPublishers.ofString(reqString))
                    .build();
        } catch (NoSuchAlgorithmException | InvalidKeyException | URISyntaxException e) {
            throw new ApiException("Build request failed");
        }

        HttpResponse<String> httpResponse;
        try {
            httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new ApiException("POST failed");
        }

        LOGGER.info("Status : " + httpResponse.statusCode());
        LOGGER.info("Body : " + httpResponse.body());

        ApiResponse response;
        try {
            response = mapper.readValue(httpResponse.body(), ApiResponse.class);
        } catch (JsonProcessingException e) {
            throw new ApiException("Unable to parse response");
        }
        return response;
    }

    private String signMessage(String endpoint, String nonce, String jsonReq)
            throws NoSuchAlgorithmException, InvalidKeyException {
        String message = nonce + jsonReq;
        byte[] hash256 = MessageDigest.getInstance("SHA-256").digest(message.getBytes(StandardCharsets.UTF_8));
        byte[] secretDecoded = Base64.getDecoder().decode(PropertyLoader.getInstance().getProperty(
                KRAKEN_SECRET));
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
    public KrakenTicker getTicker(List<String> pairs) throws ApiException {
        return (KrakenTicker) sendGet(PropertyLoader.getInstance().getProperty(KRAKEN_TICKER) + String.join(",", pairs));
    }

    @Override
    public BalanceResponse getBalance(BalanceRequest request) throws ApiException {
        request.setNonce(getNonce());
        return (BalanceResponse) sendPost(PropertyLoader.getInstance().getProperty(KRAKEN_BALANCE), request);
    }

    @Override
    public AddOrderResponse addOrder(AddOrderRequest request) {
        return null;
    }
}

package com.zpavel.kraken.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
    public static final String KRAKEN_KEY = "kraken.key";
    public static final String KRAKEN_SECRET = "kraken.secret";
    public static final String KRAKEN_TICKER = "kraken.ticker";
    public static final String KRAKEN_ASSET_PAIRS = "kraken.assetPairs";
    public static final String KRAKEN_BALANCE = "kraken.balance";
    public static final String KRAKEN_OPEN_ORDERS = "kraken.openOrders";
    public static final String KRAKEN_ADD_ORDER = "kraken.addOrder";
    public static final String KRAKEN_CANCEL_ORDER = "kraken.cancelOrder";
    public static final String KRAKEN_CANCEL_ALL = "kraken.cancelAll";

    private static final Logger LOG = Logger.getLogger(KrakenServiceImpl.class.getName());
    private static int nonce = 0;
    private HttpClient httpClient = HttpClient.newHttpClient();
    private ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private HttpResponse sendGet(String url) throws ApiException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> httpResponse;
        try {
            httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            LOG.info("Response status  : " + httpResponse.statusCode());
            LOG.info("Response headers : " + httpResponse.headers());
            LOG.info("Response body    : " + httpResponse.body());
        } catch (IOException | InterruptedException e) {
            throw new ApiException("GET failed", e);
        }

        return httpResponse;
    }

    private HttpResponse sendPost(String url, ApiRequest req) throws ApiException {
        String reqString = null;
        try {
            reqString = objectMapper.writeValueAsString(req);
        } catch (JsonProcessingException e) {
            throw new ApiException("Build request failed", e);
        }
        HttpRequest request;
        try {
            URI uri = new URI(url);
            request = HttpRequest.newBuilder()
                    .uri(uri)
                    .headers(
                            "Content-Type", "application/json",
                            "Accept", "application/json",
                            "API-Key", System.getProperty(KRAKEN_KEY),
                            "API-Sign", signMessage(uri.getPath(), req.getNonce(), reqString)
                    )
                    .POST(HttpRequest.BodyPublishers.ofString(reqString))
                    .build();
        } catch (NoSuchAlgorithmException | InvalidKeyException | URISyntaxException e) {
            throw new ApiException("Build request failed", e);
        }

        LOG.info("Request body : " + reqString);
        HttpResponse<String> httpResponse;
        try {
            httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new ApiException("POST failed");
        }

        LOG.info("Response status : " + httpResponse.statusCode());
        LOG.info("Response body : " + httpResponse.body());

        return httpResponse;
    }

    private String signMessage(String endpoint, String nonce, String jsonReq)
            throws NoSuchAlgorithmException, InvalidKeyException {
        String message = nonce + jsonReq;
        byte[] hash256 = MessageDigest.getInstance("SHA-256").digest(message.getBytes(StandardCharsets.UTF_8));
        byte[] secretDecoded = Base64.getDecoder().decode(System.getProperty(KRAKEN_SECRET));
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
    public AssetPairsResponse getAssetPairs() throws ApiException {
        HttpResponse httpResponse = sendGet(PropertyLoader.getInstance().getProperty(KRAKEN_ASSET_PAIRS));
        try {
            return objectMapper.readValue((String) httpResponse.body(), AssetPairsResponse.class);
        } catch (JsonProcessingException e) {
            throw new ApiException("Build request failed", e);
        }
    }

    @Override
    public KrakenTickerResponse getTicker(List<String> pairs) throws ApiException {
        HttpResponse httpResponse = sendGet(PropertyLoader.getInstance().getProperty(KRAKEN_TICKER) + String.join(",", pairs));
        try {
            return objectMapper.readValue((String) httpResponse.body(), KrakenTickerResponse.class);
        } catch (JsonProcessingException e) {
            throw new ApiException("Build request failed", e);
        }
    }

    @Override
    public KrakenTickerResponse getTrades(List<String> pairs) throws ApiException {
        return null;
    }

    @Override
    public BalanceResponse getBalance(BalanceRequest request) throws ApiException {
        request.setNonce(getNonce());
        HttpResponse httpResponse = sendPost(PropertyLoader.getInstance().getProperty(KRAKEN_BALANCE), request);
        try {
            return objectMapper.readValue((String) httpResponse.body(), BalanceResponse.class);
        } catch (JsonProcessingException e) {
            throw new ApiException("Build request failed", e);
        }
    }

    @Override
    public OpenOrdersResponse getOpenOrders(OpenOrdersRequest request) throws ApiException {
        request.setNonce(getNonce());
        HttpResponse httpResponse = sendPost(PropertyLoader.getInstance().getProperty(KRAKEN_OPEN_ORDERS), request);
        try {
            return objectMapper.readValue((String) httpResponse.body(), OpenOrdersResponse.class);
        } catch (JsonProcessingException e) {
            throw new ApiException("Build request failed", e);
        }
    }

    @Override
    public AddOrderResponse addOrder(AddOrderRequest request) throws ApiException {
        request.setNonce(getNonce());
        HttpResponse httpResponse = sendPost(PropertyLoader.getInstance().getProperty(KRAKEN_ADD_ORDER), request);
        try {
            return objectMapper.readValue((String) httpResponse.body(), AddOrderResponse.class);
        } catch (JsonProcessingException e) {
            throw new ApiException("Build request failed", e);
        }
    }

    @Override
    public CancelOrderResponse cancelOrder(CancelOrderRequest request) throws ApiException {
        request.setNonce(getNonce());
        HttpResponse httpResponse = sendPost(PropertyLoader.getInstance().getProperty(KRAKEN_CANCEL_ORDER), request);
        try {
            return objectMapper.readValue((String) httpResponse.body(), CancelOrderResponse.class);
        } catch (JsonProcessingException e) {
            throw new ApiException("Build request failed", e);
        }
    }

    @Override
    public CancelAllResponse cancelAll(CancelAllRequest request) throws ApiException {
        request.setNonce(getNonce());
        HttpResponse httpResponse = sendPost(PropertyLoader.getInstance().getProperty(KRAKEN_CANCEL_ALL), request);
        try {
            return objectMapper.readValue((String) httpResponse.body(), CancelAllResponse.class);
        } catch (JsonProcessingException e) {
            throw new ApiException("Build request failed", e);
        }
    }
}

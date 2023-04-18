package com.yaphet.chapa.client;

import java.util.Map;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.apache.http.HttpHeaders;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.body.MultipartBody;
import com.mashape.unirest.request.body.RequestBodyEntity;

public class ChapaClientImpl implements ChapaClient {

    private static final String authorizationHeader = HttpHeaders.AUTHORIZATION;
    private static final String acceptEncodingHeader = HttpHeaders.ACCEPT_ENCODING;

    private int statusCode;

    @Override
    public String post(String url, Map<String, Object> fields, String secreteKey) throws Throwable {
        MultipartBody request = Unirest.post(url)
                .header(acceptEncodingHeader, "application/json")
                .header(authorizationHeader, "Bearer " + secreteKey)
                .fields(fields);

        statusCode = request.asJson().getStatus();
        return request.asJson().getBody().toString();
    }

    @Override
    public String post(String url, String body, String secreteKey) throws Throwable {
        RequestBodyEntity request = Unirest.post(url)
                .header(acceptEncodingHeader, "application/json")
                .header(authorizationHeader, "Bearer " + secreteKey)
                .body(body);

        statusCode = request.asJson().getStatus();
        return request.asJson().getBody().toString();
    }

    @Override
    public String get(String url, String secreteKey) throws Throwable {
        HttpResponse<JsonNode> httpResponse = Unirest.get(url)
                .header(acceptEncodingHeader, "application/json")
                .header(authorizationHeader, "Bearer " + secreteKey)
                .asJson();

        statusCode = httpResponse.getStatus();
        return httpResponse.getBody().toString();
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }
}

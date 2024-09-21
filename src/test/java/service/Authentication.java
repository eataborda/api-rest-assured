package service;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resources.BodyFieldNames;
import resources.HeaderNames;
import resources.HeaderValues;
import resources.URLs;

import java.util.Map;

public class Authentication {
    private final String user;
    private final String password;
    private final String authorization;

    public Authentication() {
        user = getEnvironmentVariable("USER");
        password = getEnvironmentVariable("PASSWORD");
        authorization = getEnvironmentVariable("AUTHORIZATION");
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthorization() {
        return authorization;
    }

    public String getSessionToken() {
        RequestSpecification request = RestAssured.given();
        Map<String, String> requestBody = Map.of(BodyFieldNames.USERNAME.getValue(), user, BodyFieldNames.PASSWORD.getValue(), password);
        request.header(HeaderNames.CONTENT_TYPE.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        request.header(HeaderNames.ACCEPT.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        request.body(requestBody);
        Response response = request.post(URLs.AUTH.getValue());
        return response.jsonPath().getString("token");
    }

    private String getEnvironmentVariable(String key) {
        return System.getenv(key);
    }
}

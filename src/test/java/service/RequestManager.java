package service;

import resources.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class RequestManager {
    static AllureRestAssured allureFilter = new AllureRestAssured()
            .setRequestAttachmentName("Request")
            .setResponseAttachmentName("Response");

    public Response getHealthCheck() {
        RequestSpecification request = RestAssured.given();
        request.filter(allureFilter);
        request.header(HeaderNames.CONTENT_TYPE.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        request.header(HeaderNames.ACCEPT.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        return request.get(URLs.PING.getValue());
    }

    public Response getAllBookingIds() {
        RequestSpecification request = RestAssured.given();
        request.filter(allureFilter);
        request.header(HeaderNames.CONTENT_TYPE.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        request.header(HeaderNames.ACCEPT.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        return request.get(URLs.BASE.getValue());
    }

    public Response getBookingById(int bookingId) {
        RequestSpecification request = RestAssured.given();
        request.filter(allureFilter);
        request.header(HeaderNames.CONTENT_TYPE.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        request.header(HeaderNames.ACCEPT.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        request.pathParams("id", bookingId);
        return request.get(URLs.BASE.getValue() + "/{id}");
    }

    public Response postCreateBooking() {
        RequestSpecification request = RestAssured.given();
        request.filter(allureFilter);
        request.header(HeaderNames.CONTENT_TYPE.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        request.header(HeaderNames.ACCEPT.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        request.body(Body.getRequestBody());
        return request.post(URLs.BASE.getValue());
    }

    public Response putUpdateBooking(int bookingId, String sessionToken) {
        Map<String, String> authenticationHeader = Header.getAuthenticationHeader(sessionToken);
        RequestSpecification request = RestAssured.given();
        request.filter(allureFilter);
        request.header(HeaderNames.CONTENT_TYPE.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        request.header(HeaderNames.ACCEPT.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        request.header(authenticationHeader.get("key"), authenticationHeader.get("value"));
        request.body(Body.getUpdatedRequestBody());
        request.pathParams("id", bookingId);
        return request.put(URLs.BASE.getValue() + "/{id}");
    }

    public Response patchPartialUpdateBooking(int bookingId, String sessionToken) {
        Map<String, String> authenticationHeader = Header.getAuthenticationHeader(sessionToken);
        RequestSpecification request = RestAssured.given();
        request.filter(allureFilter);
        request.header(HeaderNames.CONTENT_TYPE.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        request.header(HeaderNames.ACCEPT.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        request.header(authenticationHeader.get("key"), authenticationHeader.get("value"));
        request.body(Body.getPartiallyUpdatedRequestBody());
        request.pathParams("id", bookingId);
        return request.patch(URLs.BASE.getValue() + "/{id}");
    }

    public Response deleteBooking(int bookingId, String sessionToken) {
        Map<String, String> authenticationHeader = Header.getAuthenticationHeader(sessionToken);
        RequestSpecification request = RestAssured.given();
        request.filter(allureFilter);
        request.header(HeaderNames.CONTENT_TYPE.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        request.header(HeaderNames.ACCEPT.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        request.header(authenticationHeader.get("key"), authenticationHeader.get("value"));
        request.pathParams("id", bookingId);
        return request.delete(URLs.BASE.getValue() + "/{id}");
    }

    public Response postCreateBookingWithMalformedBody(int expectedStatusCode) {
        RequestSpecification request = RestAssured.given();
        request.filter(allureFilter);
        request.headers(HeaderNames.CONTENT_TYPE.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        request.header(HeaderNames.ACCEPT.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        if (expectedStatusCode == StatusCode.SC_400.getValue()) {
            request.body(Body.getMalformedRequestBodyStatusCode400());
        } else if (expectedStatusCode == StatusCode.SC_500.getValue()) {
            request.body(Body.getMalformedRequestBodyStatusCode500());
        }
        return request.post(URLs.BASE.getValue());
    }

    public Response putUpdateBookingAuthenticationHeader(int bookingId) {
        RequestSpecification request = RestAssured.given();
        request.filter(allureFilter);
        request.header(HeaderNames.CONTENT_TYPE.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        request.header(HeaderNames.ACCEPT.getValue(), HeaderValues.APPLICATION_JSON.getValue());
        request.body(Body.getUpdatedRequestBody());
        request.pathParams("id", bookingId);
        return request.put(URLs.BASE.getValue() + "/{id}");
    }

}

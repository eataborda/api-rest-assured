package service;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import resources.HeaderNames;
import resources.HeaderValues;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class Header {
    private static final Authentication authentication = new Authentication();

    public static boolean isValidResponseHeaderDate(String dateString) {
        try {
            LocalDate.parse(dateString, DateTimeFormatter.RFC_1123_DATE_TIME);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public static Map<String, String> getAuthenticationHeader(String sessionToken) {
        Map<String, String> authenticationHeader = new HashMap<>();
        if (sessionToken.length() != 0) {
            authenticationHeader.put("key", HeaderNames.COOKIE.getValue());
            authenticationHeader.put("value", "token=" + sessionToken);
        } else {
            authenticationHeader.put("key", HeaderNames.AUTHORIZATION.getValue());
            authenticationHeader.put("value", "Basic " + authentication.getAuthorization());
        }
        return authenticationHeader;
    }
}

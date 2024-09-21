package service;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import resources.BodyFieldNames;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Body {

    public static boolean isValidResponseBodyDate(String dateString) {
        try {
            LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
    public static Map<String, Object> getRequestBody() {
        Map<String, String> bookingDates = new HashMap<>();
        bookingDates.put(BodyFieldNames.CHECKIN.getValue(), "2024-09-02");
        bookingDates.put(BodyFieldNames.CHECKOUT.getValue(), "2024-09-10");
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put(BodyFieldNames.FIRST_NAME.getValue(), "Edwin");
        reqBody.put(BodyFieldNames.LAST_NAME.getValue(), "Hubble");
        reqBody.put(BodyFieldNames.TOTAL_PRICE.getValue(), 170);
        reqBody.put(BodyFieldNames.DEPOSIT_PAID.getValue(), true);
        reqBody.put(BodyFieldNames.ADDITIONAL_NEEDS.getValue(), "Learning and sharing knowledge!");
        reqBody.put(BodyFieldNames.BOOKING_DATES.getValue(), bookingDates);
        return reqBody;
    }

    public static Map<String, Object> getUpdatedRequestBody() {
        Map<String, String> bookingDates = new HashMap<>();
        bookingDates.put(BodyFieldNames.CHECKIN.getValue(), "2022-05-04");
        bookingDates.put(BodyFieldNames.CHECKOUT.getValue(), "2022-06-05");
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put(BodyFieldNames.FIRST_NAME.getValue(), "Albert");
        reqBody.put(BodyFieldNames.LAST_NAME.getValue(), "Einstein");
        reqBody.put(BodyFieldNames.TOTAL_PRICE.getValue(), 190);
        reqBody.put(BodyFieldNames.DEPOSIT_PAID.getValue(), true);
        reqBody.put(BodyFieldNames.ADDITIONAL_NEEDS.getValue(), "Understand the nature of things!");
        reqBody.put(BodyFieldNames.BOOKING_DATES.getValue(), bookingDates);

        return reqBody;
    }

    public static Map<String, Object> getPartiallyUpdatedRequestBody() {
        Map<String, Object> bookingValuesToUpdate = new HashMap<>();
        bookingValuesToUpdate.put(BodyFieldNames.FIRST_NAME.getValue(), "Nikola");
        bookingValuesToUpdate.put(BodyFieldNames.LAST_NAME.getValue(), "Tesla");
        return bookingValuesToUpdate;
    }

    public static String getMalformedRequestBodyStatusCode400() {
        return new String("{\"firstname\":\"George\",\"lastname\":\"Fisher\",\"totalprice\":420,\"depositpaid\":true,\"bookingdates\":{\"checkin\":\"2022-04-24\",\"checkout\":\"2023-04-24\"},\"additionalneeds\":%}");
    }

    public static Map<String, Object> getMalformedRequestBodyStatusCode500() {
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put(BodyFieldNames.FIRST_NAME.getValue(), "George");
        reqBody.put(BodyFieldNames.LAST_NAME.getValue(), "Fisher");
        reqBody.put(BodyFieldNames.TOTAL_PRICE.getValue(), 190);
        reqBody.put(BodyFieldNames.DEPOSIT_PAID.getValue(), true);
        reqBody.put(BodyFieldNames.ADDITIONAL_NEEDS.getValue(), "Fish all day!");
        return reqBody;
    }

    public static int getIdFromCreatedBooking(Response response) {
        return response.getBody().jsonPath().getInt(BodyFieldNames.BOOKING_ID.getValue());
    }

    public static int getARandomExistingBookingId(Response response) {
        List<Map<String, Integer>> bookingIdList = response.jsonPath().getList("$");
        List<Integer> existingBookingIds = new ArrayList<>();
        for (Map<String, Integer> bookingIdElement : bookingIdList) {
            existingBookingIds.add(bookingIdElement.get("bookingid"));
        }
        int randomBookingId = existingBookingIds.get(new Random().nextInt(existingBookingIds.size() - 1));
        Allure.step("Select a random booking id: " + randomBookingId);
        return randomBookingId;
    }
}

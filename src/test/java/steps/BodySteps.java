package steps;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.assertj.core.api.SoftAssertions;
import resources.BodyFieldNames;
import service.Body;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BodySteps {
    private static SoftAssertions assertion;

    public void validateResponseBodyIsNotNullAndNotEmpty(Response response) {
        var body = response.getBody();
        Integer bodyLength = body.asString().length();
        assertion = new SoftAssertions();
        assertion.assertThat(body)
                .as("Response body is null")
                .isNotNull();
        assertion.assertThat(bodyLength > 2)
                .as("Response body is empty, length = ".concat(bodyLength.toString()))
                .isTrue();
        assertion.assertAll();
    }

    public void validateGetAllBookingIdListResponseBodyHasExpectedFields(Response response) {
        assertion = new SoftAssertions();
        List<Map<String, Integer>> bookingIdList = response.jsonPath().getList("$");
        String bookingId = "";
        for (Map<String, Integer> bookingIdElement : bookingIdList) {
            bookingId = String.valueOf(bookingIdElement.get("bookingid"));
            Allure.step("Validate field value is not null and only has digits -  bookingid = ".concat(bookingId));
            assertion.assertThat(bookingId).as("BookingId field is null").isNotNull();
            assertion.assertThat(bookingId.matches("[0-9.]+")).isTrue();
        }
        assertion.assertAll();
    }

    public void validateResponseBodyHasExpectedFields(Response response) {
        assertion = new SoftAssertions();
        validateFieldIsNotNullAndEmpty(BodyFieldNames.FIRST_NAME.getValue(),
                getStringFieldValue(response,
                        " ",
                        BodyFieldNames.FIRST_NAME.getValue()));
        validateFieldIsNotNullAndEmpty(BodyFieldNames.LAST_NAME.getValue(),
                getStringFieldValue(response,
                        " ",
                        BodyFieldNames.LAST_NAME.getValue()));
        validateIntegerFieldValue(BodyFieldNames.TOTAL_PRICE.getValue(),
                getIntFieldValue(response,
                        " ",
                        BodyFieldNames.TOTAL_PRICE.getValue()));
        validateBooleanFieldValue(BodyFieldNames.DEPOSIT_PAID.getValue(),
                getBooleanFieldValue(response,
                        " ",
                        BodyFieldNames.DEPOSIT_PAID.getValue()));
        validateDateFieldValue(BodyFieldNames.CHECKIN.getValue(),
                getStringFieldValue(response,
                        BodyFieldNames.BOOKING_DATES.getValue().concat("."),
                        BodyFieldNames.CHECKIN.getValue()));
        validateDateFieldValue(BodyFieldNames.CHECKOUT.getValue(),
                getStringFieldValue(response,
                        BodyFieldNames.BOOKING_DATES.getValue().concat("."),
                        BodyFieldNames.CHECKOUT.getValue()));
        if (getStringFieldValue(response,
                " ",
                BodyFieldNames.ADDITIONAL_NEEDS.getValue()) != null) {
            validateFieldIsNotNullAndEmpty(BodyFieldNames.ADDITIONAL_NEEDS.getValue(),
                    getStringFieldValue(response,
                            " ",
                            BodyFieldNames.ADDITIONAL_NEEDS.getValue()));
        }
        assertion.assertAll();
    }

    public void validatePostCreateBookingResponseBodyHasExpectedFields(Response response) {
        assertion = new SoftAssertions();
        validateIntegerFieldValue(BodyFieldNames.BOOKING_ID.getValue(),
                getIntFieldValue(response,
                        "",
                        BodyFieldNames.BOOKING_ID.getValue()));
        validateFieldIsNotNullAndEmpty(BodyFieldNames.FIRST_NAME.getValue(),
                getStringFieldValue(response,
                        BodyFieldNames.BOOKING.getValue().concat("."),
                        BodyFieldNames.FIRST_NAME.getValue()));
        validateFieldIsNotNullAndEmpty(BodyFieldNames.LAST_NAME.getValue(),
                getStringFieldValue(response,
                        BodyFieldNames.BOOKING.getValue().concat("."),
                        BodyFieldNames.LAST_NAME.getValue()));
        validateIntegerFieldValue(BodyFieldNames.TOTAL_PRICE.getValue(),
                getIntFieldValue(response,
                        BodyFieldNames.BOOKING.getValue().concat("."),
                        BodyFieldNames.TOTAL_PRICE.getValue()));
        validateBooleanFieldValue(BodyFieldNames.DEPOSIT_PAID.getValue(),
                getBooleanFieldValue(response,
                        BodyFieldNames.BOOKING.getValue().concat("."),
                        BodyFieldNames.DEPOSIT_PAID.getValue()));
        validateDateFieldValue(BodyFieldNames.CHECKIN.getValue(),
                getStringFieldValue(response,
                        BodyFieldNames.BOOKING.getValue().concat(".")
                                .concat(BodyFieldNames.BOOKING_DATES.getValue())
                                .concat("."),
                        BodyFieldNames.CHECKIN.getValue()));

        validateDateFieldValue(BodyFieldNames.CHECKOUT.getValue(),
                getStringFieldValue(response,
                        BodyFieldNames.BOOKING.getValue()
                                .concat(".")
                                .concat(BodyFieldNames.BOOKING_DATES.getValue())
                                .concat("."),
                        BodyFieldNames.CHECKOUT.getValue()));
        if (getStringFieldValue(response,
                BodyFieldNames.BOOKING.getValue().concat("."),
                BodyFieldNames.ADDITIONAL_NEEDS.getValue()) != null) {
            validateFieldIsNotNullAndEmpty(BodyFieldNames.ADDITIONAL_NEEDS.getValue(),
                    getStringFieldValue(response,
                            BodyFieldNames.BOOKING.getValue().concat("."),
                            BodyFieldNames.ADDITIONAL_NEEDS.getValue()));
        }
        assertion.assertAll();
    }

    @Step("Validate field is not null and not empty: {1}")
    public void validateFieldIsNotNullAndEmpty(String fieldName, String currentValue) {
        Integer fieldValueLength = currentValue.length();
        assertion.assertThat(currentValue)
                .as("Field ".concat(fieldName).concat(" is null"))
                .isNotNull();
        assertion.assertThat(fieldValueLength)
                .as("Field ".concat(fieldName).concat(" is empty, length = ").concat(fieldValueLength.toString()))
                .isNotZero();
    }

    @Step("Validate field is not null and only has digits: {1} ")
    public void validateIntegerFieldValue(String fieldName, Integer intValue) {
        assertion.assertThat(intValue)
                .as("Field ".concat(fieldName).concat(" is null"))
                .isNotNull();
        assertion.assertThat(String.valueOf(intValue).matches("[0-9.]+"))
                .as(fieldName.concat(" field doesn't have the expected value: ").concat(intValue.toString()))
                .isTrue();
    }

    @Step("Validate field is not null and is a boolean: {1}")
    public void validateBooleanFieldValue(String fieldName, Boolean booleanValue) {
        Pattern pattern = Pattern.compile("true|false", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(String.valueOf(booleanValue));
        assertion.assertThat(booleanValue)
                .as("Field ".concat(fieldName).concat(" is null"))
                .isNotNull();
        assertion.assertThat(matcher.matches())
                .as(fieldName.concat(" field doesn't have the expected value: ").concat(booleanValue.toString()))
                .isTrue();
    }

    @Step("Validate field is not null, not empty and is a valid date: {1}")
    public void validateDateFieldValue(String fieldName, String fieldValue) {
        Integer fieldValueLength = fieldValue.length();
        assertion.assertThat(fieldValue)
                .as("Field ".concat(fieldName).concat(" is null"))
                .isNotNull();
        assertion.assertThat(fieldValueLength)
                .as("Field ".concat(fieldName).concat(" is empty, length = ").concat(fieldValueLength.toString()))
                .isNotZero();
        assertion.assertThat(Body.isValidResponseBodyDate(fieldValue))
                .as(fieldName.concat(" value doesn't have the correct format: ").concat(fieldValue))
                .isTrue();
    }

    public void validateResponseBodyHasSameFieldValuesUsedOnRequestBody
            (Response response, String jsonUsedOnServiceCall) {
        ResponseBody responseBody = response.getBody();
        assertion = new SoftAssertions();
        switch (jsonUsedOnServiceCall) {
            case "create" -> validateResponseBodyHasSameFieldValuesUsedOnRequestBodyForAllFields
                    (Body.getRequestBody(), responseBody);
            case "update" -> validateResponseBodyHasSameFieldValuesUsedOnRequestBodyForAllFields
                    (Body.getUpdatedRequestBody(), responseBody);
            case "partialUpdate" -> validateResponseBodyHasSameFieldValuesUsedOnRequestBodyForSpecificFields
                    (Body.getPartiallyUpdatedRequestBody(), responseBody);
            default -> {
            }
        }
        assertion.assertAll();
    }

    public void validateResponseBodyHasSameFieldValuesUsedOnRequestBodyForAllFields
            (Map<String, Object> requestBody, ResponseBody responseBody) {
        validateResponseAndRequestFieldIsEqual(
                BodyFieldNames.FIRST_NAME.getValue(),
                responseBody.jsonPath().getString(BodyFieldNames.FIRST_NAME.getValue()),
                requestBody.get(BodyFieldNames.FIRST_NAME.getValue()).toString());
        validateResponseAndRequestFieldIsEqual(
                BodyFieldNames.LAST_NAME.getValue(),
                responseBody.jsonPath().getString(BodyFieldNames.LAST_NAME.getValue()),
                requestBody.get(BodyFieldNames.LAST_NAME.getValue()).toString());
        validateResponseAndRequestFieldIsEqual(
                BodyFieldNames.TOTAL_PRICE.getValue(),
                responseBody.jsonPath().getInt(BodyFieldNames.TOTAL_PRICE.getValue()),
                requestBody.get(BodyFieldNames.TOTAL_PRICE.getValue()));
        validateResponseAndRequestFieldIsEqual(
                BodyFieldNames.DEPOSIT_PAID.getValue(),
                responseBody.jsonPath().getBoolean(BodyFieldNames.DEPOSIT_PAID.getValue()),
                requestBody.get(BodyFieldNames.DEPOSIT_PAID.getValue()));
        Map<String, String> bookingDates = new Gson().fromJson(
                requestBody.get(BodyFieldNames.BOOKING_DATES.getValue()).toString(),
                new TypeToken<HashMap<String, String>>() {
                }.getType());
        validateResponseAndRequestFieldIsEqual(
                BodyFieldNames.CHECKIN.getValue(),
                responseBody.jsonPath().getString(BodyFieldNames.BOOKING_DATES.getValue()
                        .concat(".")
                        .concat(BodyFieldNames.CHECKIN.getValue())),
                bookingDates.get(BodyFieldNames.CHECKIN.getValue()));
        validateResponseAndRequestFieldIsEqual(
                BodyFieldNames.CHECKOUT.getValue(),
                responseBody.jsonPath().getString(BodyFieldNames.BOOKING_DATES.getValue()
                        .concat(".")
                        .concat(BodyFieldNames.CHECKOUT.getValue())),
                bookingDates.get(BodyFieldNames.CHECKOUT.getValue()));
        validateResponseAndRequestFieldIsEqual(
                BodyFieldNames.ADDITIONAL_NEEDS.getValue(),
                responseBody.jsonPath().getString(BodyFieldNames.ADDITIONAL_NEEDS.getValue()),
                requestBody.get(BodyFieldNames.ADDITIONAL_NEEDS.getValue()).toString());
    }

    public void validateResponseBodyHasSameFieldValuesUsedOnRequestBodyForSpecificFields
            (Map<String, Object> requestBody, ResponseBody responseBody) {
        validateResponseAndRequestFieldIsEqual(
                BodyFieldNames.FIRST_NAME.getValue(),
                responseBody.jsonPath().getString(BodyFieldNames.FIRST_NAME.getValue()),
                requestBody.get(BodyFieldNames.FIRST_NAME.getValue()).toString());
        validateResponseAndRequestFieldIsEqual(
                BodyFieldNames.LAST_NAME.getValue(),
                responseBody.jsonPath().getString(BodyFieldNames.LAST_NAME.getValue()),
                requestBody.get(BodyFieldNames.LAST_NAME.getValue()).toString());
    }

    @Step("Body - Validate {0} field value on response body equals to request body: {1} = {2}")
    public void validateResponseAndRequestFieldIsEqual(String fieldName, Object currentValue, Object expectedValue) {
        assertion.assertThat(currentValue)
                .as("Field: "
                        .concat(fieldName)
                        .concat("Doesn't have the same value used on the request body, Current= ")
                        .concat(currentValue.toString())
                        .concat(" Expected = ")
                        .concat(expectedValue.toString()))
                .isEqualTo(expectedValue);
    }


    public String getStringFieldValue(Response response, String fieldPath, String fieldName) {
        return response.getBody().jsonPath().getString(fieldPath + fieldName);
    }

    public int getIntFieldValue(Response response, String fieldPath, String fieldName) {
        return response.getBody().jsonPath().getInt(fieldPath + fieldName);
    }

    public Boolean getBooleanFieldValue(Response response, String fieldPath, String fieldName) {
        return response.getBody().jsonPath().getBoolean(fieldPath + fieldName);
    }
}

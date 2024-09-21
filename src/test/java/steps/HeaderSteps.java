package steps;

import io.qameta.allure.Step;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import resources.HeaderNames;
import resources.HeaderValues;
import service.Header;

public class HeaderSteps {
    private static SoftAssertions assertion;

    public void validateResponseHeadersAreNotNullAndNotEmpty(Response response) {
        Headers headers = response.getHeaders();
        assertion = new SoftAssertions();
        assertion.assertThat(headers)
                .as("Headers are null")
                .isNotNull();
        assertion.assertThat(headers.asList().toString())
                .as("Headers are empty")
                .isNotEmpty();
        assertion.assertAll();
    }

    public void validateResponseHeadersHasExpectedFields(Response response) {
        assertion = new SoftAssertions();
        validateHeaderFieldSize(11, response.getHeaders().size());
        validateStringFieldType(
                HeaderNames.SERVER.getValue(),
                HeaderValues.COWBOY.getValue(),
                response.getHeader(HeaderNames.SERVER.getValue()));
        validateStringFieldType(
                HeaderNames.NEL.getValue(),
                HeaderValues.NEL_VALUE.getValue(),
                response.getHeader(HeaderNames.NEL.getValue()));
        validateStringFieldType(
                HeaderNames.CONNECTION.getValue(),
                HeaderValues.KEEP_ALIVE.getValue(),
                response.getHeader(HeaderNames.CONNECTION.getValue()));
        validateStringFieldType(
                HeaderNames.X_POWERED_BY.getValue(),
                HeaderValues.EXPRESS.getValue(),
                response.getHeader(HeaderNames.X_POWERED_BY.getValue()));
        String jsonBody = response.getBody().asString();
        if (jsonBody.equals("Created") || jsonBody.equals("Not Found")) {
            validateStringFieldType(
                    HeaderNames.CONTENT_TYPE.getValue(),
                    HeaderValues.TEXT_PLAIN_CHARSET_UTF_8.getValue(),
                    response.getHeader(HeaderNames.CONTENT_TYPE.getValue()));
        } else {
            validateStringFieldType(
                    HeaderNames.CONTENT_TYPE.getValue(),
                    HeaderValues.APPLICATION_JSON_CHARSET_UTF_8.getValue(),
                    response.getHeader(HeaderNames.CONTENT_TYPE.getValue()));
        }
        validateIntegerFieldType(
                HeaderNames.CONTENT_LENGTH.getValue(),
                response.getHeader(HeaderNames.CONTENT_LENGTH.getValue()));
        validateFieldValueIsNotNull(
                HeaderNames.ETAG.getValue(),
                response.getHeader(HeaderNames.ETAG.getValue()),
                response.getHeader(HeaderNames.ETAG.getValue()));
        validateDateFieldValue(
                HeaderNames.DATE.getValue(),
                response.getHeader(HeaderNames.DATE.getValue()));
        validateStringFieldType(
                HeaderNames.VIA.getValue(),
                HeaderValues.VEGUR_1_1.getValue(),
                response.getHeader(HeaderNames.VIA.getValue()));
        assertion.assertAll();
    }

    @Step("Validate expected number of fields")
    public void validateHeaderFieldSize(int expectedSize, int currentSize) {
        assertion.assertThat(currentSize)
                .as("Response headers doesn't have the expected number of fields: ".concat(Integer.toString(currentSize)))
                .isEqualTo(expectedSize);
    }

    @Step("Validate expected value of string field: {0}")
    public void validateStringFieldType(String fieldName, String expectedValue, String currentValue) {
        assertion.assertThat(currentValue)
                .as("Field " + fieldName + " is null")
                .isNotNull();
        assertion.assertThat(currentValue)
                .as(fieldName.concat(" field doesn't have the expected value: ").concat(currentValue))
                .isEqualTo(expectedValue);
    }

    @Step("Validate expected value of integer field: {0}")
    public void validateIntegerFieldType(String fieldName, String currentValue) {
        assertion.assertThat(currentValue)
                .as("Field " + fieldName + " is null")
                .isNotNull();
        assertion.assertThat(currentValue.matches("[0-9.]+"))
                .as(fieldName.concat(" field doesn't have the expected value: ").concat(currentValue))
                .isTrue();
    }

    @Step("Validate expected value of string field: {0}")
    public void validateFieldValueIsNotNull(String fieldName, String expectedValue, String currentValue) {
        assertion.assertThat(currentValue)
                .as(fieldName.concat(" field doesn't have the expected value: ").concat(expectedValue))
                .isNotNull();
    }

    @Step("Validate expected value of date field: {0}")
    public void validateDateFieldValue(String fieldName, String currentValue) {
        assertion.assertThat(currentValue)
                .as("Field ".concat(fieldName).concat(" is null"))
                .isNotNull();
        assertion.assertThat(Header.isValidResponseHeaderDate(currentValue))
                .as(fieldName.concat(" value doesn't have the correct format: ").concat(currentValue))
                .isTrue();
    }
}

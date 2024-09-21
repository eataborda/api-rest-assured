package steps;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import resources.Comments;
import service.Authentication;
import service.RequestManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingAPISteps {
    private final HeaderSteps headerSteps = new HeaderSteps();
    private final BodySteps bodySteps = new BodySteps();
    private final RequestManager requestManager = new RequestManager();
    private final Authentication authentication = new Authentication();

    @Step("Get session token")
    public String getSessionToken() {
        return authentication.getSessionToken();
    }

    @Step("Get service health check")
    public Response getHealthCheck() {
        return requestManager.getHealthCheck();
    }

    @Step("GET all booking Ids")
    public Response getAllBookingIds() {
        return requestManager.getAllBookingIds();
    }

    @Step("Get booking by id = {0}")
    public Response getBookingById(int id) {
        return requestManager.getBookingById(id);
    }

    @Step("Create booking")
    public Response postCreateBooking() {
        return requestManager.postCreateBooking();
    }

    @Step("Update booking by id = {0}")
    public Response putUpdateBooking(int bookingId, String sessionToken) {
        return requestManager.putUpdateBooking(bookingId, sessionToken);
    }

    @Step("Partial update booking by id = {0}")
    public Response patchPartialUpdateBooking(int bookingId, String sessionToken) {
        return requestManager.patchPartialUpdateBooking(bookingId, sessionToken);
    }

    @Step("Delete booking by id = {0}")
    public Response deleteBooking(int bookingId, String sessionToken) {
        return requestManager.deleteBooking(bookingId, sessionToken);
    }


    @Step("Validate expected status code: {0}")
    public void validateStatusCode(int expected, int current) {
        assertEquals(expected, current, "Not expected status code:");
    }

    @Step("Validate response body content is not null and not empty")
    public void validateResponseBodyIsNotNullAndNotEmpty(Response response) {
        bodySteps.validateResponseBodyIsNotNullAndNotEmpty(response);
    }

    @Step("Validate expected response body fields")
    public void validateResponseBodyHasExpectedFields(Response response) {
        bodySteps.validateResponseBodyHasExpectedFields(response);
    }

    @Step("Validate expected response body fields")
    public void validateGetAllBookingIdListResponseBodyHasExpectedFields(Response response) {
        bodySteps.validateGetAllBookingIdListResponseBodyHasExpectedFields(response);
    }

    @Step("Validate expected response body fields")
    public void validatePostCreateBookingResponseBodyHasExpectedFields(Response response) {
        bodySteps.validatePostCreateBookingResponseBodyHasExpectedFields(response);
    }

    @Step("Validate response header content is not null and not empty")
    public void validateResponseHeadersAreNotNullAndNotEmpty(Response response) {
        headerSteps.validateResponseHeadersAreNotNullAndNotEmpty(response);
    }

    @Step("Validate expected response header fields")
    public void validateResponseHeadersHasExpectedFields(Response response) {
        headerSteps.validateResponseHeadersHasExpectedFields(response);
    }

    @Step("Validate response body has the same values used on request body")
    public void validateResponseBodyHasSameFieldValuesUsedOnRequestBody(Response response, String jsonUsedOnServiceCall) {
        bodySteps.validateResponseBodyHasSameFieldValuesUsedOnRequestBody(response, jsonUsedOnServiceCall);
    }

    @Step("Create booking with malformed body - Status code: {0}")
    public Response postCreateBookingWithMalformedBody(int expectedStatusCode) {
        return requestManager.postCreateBookingWithMalformedBody(expectedStatusCode);
    }

    @Step("Update booking without authentication header")
    public Response putUpdateBookingAuthenticationHeader(int bookingId) {
        return requestManager.putUpdateBookingAuthenticationHeader(bookingId);
    }
}

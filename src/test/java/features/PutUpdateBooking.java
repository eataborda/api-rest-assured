package features;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import resources.AnnotationValues;
import resources.StatusCode;
import service.Body;
import steps.BookingAPISteps;

@Tags(value = {@Tag(AnnotationValues.REGRESSION_TAG),
        @Tag(AnnotationValues.SMOKE_TAG), @Tag(AnnotationValues.PUT_METHOD_TAG)})
@DisplayName("Put update booking")
public class PutUpdateBooking {
    private static String token;
    private static int bookingId;
    private static BookingAPISteps bookingSteps = new BookingAPISteps();

    @BeforeAll
    public static void setupValues() {
        Response response = bookingSteps.postCreateBooking();
        bookingId = Body.getIdFromCreatedBooking(response);
        token = bookingSteps.getSessionToken();
    }
    @Test
    @DisplayName("Put update booking")
    @Description("PUT call to update a booking in the database")
    @Feature("PUT booking")
    public void putUpdateBooking() {
        Response response = bookingSteps.putUpdateBooking(bookingId, token);
        bookingSteps.validateStatusCode(StatusCode.SC_200.getValue(), response.getStatusCode());
        bookingSteps.validateResponseBodyIsNotNullAndNotEmpty(response);
        bookingSteps.validateResponseBodyHasExpectedFields(response);
        bookingSteps.validateResponseHeadersAreNotNullAndNotEmpty(response);
        bookingSteps.validateResponseHeadersHasExpectedFields(response);
        //Verify that the changes take effect after the update
        Response responseAfterUpdateBooking = bookingSteps.getBookingById(bookingId);
        bookingSteps.validateStatusCode(StatusCode.SC_200.getValue(), responseAfterUpdateBooking.getStatusCode());
        bookingSteps.validateResponseBodyIsNotNullAndNotEmpty(responseAfterUpdateBooking);
        bookingSteps.validateResponseBodyHasExpectedFields(responseAfterUpdateBooking);
        bookingSteps.validateResponseBodyHasSameFieldValuesUsedOnRequestBody(responseAfterUpdateBooking, "update");
        bookingSteps.validateResponseHeadersAreNotNullAndNotEmpty(responseAfterUpdateBooking);
        bookingSteps.validateResponseHeadersHasExpectedFields(responseAfterUpdateBooking);

    }
}

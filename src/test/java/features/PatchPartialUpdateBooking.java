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


@Tags(value = {@Tag(AnnotationValues.REGRESSION_TAG), @Tag(AnnotationValues.PATCH_METHOD_TAG)})
@DisplayName("Patch partial update booking")
public class PatchPartialUpdateBooking {
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
    @DisplayName("Patch partial update booking")
    @Description("PATCH call to partially update a booking in the database")
    @Feature("PATCH booking")
    public void patchPartialUpdateBooking() {
        Response response = bookingSteps.patchPartialUpdateBooking(bookingId, token);
        bookingSteps.validateStatusCode(StatusCode.SC_200.getValue(), response.getStatusCode());
        bookingSteps.validateResponseBodyIsNotNullAndNotEmpty(response);
        bookingSteps.validateResponseBodyHasExpectedFields(response);
        bookingSteps.validateResponseHeadersAreNotNullAndNotEmpty(response);
        bookingSteps.validateResponseHeadersHasExpectedFields(response);
        //Verify that the changes take effect after the partial update
        Response responseAfterPartialUpdateBooking = bookingSteps.getBookingById(bookingId);
        bookingSteps.validateStatusCode(StatusCode.SC_200.getValue(), responseAfterPartialUpdateBooking.getStatusCode());
        bookingSteps.validateResponseBodyIsNotNullAndNotEmpty(responseAfterPartialUpdateBooking);
        bookingSteps.validateResponseBodyHasExpectedFields(responseAfterPartialUpdateBooking);
        bookingSteps.validateResponseBodyHasSameFieldValuesUsedOnRequestBody(responseAfterPartialUpdateBooking, "partialUpdate");
        bookingSteps.validateResponseHeadersAreNotNullAndNotEmpty(responseAfterPartialUpdateBooking);
        bookingSteps.validateResponseHeadersHasExpectedFields(responseAfterPartialUpdateBooking);
    }
}

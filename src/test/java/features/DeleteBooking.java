package features;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import resources.AnnotationValues;
import resources.Comments;
import resources.StatusCode;
import service.Body;
import steps.BookingAPISteps;

@Tags(value = {@Tag(AnnotationValues.REGRESSION_TAG),
        @Tag(AnnotationValues.SMOKE_TAG), @Tag(AnnotationValues.DELETE_METHOD_TAG)})
@DisplayName("Delete booking")
public class DeleteBooking {
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
    @DisplayName("Delete booking")
    @Description("DELETE call to erase a booking in the database")
    @Feature("DELETE booking")
    public void deleteBooking() {
        Response response = bookingSteps.deleteBooking(bookingId, token);
        bookingSteps.validateStatusCode(StatusCode.SC_201.getValue(), response.getStatusCode());
        Allure.step(Comments.SUCCESS_DELETE.getValue());
        bookingSteps.validateResponseBodyIsNotNullAndNotEmpty(response);
        bookingSteps.validateResponseHeadersAreNotNullAndNotEmpty(response);
        bookingSteps.validateResponseHeadersHasExpectedFields(response);
        //Verify that the booking does not exist after deletion
        Response responseAfterDeletingBooking = bookingSteps.getBookingById(bookingId);
        bookingSteps.validateStatusCode(StatusCode.SC_404.getValue(), responseAfterDeletingBooking.getStatusCode());
        bookingSteps.validateResponseBodyIsNotNullAndNotEmpty(responseAfterDeletingBooking);
        bookingSteps.validateResponseHeadersAreNotNullAndNotEmpty(responseAfterDeletingBooking);
        bookingSteps.validateResponseHeadersHasExpectedFields(responseAfterDeletingBooking);
    }
}

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


@Tags(value = {@Tag(AnnotationValues.REGRESSION_TAG), @Tag(AnnotationValues.WORKFLOW_TAG)})
@DisplayName("Workflow validation")
public class WorkFlowTest {
    private static String token;
    private static int bookingId;
    private static BookingAPISteps bookingSteps = new BookingAPISteps();

    @BeforeAll
    public static void setupValues() {
        token = bookingSteps.getSessionToken();
    }

    @Test
    @DisplayName("Workflow")
    @Description("Different calls validations to change the status of a booking form created to deleted")
    @Feature("Workflow")
    public void workflow() {
        //Get service status
        Response pingResponse = bookingSteps.getHealthCheck();
        bookingSteps.validateStatusCode(StatusCode.SC_201.getValue(), pingResponse.getStatusCode());
        Allure.step(Comments.SUCCESS_HEALTH_CHECK.getValue());
        bookingSteps.validateResponseBodyIsNotNullAndNotEmpty(pingResponse);
        bookingSteps.validateResponseHeadersAreNotNullAndNotEmpty(pingResponse);
        bookingSteps.validateResponseHeadersHasExpectedFields(pingResponse);

        //Create booking
        Response createBookingResponse = bookingSteps.postCreateBooking();
        bookingSteps.validateStatusCode(StatusCode.SC_200.getValue(), createBookingResponse.getStatusCode());
        Allure.step(Comments.SUCCESS_POST.getValue());
        bookingSteps.validateResponseBodyIsNotNullAndNotEmpty(createBookingResponse);
        bookingSteps.validatePostCreateBookingResponseBodyHasExpectedFields(createBookingResponse);
        bookingSteps.validateResponseHeadersAreNotNullAndNotEmpty(createBookingResponse);
        bookingSteps.validateResponseHeadersHasExpectedFields(createBookingResponse);

        //Verify that the booking exists after call the create post method
        bookingId = Body.getIdFromCreatedBooking(createBookingResponse);
        Response responseAfterCreateBooking = bookingSteps.getBookingById(bookingId);
        bookingSteps.validateStatusCode(StatusCode.SC_200.getValue(), responseAfterCreateBooking.getStatusCode());
        bookingSteps.validateResponseBodyIsNotNullAndNotEmpty(responseAfterCreateBooking);
        bookingSteps.validateResponseBodyHasExpectedFields(responseAfterCreateBooking);
        bookingSteps.validateResponseBodyHasSameFieldValuesUsedOnRequestBody(responseAfterCreateBooking, "create");
        bookingSteps.validateResponseHeadersAreNotNullAndNotEmpty(responseAfterCreateBooking);
        bookingSteps.validateResponseHeadersHasExpectedFields(responseAfterCreateBooking);

        //Update booking
        Response updateBookingResponse = bookingSteps.putUpdateBooking(bookingId, token);
        bookingSteps.validateStatusCode(StatusCode.SC_200.getValue(), updateBookingResponse.getStatusCode());
        bookingSteps.validateResponseBodyIsNotNullAndNotEmpty(updateBookingResponse);
        bookingSteps.validateResponseBodyHasExpectedFields(updateBookingResponse);
        bookingSteps.validateResponseHeadersAreNotNullAndNotEmpty(updateBookingResponse);
        bookingSteps.validateResponseHeadersHasExpectedFields(updateBookingResponse);
        //Verify that the changes take effect after the update
        Response responseAfterUpdateBooking = bookingSteps.getBookingById(bookingId);
        bookingSteps.validateStatusCode(StatusCode.SC_200.getValue(), responseAfterUpdateBooking.getStatusCode());
        bookingSteps.validateResponseBodyIsNotNullAndNotEmpty(responseAfterUpdateBooking);
        bookingSteps.validateResponseBodyHasExpectedFields(responseAfterUpdateBooking);
        bookingSteps.validateResponseBodyHasSameFieldValuesUsedOnRequestBody(responseAfterUpdateBooking, "update");
        bookingSteps.validateResponseHeadersAreNotNullAndNotEmpty(responseAfterUpdateBooking);
        bookingSteps.validateResponseHeadersHasExpectedFields(responseAfterUpdateBooking);

        //Partial update booking
        Response partialUpdateResponse = bookingSteps.patchPartialUpdateBooking(bookingId, token);
        bookingSteps.validateStatusCode(StatusCode.SC_200.getValue(), partialUpdateResponse.getStatusCode());
        bookingSteps.validateResponseBodyIsNotNullAndNotEmpty(partialUpdateResponse);
        bookingSteps.validateResponseBodyHasExpectedFields(partialUpdateResponse);
        bookingSteps.validateResponseHeadersAreNotNullAndNotEmpty(partialUpdateResponse);
        bookingSteps.validateResponseHeadersHasExpectedFields(partialUpdateResponse);
        //Verify that the changes take effect after the partial update
        Response responseAfterPartialUpdateBooking = bookingSteps.getBookingById(bookingId);
        bookingSteps.validateStatusCode(StatusCode.SC_200.getValue(), responseAfterPartialUpdateBooking.getStatusCode());
        bookingSteps.validateResponseBodyIsNotNullAndNotEmpty(responseAfterPartialUpdateBooking);
        bookingSteps.validateResponseBodyHasExpectedFields(responseAfterPartialUpdateBooking);
        bookingSteps.validateResponseBodyHasSameFieldValuesUsedOnRequestBody(responseAfterPartialUpdateBooking, "partialUpdate");
        bookingSteps.validateResponseHeadersAreNotNullAndNotEmpty(responseAfterPartialUpdateBooking);
        bookingSteps.validateResponseHeadersHasExpectedFields(responseAfterPartialUpdateBooking);

        //Delete booking
        Response deleteBookingResponse = bookingSteps.deleteBooking(bookingId, token);
        bookingSteps.validateStatusCode(StatusCode.SC_201.getValue(), deleteBookingResponse.getStatusCode());
        Allure.step(Comments.SUCCESS_DELETE.getValue());
        bookingSteps.validateResponseBodyIsNotNullAndNotEmpty(deleteBookingResponse);
        bookingSteps.validateResponseHeadersAreNotNullAndNotEmpty(deleteBookingResponse);
        bookingSteps.validateResponseHeadersHasExpectedFields(deleteBookingResponse);
        //Verify that the booking does not exist after deletion update + comment
        Response responseAfterDeleteBooking = bookingSteps.getBookingById(bookingId);
        bookingSteps.validateStatusCode(StatusCode.SC_404.getValue(), responseAfterDeleteBooking.getStatusCode());
        bookingSteps.validateResponseBodyIsNotNullAndNotEmpty(responseAfterDeleteBooking);
        bookingSteps.validateResponseHeadersAreNotNullAndNotEmpty(responseAfterDeleteBooking);
        bookingSteps.validateResponseHeadersHasExpectedFields(responseAfterDeleteBooking);
    }
}

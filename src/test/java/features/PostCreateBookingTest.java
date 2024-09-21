package features;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import resources.AnnotationValues;
import resources.Comments;
import resources.StatusCode;
import service.Body;
import steps.BookingAPISteps;

@Tags(value = {@Tag(AnnotationValues.REGRESSION_TAG),
        @Tag(AnnotationValues.SMOKE_TAG), @Tag(AnnotationValues.POST_METHOD_TAG)})
@DisplayName("Post create booking")
public class PostCreateBookingTest {
    private BookingAPISteps bookingSteps = new BookingAPISteps();

    @Test
    @DisplayName("Post create booking")
    @Description("POST call to create a new booking in the database")
    @Feature("POST booking")
    public void postCreateBooking() {
        Response response = bookingSteps.postCreateBooking();
        bookingSteps.validateStatusCode(StatusCode.SC_200.getValue(), response.getStatusCode());
        Allure.step(Comments.SUCCESS_POST.getValue());
        bookingSteps.validateResponseBodyIsNotNullAndNotEmpty(response);
        bookingSteps.validatePostCreateBookingResponseBodyHasExpectedFields(response);
        bookingSteps.validateResponseHeadersAreNotNullAndNotEmpty(response);
        bookingSteps.validateResponseHeadersHasExpectedFields(response);
        //Verify that the booking exists after call the create post method
        Response responseAfterCreateBooking = bookingSteps.getBookingById(Body.getIdFromCreatedBooking(response));
        bookingSteps.validateStatusCode(StatusCode.SC_200.getValue(), responseAfterCreateBooking.getStatusCode());
        bookingSteps.validateResponseBodyIsNotNullAndNotEmpty(responseAfterCreateBooking);
        bookingSteps.validateResponseBodyHasExpectedFields(responseAfterCreateBooking);
        bookingSteps.validateResponseBodyHasSameFieldValuesUsedOnRequestBody(responseAfterCreateBooking, "create");
        bookingSteps.validateResponseHeadersAreNotNullAndNotEmpty(responseAfterCreateBooking);
        bookingSteps.validateResponseHeadersHasExpectedFields(responseAfterCreateBooking);
    }
}

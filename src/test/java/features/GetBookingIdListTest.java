package features;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import resources.AnnotationValues;
import resources.StatusCode;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.BookingAPISteps;

@Tags(value = {@Tag(AnnotationValues.REGRESSION_TAG),
        @Tag(AnnotationValues.SMOKE_TAG), @Tag(AnnotationValues.GET_METHOD_TAG)})
@DisplayName("Get booking id list")
@Epic("GET booking")
public class GetBookingIdListTest {
    private BookingAPISteps bookingSteps = new BookingAPISteps();

    @Test
    @DisplayName("Get all booking id list")
    @Description("GET call to retrieve all bookings id list")
    @Feature("GET booking Ids")
    public void getAllBookingIdList() {
        Response response = bookingSteps.getAllBookingIds();
        bookingSteps.validateStatusCode(StatusCode.SC_200.getValue(), response.getStatusCode());
        bookingSteps.validateResponseBodyIsNotNullAndNotEmpty(response);
        bookingSteps.validateGetAllBookingIdListResponseBodyHasExpectedFields(response);
        bookingSteps.validateResponseHeadersAreNotNullAndNotEmpty(response);
        bookingSteps.validateResponseHeadersHasExpectedFields(response);
    }
}

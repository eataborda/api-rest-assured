package features;

import org.junit.jupiter.api.*;
import resources.AnnotationValues;
import resources.StatusCode;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import service.Body;
import steps.BookingAPISteps;

@Tags(value = {@Tag(AnnotationValues.REGRESSION_TAG),
        @Tag(AnnotationValues.SMOKE_TAG), @Tag(AnnotationValues.GET_METHOD_TAG)})
@DisplayName("Get booking by id")
@Epic("GET booking")
public class GetBookingByIdTest {
    private static int bookingId;
    private BookingAPISteps bookingSteps = new BookingAPISteps();
    private static BookingAPISteps setupApiSteps = new BookingAPISteps();

    @BeforeAll
    public static void setupValues() {
        Response response = setupApiSteps.getAllBookingIds();
        bookingId = Body.getARandomExistingBookingId(response);
    }

    @Test
    @DisplayName("Get booking by id")
    @Description("GET call to retrieve a booking by id")
    @Feature("GET booking by id")
    public void getBookingById() {
        Response response = bookingSteps.getBookingById(bookingId);
        bookingSteps.validateStatusCode(StatusCode.SC_200.getValue(), response.getStatusCode());
        bookingSteps.validateResponseBodyIsNotNullAndNotEmpty(response);
        bookingSteps.validateResponseBodyHasExpectedFields(response);
        bookingSteps.validateResponseHeadersAreNotNullAndNotEmpty(response);
        bookingSteps.validateResponseHeadersHasExpectedFields(response);
    }
}

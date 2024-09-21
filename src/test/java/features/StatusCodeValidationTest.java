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


@Tags(value = {@Tag(AnnotationValues.REGRESSION_TAG), @Tag(AnnotationValues.STATUS_CODE_ALL_TAG)})
@DisplayName("Status code validation")
public class StatusCodeValidationTest {
    private static String token;
    private static int bookingId;
    private static BookingAPISteps bookingSteps = new BookingAPISteps();

    @BeforeAll
    public static void setupValues() {
        token = bookingSteps.getSessionToken();
        Response response = bookingSteps.postCreateBooking();
        bookingId = Body.getIdFromCreatedBooking(response);
        bookingSteps.deleteBooking(bookingId, token);
    }

    @Test
    @DisplayName("Status code validation - 200")
    @Description("Status code validation for 200")
    @Feature("Status code validation")
    @Tag(AnnotationValues.STATUS_CODE_200_TAG)
    public void statusCode200Validation() {
        Response statusCodeResponse = bookingSteps.getAllBookingIds();
        bookingSteps.validateStatusCode(StatusCode.SC_200.getValue(), statusCodeResponse.getStatusCode());
    }

    @Test
    @DisplayName("Status code validation - 201")
    @Description("Status code validation for 201")
    @Feature("Status code validation")
    @Tag(AnnotationValues.STATUS_CODE_201_TAG)
    public void statusCode201Validation() {
        Response statusCodeResponse = bookingSteps.postCreateBooking();
        bookingSteps.validateStatusCode(StatusCode.SC_200.getValue(), statusCodeResponse.getStatusCode());
        Allure.step(Comments.SUCCESS_POST.getValue());
    }

    @Test
    @DisplayName("Status code validation - 400")
    @Description("Status code validation for 400")
    @Feature("Status code validation")
    @Tag(AnnotationValues.STATUS_CODE_400_TAG)
    public void statusCode400Validation() {
        Response response = bookingSteps.postCreateBookingWithMalformedBody(StatusCode.SC_400.getValue());
        bookingSteps.validateStatusCode(StatusCode.SC_400.getValue(), response.getStatusCode());
    }

    @Test
    @DisplayName("Status code validation - 403")
    @Description("Status code validation for 403")
    @Feature("Status code validation")
    @Tag(AnnotationValues.STATUS_CODE_403_TAG)
    public void statusCode403Validation() {
        Response response = bookingSteps.putUpdateBookingAuthenticationHeader(bookingId);
        bookingSteps.validateStatusCode(StatusCode.SC_403.getValue(), response.getStatusCode());
    }

    @Test
    @DisplayName("Status code validation - 404")
    @Description("Status code validation for 404")
    @Feature("Status code validation")
    @Tag(AnnotationValues.STATUS_CODE_404_TAG)
    public void statusCode404Validation() {
        Response response = bookingSteps.getBookingById(bookingId);
        bookingSteps.validateStatusCode(StatusCode.SC_404.getValue(), response.getStatusCode());
    }

    @Test
    @DisplayName("Status code validation - 405")
    @Description("Status code validation for 405")
    @Feature("Status code validation")
    @Tag(AnnotationValues.STATUS_CODE_405_TAG)
    public void statusCode405Validation() {
        Response response = bookingSteps.putUpdateBooking(bookingId, token);
        bookingSteps.validateStatusCode(StatusCode.SC_405.getValue(), response.getStatusCode());
    }

    @Test
    @DisplayName("Status code validation - 500")
    @Description("Status code validation for 500")
    @Feature("Status code validation")
    @Tag(AnnotationValues.STATUS_CODE_500_TAG)
    public void statusCode500Validation() {
        Response response = bookingSteps.postCreateBookingWithMalformedBody(StatusCode.SC_500.getValue());
        bookingSteps.validateStatusCode(StatusCode.SC_500.getValue(), response.getStatusCode());
    }
}

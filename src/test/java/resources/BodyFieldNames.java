package resources;

public enum BodyFieldNames {
    FIRST_NAME("firstname"),
    LAST_NAME("lastname"),
    TOTAL_PRICE("totalprice"),
    DEPOSIT_PAID("depositpaid"),
    BOOKING_DATES("bookingdates"),
    CHECKIN("checkin"),
    CHECKOUT("checkout"),
    ADDITIONAL_NEEDS("additionalneeds"),
    BOOKING("booking"),
    BOOKING_ID("bookingid"),
    USERNAME("username"),
    PASSWORD("password");

    private final String value;

    BodyFieldNames(String value){this.value = value;}

    public String getValue(){return value;}
}

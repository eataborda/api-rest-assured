package resources;

public enum StatusCode {
    SC_200(200),
    SC_201(201),
    SC_400(400),
    SC_401(401),
    SC_403(403),
    SC_404(404),
    SC_405(405),
    SC_500(500);

    private final Integer value;

    StatusCode(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}

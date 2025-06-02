package resources;

public enum HeaderValues {
    APPLICATION_JSON("application/json"),
    CHARSET_UTF_8("charset=utf-8"),
    HEROKU("Heroku"),
    NEL_VALUE("{\"report_to\":\"heroku-nel\",\"response_headers\":[\"Via\"],\"max_age\":3600,\"success_fraction\":0.01,\"failure_fraction\":0.1}"),
    APPLICATION_JSON_CHARSET_UTF_8("application/json; charset=utf-8"),
    TEXT_PLAIN_CHARSET_UTF_8("text/plain; charset=utf-8"),
    EXPRESS("Express"),
    HEROKU_ROUTER_1_1("1.1 heroku-router");

    private final String value;

    HeaderValues(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

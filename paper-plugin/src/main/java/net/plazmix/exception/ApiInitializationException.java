package net.plazmix.exception;

public class ApiInitializationException extends RuntimeException {

    private final String fieldName;

    public ApiInitializationException(String fieldName) {
        super(String.format("Unable to initialize PlazmixAPI field %s!", fieldName));
        this.fieldName = fieldName;
    }

    public ApiInitializationException(Throwable cause, String fieldName) {
        super(String.format("Unable to initialize PlazmixAPI field %s! Details: %s", fieldName, cause.getMessage()));
        this.fieldName = fieldName;
    }

    public ApiInitializationException(Throwable cause) {
        super(String.format("Unable to initialize PlazmixAPI! Details: %s", cause.getMessage()));
        this.fieldName = "null";
    }

    public String getFieldName() {
        return fieldName;
    }
}

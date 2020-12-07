package ua.placebo.api.exception;

public class DrugNotFoundException extends RuntimeException {
    public DrugNotFoundException(String msg) {
        super(msg);
    }
}

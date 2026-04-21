package app.application.adapters.api.response;

import lombok.Getter;
import java.util.List;

@Getter
public class ErrorResponse {
    private final int status;
    private final String message;
    private final List<String> errors;

    public ErrorResponse(int status, String message) {
        this.status = status; this.message = message; this.errors = null;
    }
    public ErrorResponse(int status, String message, List<String> errors) {
        this.status = status; this.message = message; this.errors = errors;
    }
}

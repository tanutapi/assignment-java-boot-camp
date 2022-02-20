package dev.tanutapi.assignmentjavabootcamp.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Unable to create a new user")
public class CreateUserException extends RuntimeException {
    public CreateUserException(String message) {
        super(message);
    }
}

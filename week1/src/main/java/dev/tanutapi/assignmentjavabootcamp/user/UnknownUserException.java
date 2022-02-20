package dev.tanutapi.assignmentjavabootcamp.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such user found")
public class UnknownUserException extends RuntimeException {
    public UnknownUserException(String s) {
        super(s);
    }
}

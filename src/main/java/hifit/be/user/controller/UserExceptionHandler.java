package hifit.be.user.controller;

import hifit.be.user.exception.ExistingMemberException;
import hifit.be.util.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomResponse> error(IllegalArgumentException ex) {

        return ResponseEntity
                .badRequest()
                .body(new CustomResponse<>(
                        "fail",
                        400,
                        ex.getMessage()));
    }
}

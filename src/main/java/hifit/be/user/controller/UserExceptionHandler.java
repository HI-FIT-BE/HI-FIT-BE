package hifit.be.user.controller;

import hifit.be.user.exception.AuthorizationNullException;
import hifit.be.user.exception.NotBearerException;
import hifit.be.user.exception.TokenExpiredException;
import hifit.be.user.exception.TokenManipulatedException;
import hifit.be.util.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

    @ExceptionHandler(AuthorizationNullException.class)
    public ResponseEntity<CustomResponse> error(AuthorizationNullException ex) {

        return ResponseEntity
                .status(401)
                .body(new CustomResponse<>(
                        "fail",
                        40101,
                        ex.getMessage()));
    }

    @ExceptionHandler(NotBearerException.class)
    public ResponseEntity<CustomResponse> error(NotBearerException ex) {

        return ResponseEntity
                .status(401)
                .body(new CustomResponse<>(
                        "fail",
                        40102,
                        ex.getMessage()));
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<CustomResponse> error(TokenExpiredException ex) {

        return ResponseEntity
                .status(401)
                .body(new CustomResponse<>(
                        "fail",
                        40103,
                        ex.getMessage()));
    }

    @ExceptionHandler(TokenManipulatedException.class)
    public ResponseEntity<CustomResponse> error(TokenManipulatedException ex) {

        return ResponseEntity
                .status(401)
                .body(new CustomResponse<>(
                        "fail",
                        40104,
                        ex.getMessage()));
    }
}

package com.quangduy.identity_service.util.exception;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.quangduy.common_service.dto.response.ApiResponse;

import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHanlder {
    private static final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleAllException(Exception ex) {
        log.error("Exception: ", ex);
        ApiResponse<Object> res = new ApiResponse<Object>();
        res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        res.setMessage(ex.getMessage());
        res.setError("Internal Server Error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
    }

    @ExceptionHandler(value = {
            BadCredentialsException.class,
            UsernameNotFoundException.class
    })
    public ResponseEntity<ApiResponse<Object>> handleBadCreadentials(Exception ex) {
        ApiResponse<Object> res = new ApiResponse<Object>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError("Bad request");
        res.setMessage("Username/password không đúng");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(value = MyAppException.class)
    public ResponseEntity<ApiResponse<Object>> handleMyAppException(MyAppException ex) {
        ApiResponse<Object> res = new ApiResponse<Object>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError("Exception occurs...");
        res.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    // @ExceptionHandler(value = MethodArgumentNotValidException.class)
    // public ResponseEntity<ApiResponse<Object>>
    // handleNotValidException(MethodArgumentNotValidException ex) {
    // BindingResult result = ex.getBindingResult();
    // final List<FieldError> fieldErrors = result.getFieldErrors();
    // ApiResponse<Object> res = new ApiResponse<Object>();
    // res.setStatusCode(HttpStatus.BAD_REQUEST.value());
    // res.setError(ex.getBody().getDetail());

    // List<String> errors = fieldErrors.stream().map(f ->
    // f.getDefaultMessage()).collect(Collectors.toList());
    // res.setMessage(errors.size() > 1 ? errors : errors.get(0));
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    // }

    @ExceptionHandler(value = {
            NoResourceFoundException.class,
    })
    public ResponseEntity<ApiResponse<Object>> handleNotFoundExeption(Exception ex) {
        ApiResponse<Object> res = new ApiResponse<Object>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setMessage(ex.getMessage());
        res.setError("404 Not Found. URL may not exist...");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception) {
        String enumKey = exception.getFieldError().getDefaultMessage();

        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        Map<String, Object> attributes = null;
        try {
            errorCode = ErrorCode.valueOf(enumKey);

            var constraintViolation = exception.getBindingResult().getAllErrors().getFirst()
                    .unwrap(ConstraintViolation.class);

            attributes = constraintViolation.getConstraintDescriptor().getAttributes();

            log.info(attributes.toString());

        } catch (IllegalArgumentException e) {

        }

        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setStatusCode(errorCode.getStatusCode().value());
        apiResponse.setMessage(
                Objects.nonNull(attributes)
                        ? mapAttribute(errorCode.getMessage().toString(), attributes)
                        : errorCode.getMessage().toString());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));

        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }
}

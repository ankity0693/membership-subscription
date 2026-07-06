package com.assignment.firstclub.common.exception;

import com.assignment.firstclub.membership.exception.SubscriptionException;
import com.assignment.firstclub.membership.exception.TierException;
import com.assignment.firstclub.user.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // -----------------------------------------------------------------------
    // 400 Bad Request
    // -----------------------------------------------------------------------

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<ApiErrorResponse.FieldError> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> ApiErrorResponse.FieldError.builder()
                        .field(fe.getField())
                        .message(fe.getDefaultMessage())
                        .build())
                .toList();

        ApiErrorResponse body = ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Validation failed for one or more fields")
                .path(request.getRequestURI())
                .fieldErrors(fieldErrors)
                .build();

        log.warn("Validation error on {}: {}", request.getRequestURI(), fieldErrors);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleUnreadableBody(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        ApiErrorResponse body = buildError(HttpStatus.BAD_REQUEST,
                "Malformed or missing request body", request.getRequestURI());
        log.warn("Unreadable request body on {}: {}", request.getRequestURI(), ex.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingParam(
            MissingServletRequestParameterException ex,
            HttpServletRequest request) {

        ApiErrorResponse body = buildError(HttpStatus.BAD_REQUEST,
                String.format("Required parameter '%s' is missing", ex.getParameterName()),
                request.getRequestURI());
        log.warn("Missing request parameter on {}: {}", request.getRequestURI(), ex.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {

        String message = String.format(
                "Parameter '%s' has an invalid value: '%s'", ex.getName(), ex.getValue());
        ApiErrorResponse body = buildError(HttpStatus.BAD_REQUEST, message, request.getRequestURI());
        log.warn("Type mismatch on {}: {}", request.getRequestURI(), ex.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        ApiErrorResponse body = buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
        log.warn("Illegal argument on {}: {}", request.getRequestURI(), ex.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    // -----------------------------------------------------------------------
    // 404 Not Found
    // -----------------------------------------------------------------------

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotFound(
            UserNotFoundException ex,
            HttpServletRequest request) {

        ApiErrorResponse body = buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
        log.info("User not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        ApiErrorResponse body = buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
        log.info("Resource not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // -----------------------------------------------------------------------
    // 409 Conflict
    // -----------------------------------------------------------------------

    @ExceptionHandler(SubscriptionException.class)
    public ResponseEntity<ApiErrorResponse> handleSubscription(
            SubscriptionException ex,
            HttpServletRequest request) {

        ApiErrorResponse body = buildError(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI());
        log.warn("Subscription conflict on {}: {}", request.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    // -----------------------------------------------------------------------
    // 422 Unprocessable Entity
    // -----------------------------------------------------------------------

    @ExceptionHandler({TierException.class, UneligibleTierException.class})
    public ResponseEntity<ApiErrorResponse> handleTier(
            MembershipApplicationException ex,
            HttpServletRequest request) {

        ApiErrorResponse body = buildError(HttpStatus.valueOf(422),
                ex.getMessage(), request.getRequestURI());
        log.warn("Tier evaluation error on {}: {}", request.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.valueOf(422)).body(body);
    }

    // -----------------------------------------------------------------------
    // 500 Internal Server Error  (catch-all)
    // -----------------------------------------------------------------------

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(
            Exception ex,
            HttpServletRequest request) {

        ApiErrorResponse body = buildError(HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please try again later.",
                request.getRequestURI());
        log.error("Unhandled exception on {}: ", request.getRequestURI(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    private ApiErrorResponse buildError(HttpStatus status, String message, String path) {
        return ApiErrorResponse.builder()
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(path)
                .build();
    }
}

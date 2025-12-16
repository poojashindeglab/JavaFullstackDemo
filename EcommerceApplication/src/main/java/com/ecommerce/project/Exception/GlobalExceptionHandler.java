package com.ecommerce.project.Exception;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.ecommerce.project.DTO.APIResponse;
import com.ecommerce.project.utils.AppConstant;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handleException(MethodArgumentNotValidException e) {
		Map<String, String> errors = new HashMap<String, String>();
		e.getBindingResult().getFieldErrors()
				.forEach(error -> {
					String field = error.getField();
					String message = error.getDefaultMessage();
					errors.put(field, message);
				});
		return new ResponseEntity<Map<String,String>>(errors, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<APIResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
		 String message = e.getMessage();
		 APIResponse apiResponse = new APIResponse(message, false);
		return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(APIException.class)
	public ResponseEntity<APIResponse> handleAPIException(APIException e) {
		String message = e.getMessage();
		APIResponse apiResponse = new APIResponse(message, false);
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(final ConstraintViolationException exception, WebRequest request) {
		Map<String, String> errors = new HashMap<>();

        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(field, message);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
}

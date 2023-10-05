//package com.university.apigateway.exception;
//
//import com.university.attendanceservice.response.Response;
//import feign.FeignException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static com.university.attendanceservice.enums.Constants.EXCEPTION;
//
//@RestControllerAdvice
//@Slf4j
//public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Response> globalExceptionHandler(Exception ex) {
//        log.error(EXCEPTION.getValue(), ex.getMessage());
//        Response response = new Response(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<Response> resourceNotFoundException(NotFoundException ex) {
//        log.error(EXCEPTION.getValue(), ex.getMessage());
//        Response response = new Response(HttpStatus.NOT_FOUND, ex.getMessage(), null);
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//    }
//
//    @Override
//    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                               HttpHeaders headers, HttpStatusCode status,
//                                                               WebRequest request) {
//        log.error(EXCEPTION.getValue(), ex.getMessage());
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        Response response = new Response(HttpStatus.BAD_REQUEST, errors.toString(), null);
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(FeignException.class)
//    public ResponseEntity<Response> handleFeignStatusException(FeignException ex) {
//        log.error(EXCEPTION.getValue(), ex.getMessage());
//        Response response = new Response(HttpStatus.valueOf(ex.status()), ex.getMessage(), null);
//        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.status()));
//    }
//
//    @ExceptionHandler(BadRequestException.class)
//    public ResponseEntity<Response> badRequestException(BadRequestException ex) {
//        log.error(EXCEPTION.getValue(), ex.getMessage());
//        Response response = new Response(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//}

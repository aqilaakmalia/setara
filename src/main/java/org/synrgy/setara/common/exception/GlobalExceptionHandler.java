//package org.synrgy.setara.common.exception;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.synrgy.setara.common.dto.BaseResponse;
//import org.synrgy.setara.auth.exception.AuthenticationException;
//import org.synrgy.setara.transaction.exception.TransactionExceptions;
//
//@RestControllerAdvice
//@Order(2)
//public class GlobalExceptionHandler {
//    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//
//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<BaseResponse<?>> handleAuthenticationException(AuthenticationException ex) {
//        logger.error("Authentication error: {}", ex.getMessage());
//        BaseResponse<?> response = BaseResponse.failure(HttpStatus.UNAUTHORIZED, ex.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<BaseResponse<?>> handleBadCredentialsException(BadCredentialsException ex) {
//        logger.error("Bad credentials: {}", ex.getMessage());
//        BaseResponse<?> response = BaseResponse.failure(HttpStatus.UNAUTHORIZED, "Bad credentials");
//        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<BaseResponse<?>> handleException(Exception ex) {
//        logger.error("Unexpected error: {}", ex.getMessage());
//        BaseResponse<?> response = BaseResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(TransactionExceptions.DestinationEwalletUserNotFoundException.class)
//    public ResponseEntity<BaseResponse<?>> handleDestinationEwalletUserNotFoundException(TransactionExceptions.DestinationEwalletUserNotFoundException ex) {
//        logger.error("Unexpected error: {}", ex.getMessage());
//        return new ResponseEntity<>(BaseResponse.failure(HttpStatus.NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
//    }
//}

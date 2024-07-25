package org.synrgy.setara.transaction.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.synrgy.setara.common.dto.BaseResponse;
import org.synrgy.setara.transaction.exception.TopUpExceptions;

@RestControllerAdvice
//@Order(1)
public class TopUpAdvice {

    @ExceptionHandler(TopUpExceptions.UserNotFoundException.class)
    public ResponseEntity<BaseResponse<?>> handleUserNotFoundException(TopUpExceptions.UserNotFoundException ex) {
        return new ResponseEntity<>(BaseResponse.failure(HttpStatus.NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TopUpExceptions.DestinationEwalletUserNotFoundException.class)
    public ResponseEntity<BaseResponse<?>> handleDestinationEwalletUserNotFoundException(TopUpExceptions.DestinationEwalletUserNotFoundException ex) {
        return new ResponseEntity<>(BaseResponse.failure(HttpStatus.NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TopUpExceptions.InvalidMpinException.class)
    public ResponseEntity<BaseResponse<?>> handleInvalidMpinException(TopUpExceptions.InvalidMpinException ex) {
        return new ResponseEntity<>(BaseResponse.failure(HttpStatus.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TopUpExceptions.InvalidTopUpAmountException.class)
    public ResponseEntity<BaseResponse<?>> handleInvalidTopUpAmountException(TopUpExceptions.InvalidTopUpAmountException ex) {
        return new ResponseEntity<>(BaseResponse.failure(HttpStatus.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TopUpExceptions.InsufficientBalanceException.class)
    public ResponseEntity<BaseResponse<?>> handleInsufficientBalanceException(TopUpExceptions.InsufficientBalanceException ex) {
        return new ResponseEntity<>(BaseResponse.failure(HttpStatus.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TopUpExceptions.DestinationAccountNotFoundException.class)
    public ResponseEntity<BaseResponse<?>> handleDestinationAccountNotFoundException(TopUpExceptions.DestinationAccountNotFoundException ex) {
        return new ResponseEntity<>(BaseResponse.failure(HttpStatus.NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<?>> handleGenericException(Exception ex) {
        return new ResponseEntity<>(BaseResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package learn.goodgames.controllers;

import learn.goodgames.controllers.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.dao.DataAccessException;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Data Integrity Violation Exception
    // Exception thrown when an attempt to insert or update data results in violation of an integrity constraint.
    // Note that this is not purely a relational concept; integrity constraints such as unique primary keys are required by most database types.
    // Serves as a superclass for more specific exceptions, e.g. DuplicateKeyException.
    // However, it is generally recommended to handle DataIntegrityViolationException itself instead of relying on specific exception subclasses.
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleException(DataIntegrityViolationException ex) {

        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse("There is an issue with an integrity constraint. Please reach out to your supervisor for assistance."),
                HttpStatus.BAD_REQUEST);
    }

    // DataAccessException is the super class of many Spring database exceptions
    // including BadSqlGrammarException.
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleException(DataAccessException ex) {

        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse("We can't show you the details, but something went wrong in our database. Sorry :("),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // IllegalArgumentException is the super class for many Java exceptions
    // including all formatting (number, date) exceptions.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException ex) {

        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse(ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // This is our absolute last resort. Yuck.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {

        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse("Something went wrong on our end. Your request failed. :("),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
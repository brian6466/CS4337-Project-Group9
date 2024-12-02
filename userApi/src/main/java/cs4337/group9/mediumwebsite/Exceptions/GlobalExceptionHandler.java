package cs4337.group9.mediumwebsite.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                "User",
                "The requested user could not be found"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                "User",
                "Account already exists for this email. Please use a different email address."  // Details
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                "Post Not Found",
                ex.getResourceName(),
                "The resource with ID " + ex.getResourceId() + " could not be found. Please verify the resource ID and try again."  // Details
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {

        System.err.println("Exception occurred: " + ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Try again later.",
                "General Error",
                "The server encountered an error: " + ex.getMessage() + ". Please contact support if the issue persists."
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

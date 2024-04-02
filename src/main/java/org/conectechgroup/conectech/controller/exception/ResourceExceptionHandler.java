package org.conectechgroup.conectech.controller.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.conectechgroup.conectech.service.exception.ObjectNotFoundException;
import org.conectechgroup.conectech.service.exception.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Não encontrado", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<StandardError> userAlreadyExists(UserAlreadyExistsException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Conflict", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
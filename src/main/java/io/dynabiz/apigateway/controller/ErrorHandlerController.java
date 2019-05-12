package io.dynabiz.apigateway.controller;


import com.as.exception.ServerException;
import com.as.web.response.GeneralResponse;
import feign.codec.DecodeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@RestControllerAdvice
public class ErrorHandlerController {
    @ExceptionHandler(value = { ServerException.class })
    @ResponseStatus(HttpStatus.OK)
    public GeneralResponse serverException(ServerException ex, WebRequest req) {
        ex.printStackTrace();
        return new GeneralResponse(ex);
    }
    @ExceptionHandler(value = { DecodeException.class })
    @ResponseStatus(HttpStatus.OK)
    public GeneralResponse serverException(DecodeException ex, WebRequest req) {
        if(ex.getCause() instanceof  ServerException)
            return new GeneralResponse((ServerException) ex.getCause());
        else
            return new GeneralResponse((Exception) ex.getCause());
    }


    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(HttpStatus.OK)
    public GeneralResponse serverException(Exception ex, WebRequest req) {
        ex.printStackTrace();
        return new GeneralResponse(ex);
    }



}

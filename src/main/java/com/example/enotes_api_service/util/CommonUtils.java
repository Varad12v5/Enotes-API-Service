package com.example.enotes_api_service.util;

import com.example.enotes_api_service.Handler.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CommonUtils {

    public static ResponseEntity<?> createBuildResponse(Object data, HttpStatus status)
    {
        GenericResponse response=GenericResponse.builder()
                .responseStatus(status)
                .status("Success")
                .message("Success")
                .data(data)
                .build();

        return response.create();
    }

    public static ResponseEntity<?> createBuildResponseMessage(String message, HttpStatus status)
    {
        GenericResponse response=GenericResponse.builder()
                .responseStatus(status)
                .status("Success")
                .message(message)
                .build();

        return response.create();
    }

    public static ResponseEntity<?> createErrorResponse(Object data, HttpStatus status)
    {
        GenericResponse response=GenericResponse.builder()
                .responseStatus(status)
                .status("Failed")
                .message("Failed")
                .data(data)
                .build();

        return response.create();
    }

    public static ResponseEntity<?> createErrorResponseMessage(String message, HttpStatus status)
    {
        GenericResponse response=GenericResponse.builder()
                .responseStatus(status)
                .status("Failed")
                .message(message)
                .build();

        return response.create();
    }
}

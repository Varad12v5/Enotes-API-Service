package com.example.enotes_api_service.Handler;


import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenericResponse {

    private String status;   //success,fail

    private String message; // custom message like saved success etc

    private Object data; //data

    private HttpStatus responseStatus;


    public ResponseEntity<?> create()
    {
        Map<String,Object> map=new LinkedHashMap<>();
        map.put("status",status);
        map.put("message",message);
        if (!ObjectUtils.isEmpty(data))
        {
            map.put("data",data);
        }

        return new ResponseEntity<>(map, responseStatus);
    }

}

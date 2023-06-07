package com.github.api.utilities;

import com.github.api.model.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class XmlConverter {
    public static ResponseEntity<ErrorResponse> convertResponseFromXmlToJson(ErrorResponse errorResponseXml) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return ResponseEntity.status(406).headers(headers).body(errorResponseXml);
    }
}

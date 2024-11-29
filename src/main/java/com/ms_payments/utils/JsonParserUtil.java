package com.ms_payments.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ms_payments.exception.InternalException;
import com.ms_payments.messages.MessageEnum;
import org.springframework.http.HttpStatus;

import java.nio.charset.StandardCharsets;

public class JsonParserUtil {

//    TODO: Corrigir o erro da conversão de datas LocalDateTime
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static String toJson(Object obj) {
        if (obj == null) {
            throw new InternalException(MessageEnum.JSON_PARSE_ERROR, HttpStatus.BAD_GATEWAY);
        }

        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
           throw new InternalException(MessageEnum.JSON_PARSE_ERROR, HttpStatus.BAD_GATEWAY);
        }
    }

    public static <T> T fromBytes(byte[] input, Class<T> outputType) {
        if (input == null) {
            throw new InternalException(MessageEnum.JSON_PARSE_ERROR, HttpStatus.BAD_GATEWAY);
        }

        try {
            String value = new String(input, StandardCharsets.UTF_8);
            return objectMapper.readValue(value, outputType);
        } catch (JsonProcessingException e) {
            throw new InternalException(MessageEnum.JSON_PARSE_ERROR, HttpStatus.BAD_GATEWAY);
        }
    }
}
package com.ms_payments.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms_payments.config.ObjectMapperConfig;
import com.ms_payments.exception.InternalException;
import com.ms_payments.messages.MessageEnum;
import org.springframework.http.HttpStatus;

import java.nio.charset.StandardCharsets;

public class JsonParserUtil {

    private static final ObjectMapper objectMapper = new ObjectMapperConfig().objectMapper();

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
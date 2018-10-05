package com.common.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

/**
 * Simple utility helper for converting stuff to json.
 */
public class JSON {

    private static ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    static {
        mapper.registerModule(new Jdk8Module());
    }

    private JSON() {
        //NO create, only static...
    }

    public static ResponseEntity<JsonNode> message(HttpStatus status, String message, Object... args) {
        return ResponseEntity.status(status).body(JSON.message(String.format(message, args)));
    }

    public static ResponseEntity<JsonNode> message(HttpStatus status, Optional<?> message) {
        return message.isPresent() ? ResponseEntity.status(status).body(JSON.parse(message.get()))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(JSON.parse("Not Present"));
    }

    public static ResponseEntity<JsonNode> message(HttpStatus status, Object o) {
        if (o instanceof String) {
            o = message((String) o);
        }
        return ResponseEntity.status(status).body(JSON.parse(o));
    }

    public static JsonNode message(String message) {
        return parse(new JsonMessage(message));
    }

    public static JsonNode parse(Object o) {
        return mapper.valueToTree(o);
    }

    public static class JsonMessage {
        private String message;

        public JsonMessage() {
        }

        public JsonMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static ResponseEntity<JsonNode> message(RestResponse<?> restResponse) {
        if(restResponse.getResponse() != null) {
            Object response = restResponse.getResponse();
            if (response instanceof ArrayNode) {
                // ensure that we never return an array.
                ObjectNode o = mapper.createObjectNode();
                o.set("result", (ArrayNode) response);
                return JSON.message(HttpStatus.OK, o);
            } else if (response instanceof List) {
                ObjectNode o = mapper.createObjectNode();
                o.set("result", JSON.parse(response));
                return JSON.message(HttpStatus.OK, o);
            } else {
                return JSON.message(HttpStatus.OK, response);
            }
        } else {
            return JSON.message(restResponse.getStatusCode(), restResponse.getErrorMessage());
        }
    }
}
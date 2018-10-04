package com.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import okhttp3.*;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public class RestCaller {

    private OkHttpClient client;
    private ObjectMapper objectMapper;

    public RestCaller() {
        client = new OkHttpClient();
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new Jdk8Module());
    }

    public RestResponse<JsonNode> getCall(String url) {
        return getCall(url, JsonNode.class);
    }

    public <T> RestResponse<T> getCall(String url, Class<T> responseClass) {
        try {
            Response response = get(url);

            RestResponse<T> restResponse = new RestResponse<>();

            if (response.code() == 200) {
                restResponse.setResponse(extractResponseBody(response, responseClass));
            } else {
                restResponse.setErrorMessage(extractResponseBody(response, StringResponse.class).getMessage());
            }
            restResponse.setStatusCode(HttpStatus.valueOf(response.code()));
            return restResponse;
        } catch(Exception e) {
            return RestResponse.errorResponse(e);
        }
    }

    public <T> RestResponse<T> getCall(String url, TypeReference<T> typeReference) {
        try {
            Response response = get(url);

            RestResponse<T> restResponse = new RestResponse<>();

            if (response.code() == 200) {
                restResponse.setResponse(extractResponseBody(response, typeReference));
            } else {
                restResponse.setErrorMessage(extractResponseBody(response, StringResponse.class).getMessage());
            }
            restResponse.setStatusCode(HttpStatus.valueOf(response.code()));
            return restResponse;
        } catch(Exception e) {
            return RestResponse.errorResponse(e);
        }
    }

    private Response get(String url) throws IOException {
        Request request = new Request.Builder().url(url).get().build();
        return client.newCall(request).execute();
    }

    public RestResponse<JsonNode> postCall(String url, Object requestBody) {
        return postCall(url, requestBody, JsonNode.class);
    }

    @SuppressWarnings("unchecked")
    public <T> RestResponse<T> postCall(String url, Object requestBody, Class<T> responseClass) {
        try {
            Response response = post(url, requestBody);

            RestResponse<T> restResponse = new RestResponse<>();

            if (response.code() == 200) {
                if (String.class.equals(responseClass)) {
                    restResponse.setResponse((T) extractResponseBody(response, StringResponse.class).getMessage());
                } else {
                    restResponse.setResponse(extractResponseBody(response, responseClass));
                }
            } else {
                restResponse.setErrorMessage(extractResponseBody(response, StringResponse.class).getMessage());
            }

            restResponse.setStatusCode(HttpStatus.valueOf(response.code()));
            return restResponse;
        } catch (Exception e) {
            return RestResponse.errorResponse(e);
        }
    }

    public <T> RestResponse<T> postCall(String url, Object requestBody, TypeReference<T> typeReference) {
        try {
            Response response = post(url, requestBody);

            RestResponse<T> restResponse = new RestResponse<>();
            if (response.code() == 200) {
                restResponse.setResponse(extractResponseBody(response, typeReference));
            } else {
                restResponse.setErrorMessage(extractResponseBody(response, StringResponse.class).getMessage());
            }
            restResponse.setStatusCode(HttpStatus.valueOf(response.code()));
            return restResponse;
        } catch (Exception e) {
            return RestResponse.errorResponse(e);
        }
    }

    private Response post(String url, Object requestBody) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                objectMapper.writeValueAsString(requestBody));
        Request request = new Request.Builder().url(url).post(body).build();
        return client.newCall(request).execute();
    }

    public RestResponse<JsonNode> deleteCall(String url) {
        return deleteCall(url, JsonNode.class);
    }

    public <T> RestResponse<T> deleteCall(String url, Class<T> responseClass) {
        try {
            Response response = delete(url);

            RestResponse<T> restResponse = new RestResponse<>();

            if (response.code() == 200) {
                restResponse.setResponse(extractResponseBody(response, responseClass));
            } else {
                restResponse.setErrorMessage(extractResponseBody(response, StringResponse.class).getMessage());
            }
            restResponse.setStatusCode(HttpStatus.valueOf(response.code()));
            return restResponse;
        } catch (Exception e) {
            return RestResponse.errorResponse(e);
        }
    }

    public <T> RestResponse<T> deleteCall(String url, TypeReference<T> typeReference) {
        try {
            Response response = delete(url);

            RestResponse<T> restResponse = new RestResponse<>();

            if (response.code() == 200) {
                restResponse.setResponse(extractResponseBody(response, typeReference));
            } else {
                restResponse.setErrorMessage(extractResponseBody(response, StringResponse.class).getMessage());
            }
            restResponse.setStatusCode(HttpStatus.valueOf(response.code()));
            return restResponse;
        } catch(Exception e) {
            return RestResponse.errorResponse(e);
        }
    }

    private Response delete(String url) throws IOException {
        Request request = new Request.Builder().url(url).delete().build();
        return client.newCall(request).execute();
    }

    public RestResponse<JsonNode> putCall(String url, Object requestBody) {
        return putCall(url, requestBody, JsonNode.class);
    }

    @SuppressWarnings("unchecked")
    public <T> RestResponse<T> putCall(String url, Object requestBody, Class<T> responseClass) {
        try {
            Response response = put(url, requestBody);

            RestResponse<T> restResponse = new RestResponse<>();

            if (response.code() == 200) {
                if (String.class.equals(responseClass)) {
                    restResponse.setResponse((T) extractResponseBody(response, StringResponse.class).getMessage());
                } else {
                    restResponse.setResponse(extractResponseBody(response, responseClass));
                }
            } else {
                restResponse.setErrorMessage(extractResponseBody(response, StringResponse.class).getMessage());
            }

            restResponse.setStatusCode(HttpStatus.valueOf(response.code()));
            return restResponse;
        } catch (Exception e) {
            return RestResponse.errorResponse(e);
        }
    }

    public <T> RestResponse<T> putCall(String url, Object requestBody, TypeReference<T> typeReference) {
        try {
            Response response = put(url, requestBody);

            RestResponse<T> restResponse = new RestResponse<>();
            if (response.code() == 200) {
                restResponse.setResponse(extractResponseBody(response, typeReference));
            } else {
                restResponse.setErrorMessage(extractResponseBody(response, StringResponse.class).getMessage());
            }
            restResponse.setStatusCode(HttpStatus.valueOf(response.code()));
            return restResponse;
        } catch (Exception e) {
            return RestResponse.errorResponse(e);
        }
    }

    private Response put(String url, Object requestBody) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                objectMapper.writeValueAsString(requestBody));
        Request request = new Request.Builder().url(url).put(body).build();
        return client.newCall(request).execute();
    }

    private <T> T extractResponseBody(Response response, Class<T> responseClass) throws IOException {
        return objectMapper.readValue(response.body().string(), responseClass);
    }

    private <T> T extractResponseBody(Response response, TypeReference<T> typeReference) throws IOException {
        return objectMapper.readValue(response.body().string(), typeReference);
    }
}

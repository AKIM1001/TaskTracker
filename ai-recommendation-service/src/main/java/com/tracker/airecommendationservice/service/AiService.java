package com.tracker.airecommendationservice.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tracker.airecommendationservice.exceptions.ErrorAiApiResponseException;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiService {
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    private final Map<String, String> aiApiConfig;
    private final PrimeService primeService;

    private void fillRequestBody(JsonObject requestBody, String prompt) {
        JsonArray messages = new JsonArray();

        JsonObject message = new JsonObject();

        requestBody.addProperty("model", aiApiConfig.get("model"));

        requestBody.addProperty("temperature",
                Float.parseFloat(aiApiConfig.get("temperature")));

        message.addProperty("role", aiApiConfig.get("sender role"));

        message.addProperty("content", prompt);

        messages.add(message);

        requestBody.add("messages", messages);
    }

    private RequestBody getAiRequestBody(String prompt) {
        JsonObject requestBody = new JsonObject();

        fillRequestBody(requestBody, prompt);

        return RequestBody.create(gson.toJson(requestBody),
                MediaType.parse(aiApiConfig.get("message type")));
    }

    private Request getAiRequest(String prompt) {
        RequestBody requestBody = getAiRequestBody(prompt);

        return new Request.Builder()
                .url(aiApiConfig.get("url"))
                .post(requestBody)
                .addHeader("Content-Type", aiApiConfig.get("message type"))
                .addHeader("Authorization", "Bearer " + aiApiConfig.get("auth key"))
                .build();
    }

    private String getErrorMessage(JsonObject responseJson) {
        return responseJson.getAsJsonObject("error")
                .get("message").getAsString();
    }

    public String getAiResponse(String prompt) throws IOException, ErrorAiApiResponseException {
        Response response = client.newCall(getAiRequest(prompt)).execute();

        JsonObject responseJson = gson.fromJson(response.body().string(), JsonObject.class);

        if (!responseJson.has("choices")) {
            throw new ErrorAiApiResponseException(getErrorMessage(responseJson));
        }

        return responseJson
                .getAsJsonArray("choices")
                .get(0)
                .getAsJsonObject()
                .getAsJsonObject("message")
                .get("content")
                .getAsString();
    }
}


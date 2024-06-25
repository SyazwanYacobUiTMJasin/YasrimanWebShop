package com.masbro.yasriman.api.service;

import org.springframework.stereotype.Service;

import com.masbro.yasriman.model.ApiRequest;
import com.masbro.yasriman.model.ChatGptResponse;
import com.masbro.yasriman.model.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@AllArgsConstructor
public class AiRequestProcessor {

    private final GptRequestBuilder gpt;

    public ApiResponse process(ApiRequest request) {
        log.info("### -> Incoming request: {}", request.getMessage());
        ChatGptResponse response = gpt.executeRequest(request);
        log.info("### -> ChatGPT response: {}", response);
        return new ApiResponse(response);
    }
}
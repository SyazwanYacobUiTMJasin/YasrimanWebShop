package com.masbro.yasriman.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import com.masbro.yasriman.api.service.AiRequestProcessor;
import com.masbro.yasriman.model.ApiRequest;
import com.masbro.yasriman.model.ApiResponse;
@RequestMapping(value = "/api/v1")
@RestController
@AllArgsConstructor
public class ApiController {


   private final AiRequestProcessor aiProcessor;


    @PostMapping(value = "/ai/request")
    public ResponseEntity<ApiResponse> get(@RequestBody ApiRequest request) { 
 return ResponseEntity.ok(aiProcessor.process(request));
 }
}
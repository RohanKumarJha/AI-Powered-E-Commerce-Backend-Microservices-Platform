package com.ecommerce.controller;


import com.ecommerce.dto.request.ChatRequestDto;
import com.ecommerce.dto.response.ChatResponseDto;
import com.ecommerce.service.ChatService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {


    private final ChatService chatService;




    @PostMapping
    public ResponseEntity<ChatResponseDto> askQuestion(
            @Valid @RequestBody ChatRequestDto request
    ){

        return new ResponseEntity<>(
                chatService.askQuestion(request),
                HttpStatus.CREATED
        );

    }





    @GetMapping("/{id}")
    public ResponseEntity<ChatResponseDto> getChatResponse(
            @PathVariable Long id
    ){

        return ResponseEntity.ok(
                chatService.getChatResponseById(id)
        );

    }

}
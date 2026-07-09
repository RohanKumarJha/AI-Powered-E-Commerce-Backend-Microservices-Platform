package com.ecommerce.service;

import com.ecommerce.dto.request.ChatRequestDto;
import com.ecommerce.dto.response.ChatResponseDto;

public interface ChatService {


    ChatResponseDto askQuestion(
            ChatRequestDto request
    );


    ChatResponseDto getChatResponseById(
            Long chatResponseId
    );

}
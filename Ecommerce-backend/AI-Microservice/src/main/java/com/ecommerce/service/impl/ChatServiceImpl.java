package com.ecommerce.service.impl;

import com.ecommerce.dto.request.ChatRequestDto;
import com.ecommerce.dto.response.ChatResponseDto;
import com.ecommerce.service.ChatService;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {
    @Override
    public ChatResponseDto askQuestion(ChatRequestDto request) {
        return null;
    }

    @Override
    public ChatResponseDto getChatResponseById(Long chatResponseId) {
        return null;
    }
}

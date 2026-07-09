package com.ecommerce.mapper;

import com.ecommerce.dto.response.ChatResponseDto;
import com.ecommerce.model.ChatResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class ChatMapper {


    private final ModelMapper modelMapper;


    public ChatMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public ChatResponseDto toResponse(ChatResponse chatResponse) {


        ChatResponseDto response =
                modelMapper.map(
                        chatResponse,
                        ChatResponseDto.class
                );


        if(chatResponse.getChatRequest() != null){

            response.setChatRequestId(
                    chatResponse.getChatRequest()
                            .getChatRequestId()
            );
        }


        return response;
    }



    public ChatResponse toEntity(ChatResponseDto response) {

        return modelMapper.map(
                response,
                ChatResponse.class
        );
    }

}
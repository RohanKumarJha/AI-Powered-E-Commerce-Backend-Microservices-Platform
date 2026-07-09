package com.ecommerce.mapper;


import com.ecommerce.dto.response.ReviewSummaryResponse;
import com.ecommerce.model.ReviewSummary;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class ReviewSummaryMapper {


    private final ModelMapper modelMapper;


    public ReviewSummaryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }



    public ReviewSummaryResponse toResponse(
            ReviewSummary reviewSummary
    ) {


        return modelMapper.map(
                reviewSummary,
                ReviewSummaryResponse.class
        );

    }




    public ReviewSummary toEntity(
            ReviewSummaryResponse response
    ) {


        return modelMapper.map(
                response,
                ReviewSummary.class
        );

    }

}
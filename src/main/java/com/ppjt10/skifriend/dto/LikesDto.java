package com.ppjt10.skifriend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class LikesDto {
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseDto{
        private Long userId;
    }

}

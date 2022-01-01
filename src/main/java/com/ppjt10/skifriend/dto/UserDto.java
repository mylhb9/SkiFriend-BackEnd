package com.ppjt10.skifriend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestDto{
        private String username;
        private String password;
        private String phoneNum;
        private String nickname;
        private String gender;
        private String ageRange;
        private String career;
        private String selfIntro;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProfileRequestDto{
        private String gender;
        private String ageRange;
        private String career;
        private String selfIntro;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PasswordDto{
        private String password;
        private String newPassword;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IdCheckDto{
        private String username;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NicknameCheckDto{
        private String nickname;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginResponseDto{
        private Long userId;
        private String nickname;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateRequestDto{
        private String password;
        private String nickname;
        private String career;
        private String selfIntro;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseDto{
        private String username;
        private String phoneNum;
        private String nickname;
        private String profileImg;
        private String vacImg;
        private String gender;
        private String ageRange;
        private String career;
        private String selfIntro;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OtherRequestDto{
        private Long roomId;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OtherResponseDto{
        private String nickname;
        private String profileImg;
        private String vacImg;
        private String gender;
        private String ageRange;
        private String career;
        private String selfIntro;
    }
}

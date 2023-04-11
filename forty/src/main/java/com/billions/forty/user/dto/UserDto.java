package com.billions.forty.user.dto;

import lombok.*;


public class UserDto {
    @Getter
    @RequiredArgsConstructor
    @ToString
    public static class UserPostDto{

        private String id;
        private String password;
        private String nickname;
        private String email;
        private String phone;
    }
    @Getter
    @AllArgsConstructor
    public static class UserPatchDto{
        private String password;
        private String nickname;
    }
    @AllArgsConstructor
    @Getter
    public static class UserResponseDto{
        private Long userId;
        private String id;
        private String nickname;
        private String email;
        private String phone;
    }
}

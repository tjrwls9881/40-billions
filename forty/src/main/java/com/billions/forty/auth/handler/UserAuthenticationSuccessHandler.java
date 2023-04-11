package com.billions.forty.auth.handler;

import com.billions.forty.user.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@Slf4j
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();

        response.setCharacterEncoding("UTF-8"); // objectMapper.writeValueAsString 한글깨지는 것에 대한 대응코드

        /*MemberDetails에서 유저정보를 가져와서 리스폰스에 뿌려주는 부분*/
        Map<String, Object> loginResponse = new HashMap<>();
        loginResponse.put("userId", user.getUserId());
        loginResponse.put("id", user.getId());
        loginResponse.put("email", user.getEmail());
        loginResponse.put("roles", user.getRoles());
        loginResponse.put("nickname", user.getNickname());


        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = objectMapper.writeValueAsString(loginResponse);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        response.getWriter().write(responseBody);

        log.info("# Authenticated successfully");
        log.info("name:{}, email: {}, role: {}", user.getNickname(), user.getEmail(), user.getRoles() );
    }
}


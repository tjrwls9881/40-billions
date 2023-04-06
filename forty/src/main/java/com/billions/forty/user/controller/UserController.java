package com.billions.forty.user.controller;

import com.billions.forty.user.dto.UserDto;
import com.billions.forty.user.entity.User;
import com.billions.forty.user.mapper.UserMapper;
import com.billions.forty.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/user")
@RestController
@Validated
@ToString
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    public UserController(UserService userService,
                          UserMapper userMapper){
        this.userService=userService;
        this.userMapper=userMapper;
    }

    @PostMapping
    public ResponseEntity postUser(@Valid @RequestBody UserDto.UserPostDto requestBody){
    User user=userMapper.userPostToMember(requestBody);
    userService.createdUser(user);

    return new ResponseEntity<>(userMapper.memberToMemberResponse(user), HttpStatus.CREATED);

    }
}

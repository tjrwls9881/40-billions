package com.billions.forty.user.controller;

import com.billions.forty.globaldto.MultiResponseDto;
import com.billions.forty.user.dto.UserDto;
import com.billions.forty.user.entity.User;
import com.billions.forty.user.mapper.UserMapper;
import com.billions.forty.user.service.UserService;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

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

    return new ResponseEntity<>(userMapper.userToUserResponse(user), HttpStatus.CREATED);

    }

    @PatchMapping("/{userId}")
    public ResponseEntity patchUser(@PathVariable @Positive long userId,
                                    @Valid @RequestBody UserDto.UserPatchDto requestBody){

        User user=userMapper.userPatchToMember(requestBody);
        user.setUserId(userId);
        User updatedUser=userService.updateUser(user);

        return new ResponseEntity(userMapper.userToUserResponse(updatedUser),HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity getUser(@PathVariable @Positive long userId){
        User user= userService.findUser(userId);

        return new ResponseEntity(userMapper.userToUserResponse(user), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable @Positive long usesId){
        userService.deleteUser(usesId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity getUsers(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size){
        Page<User> pageUsers=userService.findUsers(page-1,size);
        List<User> members = pageUsers.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(userMapper.usersToUserResponses(members), pageUsers), HttpStatus.OK);
    }
}

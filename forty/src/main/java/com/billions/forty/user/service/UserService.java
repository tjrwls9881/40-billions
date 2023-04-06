package com.billions.forty.user.service;

import com.billions.forty.exception.BusinessLogicException;
import com.billions.forty.exception.ExceptionCode;
import com.billions.forty.user.entity.User;
import com.billions.forty.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@ToString
public class UserService {

    private final UserRepository userRepository;

//    public UserService(UserRepository userRepository){
//        this.userRepository=userRepository;
//    }

    public User createdUser(User user){
        User CreatedUser=userRepository.save(user);

        return CreatedUser;
    }

    private User updateUser(User user){
        User UpdateUser=userRepository.save(user);

        return UpdateUser;
    }

    private User findUser(long userId){
        return findVerifiedUser(userId);
    }

    private User findVerifiedUser(long userId){
        Optional<User> newUser=userRepository.findById(userId);
        User findUser=newUser.orElseThrow(()->
                new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        return findUser ;
    }

    private void deleteUser(long userId){
        User findUser=findVerifiedUser(userId);
        userRepository.delete(findUser);
    }
}

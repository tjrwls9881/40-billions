package com.billions.forty.user.service;

import com.billions.forty.exception.BusinessLogicException;
import com.billions.forty.exception.ExceptionCode;
import com.billions.forty.user.entity.User;
import com.billions.forty.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public User findUser(long userId){
        return findVerifiedUser(userId);
    }

    private User findVerifiedUser(long userId){
        Optional<User> newUser=userRepository.findById(userId);
        User findUser=newUser.orElseThrow(()->
                new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        return findUser ;
    }

    public void deleteUser(long userId){
        User findUser=findVerifiedUser(userId);
        userRepository.delete(findUser);
    }

    public User updateUser(User user) {
        User findUser = findVerifiedUser(user.getUserId());
        Optional.ofNullable(user.getNickname())
                .ifPresent(findUser::setNickname);
        Optional.ofNullable(user.getPassword())
                .ifPresent(findUser::setPassword);

        return userRepository.save(findUser);
    }

    public Page<User> memberPage(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public Page<User> findUsers(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size, Sort.by("userId").descending()));
    }
}

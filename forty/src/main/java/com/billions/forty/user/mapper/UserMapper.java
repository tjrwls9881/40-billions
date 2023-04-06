package com.billions.forty.user.mapper;

import com.billions.forty.user.dto.UserDto;
import com.billions.forty.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import java.util.List;


//@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User userPostToMember(UserDto.UserPostDto requestBody);
    User userPatchToMember(UserDto.UserPatchDto requestBody);
    UserDto.UserResponseDto memberToMemberResponse(User user);
    List<UserDto.UserResponseDto> membersToMemberResponses(List<User>users);
}

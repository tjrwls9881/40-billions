package com.billions.forty.auth.userdetails;

import com.billions.forty.auth.utils.CustomAuthorityUtils;
import com.billions.forty.exception.BusinessLogicException;
import com.billions.forty.exception.ExceptionCode;
import com.billions.forty.user.entity.User;
import com.billions.forty.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final CustomAuthorityUtils authorityUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<User> optionalUser=userRepository.findByEmail(username);
        User findUser=optionalUser.orElseThrow(()->new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        return new MemberDetails(findUser);
    }

    private final class MemberDetails extends User implements UserDetails{
        MemberDetails(User user){
            setId(user.getId());
            setUserId(user.getUserId());
            setPhone(user.getPhone());
            setEmail(user.getEmail());
            setNickname(user.getNickname());
            setPassword(user.getPassword());

        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorityUtils.createAuthorities(this.getRoles());
        }

        @Override
        public String getUsername() {
            return getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}

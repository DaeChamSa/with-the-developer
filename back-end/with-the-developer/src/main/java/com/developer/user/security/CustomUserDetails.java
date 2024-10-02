package com.developer.user.security;

import com.developer.user.command.domain.aggregate.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString()));

        return collection;
    }

    // 계정의 이름 리턴 (일반적으로 아이디)
    @Override
    public String getUsername() {
        return user.getUserId();
    }

    // 계정의 비밀번호 리턴
    @Override
    public String getPassword() {
        return user.getUserPw();
    }

    // 사용자 코드 리턴
    public Long getUserCode(){return user.getUserCode();}

}

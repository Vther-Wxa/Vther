package com.vther.auth.config;

import com.vther.auth.mapper.UserMapper;
import com.vther.auth.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class LoginHandler implements AuthenticationProvider {

    @Autowired
    private UserMapper userMapper;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
         String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = userMapper.queryUserByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        if (user.getPassword().equals(password)){
            return new UsernamePasswordAuthenticationToken(user, password, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}

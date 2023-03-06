package com.vther.auth.config;


import com.vther.auth.pojo.User;
import com.vther.comment.utils.JwtUtil;
import com.vther.comment.utils.ResponseResult;
import com.vther.comment.utils.RsaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //获得用户名
        User user = (User) authentication.getPrincipal();
        //将用户名生成jwt token
        String token = JwtUtil.generateToken(user.getUsername(), RsaUtil.privateKey, JwtUtil.EXPIRE_MINUTES);
        //将token 发送给前端
        com.vther.auth.pojo.User userToken = new com.vther.auth.pojo.User();
        userToken.setToken(token);
        userToken.setUsername(user.getUsername());
        ResponseResult.write(response,ResponseResult.ok(userToken));
    }
}

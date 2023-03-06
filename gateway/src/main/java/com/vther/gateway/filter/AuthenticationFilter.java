package com.vther.gateway.filter;

import com.vther.comment.utils.JwtUtil;
import com.vther.comment.utils.RsaUtil;
import com.vther.gateway.config.WhiteListConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 用户验证过滤器
 * @author Charon
 */
@Slf4j
@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    @Autowired
    private WhiteListConfig whiteListConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获得请求和响应对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //对白名单中的地址放行
        List<String> whiteList = whiteListConfig.getWhiteList();
        for(String str : whiteList){
            if(request.getURI().getPath().contains(str)){
                log.info("白名单，放行{}",request.getURI().getPath());
                return chain.filter(exchange);
            }
        }
        //获得请求头中Authorization token信息
        String token = request.getHeaders().getFirst("Authorization");
        try{
            //解析token
            String username = JwtUtil.getUsernameFromToken(token, RsaUtil.privateKey);
            log.info("{}解析成功，放行{}",username,request.getURI().getPath());
            return chain.filter(exchange);
        }catch (Exception ex){
            log.error("token解析失败",ex);
            //返回验证失败的响应信息
            response.setStatusCode(HttpStatus.UNAUTHORIZED);

            DataBuffer wrap = response.bufferFactory().wrap("验证错误，需要登录".getBytes());
            return response.writeWith(Mono.just(wrap));
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

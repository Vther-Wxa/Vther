package com.vther.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Charon
 * @Date 2022/8/11
 **/
@Data
@Configuration
public class WhiteListConfig {

    //放行白名单
    private List<String> whiteList = new ArrayList<>();

    @Bean
    public List initWhiteList(){
        whiteList.add("/login");
        return whiteList;
    }
}
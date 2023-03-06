package com.vther.comment.utils;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.gson.io.GsonSerializer;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

/**
 * JWT工具类
 */
public class JwtUtil {

    public static final String JWT_KEY_USERNAME = "username";
    public static final int EXPIRE_MINUTES = 120;

    /**
     * 私钥加密token
     */
    public static String generateToken(String username, PrivateKey privateKey, int expireMinutes) {

        return Jwts.builder()
                .claim(JWT_KEY_USERNAME, username)
                .setExpiration(new Date())
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .serializeToJsonWith(new GsonSerializer<>(new Gson()))
                .compact();
    }

    /**
     * 从token解析用户
     *
     * @param token
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String getUsernameFromToken(String token, PrivateKey publicKey){
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        String username = (String) body.get(JWT_KEY_USERNAME);
        return username;
    }
}

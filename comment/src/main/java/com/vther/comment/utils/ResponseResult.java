package com.vther.comment.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 响应内容
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult<T> {

    private Integer statusCode;

    private T data;

    /**
     * 正确返回数据
     */
    public static <T> ResponseResult<T> ok(T data){
        return new ResponseResult<T>(ResponseStatus.OK.getCode(),data);
    }

    /**
     * 返回错误消息
     */
    public static ResponseResult<String> error(ResponseStatus status){
        return new ResponseResult<>(status.getCode(),status.getMessage());
    }

    /**
     * 返回错误消息
     */
    public static ResponseResult<String> error(ResponseStatus status,String err){
        return new ResponseResult<>(status.getCode(),err);
    }

    /**
     * 将数据转换为json，发送给前端
     * @param resp
     * @param result
     */
    public static void write(HttpServletResponse resp,ResponseResult result) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String s = new ObjectMapper().writeValueAsString(result);
        PrintWriter writer = resp.getWriter();
        writer.write(s);
        writer.close();
    }
}

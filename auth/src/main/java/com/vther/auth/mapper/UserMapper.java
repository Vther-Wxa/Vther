package com.vther.auth.mapper;

import com.vther.auth.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Vther
 * @since 2023-03-02
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    User queryUserByUsername(@Param("username") String username);
}

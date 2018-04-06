package cn.vpclub.springboot.demo.service;

import cn.vpclub.springboot.demo.entity.UserDo;

import java.sql.SQLException;

/**
 * 用户服务
 */

public interface IUserService {

    /**
     * 查找用户
     * param UserDo
     * throws SQLException
     */
   UserDo getUserDo(UserDo userDo);

    /**
     * 注册用户
     */
   Integer addUser(UserDo userDo);

}

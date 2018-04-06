package cn.vpclub.springboot.demo.storage;

import cn.vpclub.springboot.demo.entity.UserDo;
import org.apache.ibatis.annotations.Mapper;

/**
*用户注册登录
* */
@Mapper
public interface IUserDao {

    int signUp(UserDo user);

    UserDo login(String id);

    Integer updatePassword(UserDo userDo);
}

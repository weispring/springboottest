package cn.vpclub.springboot.demo.service;

import cn.vpclub.springboot.demo.entity.UserDo;
import cn.vpclub.springboot.demo.storage.IUserDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private IUserDao userDao;

    @Override
    public UserDo getUserDo(UserDo userDo) {
        log.info("进入查询用户service方法");
        return userDao.login(userDo.getUserName());
    }

    @Override
    public Integer addUser(UserDo userDo) {
        log.info("进入注册方法");
        int flag = userDao.signUp(userDo);
        return flag;
    }
}

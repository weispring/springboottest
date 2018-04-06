package cn.vpclub.springboot.demo.web;

import cn.vpclub.moses.utils.common.JsonUtil;
import cn.vpclub.springboot.demo.entity.UserDo;
import cn.vpclub.springboot.demo.entity.response.BaseResponse;
import cn.vpclub.springboot.demo.entity.response.ReturnCodeEnum;
import cn.vpclub.springboot.demo.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.sql.SQLException;

/**
 * 用户服务
 */
@AllArgsConstructor
@RestController
@Slf4j
public class UserControl {
    private IUserService userService;

    @PostMapping("/login")
    public BaseResponse login(@RequestBody UserDo userDo,ServletRequest request){
       /* UserDo userDo = new UserDo();
        userDo.setUserName(request.getParameter("userName"));
        userDo.setPassword(request.getParameter("password"));*/
        BaseResponse baseResponse = new BaseResponse();
        log.info("用户登陆{}",JsonUtil.objectToJson(userDo));
        UserDo getUser = null;
        try {
            getUser = userService.getUserDo(userDo);
        }catch (Exception e){
            log.error("sql异常",e);
            baseResponse.setCode(ReturnCodeEnum.CODE_1001.getCode());
            baseResponse.setMessage(e.getMessage());
        }
        String password = userDo.getPassword();
        if (!(getUser != null && password != null && password.equals(getUser.getPassword()))){
            baseResponse.setCode(ReturnCodeEnum.CODE_1001.getCode());
            baseResponse.setMessage(ReturnCodeEnum.CODE_1001.getMessage());
        }
        return baseResponse;
    }

    @PostMapping("/signUp")
    public BaseResponse signUp(@RequestBody UserDo userDo, ServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        /*UserDo userDo = new UserDo();
        userDo.setUserName(request.getParameter("userName"));
        userDo.setPassword(request.getParameter("password"));*/
        log.info("用户注册{}",JsonUtil.objectToJson(userDo));
        try {
            userService.addUser(userDo);
        }catch (Exception e){
            log.error("异常",e);
            baseResponse.setCode(ReturnCodeEnum.CODE_1001.getCode());
            baseResponse.setMessage(e.getMessage());
        }
        return baseResponse;
    }
}

package cn.vpclub.springboot.demo;

import cn.vpclub.moses.utils.common.JsonUtil;
import cn.vpclub.springboot.demo.entity.UserDo;
import cn.vpclub.springboot.demo.service.IUserService;
import cn.vpclub.springboot.demo.service.UserServiceImpl;
import cn.vpclub.springboot.demo.storage.IUserDao;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.omg.CORBA.Any;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Slf4j
@SpringBootTest
public class UserServiceTests {

    @Autowired   //此注解单元测试的时候无效
    private IUserService userService;
    private IUserDao userDao;
    private UserDo userDo;

    @BeforeMethod  //测试前需要完成的动作
    public void before() throws Exception{
        userDo = new UserDo();
        userDo.setUserName("li");
        userDo.setPassword("123456");
        //创建需要的serviceImpl
        userDao = PowerMockito.mock(IUserDao.class);//
        userService = new UserServiceImpl(userDao);
    }

    @AfterTest
    public void after() throws Exception {
        log.info("测试结束");
    }

    @Test(dataProvider = "insertData")  //引用参数
    public void insertUser(Integer result, String userName, String password) throws Exception {
        UserDo userDo = new UserDo();
        userDo.setPassword(password);
        userDo.setUserName(userName);
        log.info("进入测试方法注册用户");
        //dao 层并不会真是调用，但是可以模拟方法的返回值 用when方法进行期望数据模拟
        PowerMockito.when(userService.addUser(userDo)).thenReturn(1);
        Integer rr = userService.addUser(userDo);
        //验证前面是否调用了某方法  验证的对象必须是mock对象（mock 方法创建的对象）
        Mockito.verify(userDao).signUp(userDo);
        log.info("添加用户返回结果："+rr);
        Assert.assertEquals(rr,result);//(actual, expected)
    }

    @Test(dataProvider = "selectUser")  //引用参数
    public void selectUser(Integer result, String userName) {

        UserDo userDo = new UserDo();
        userDo.setUserName(userName);
        UserDo user = null;
        //dao 层并不会真是调用，但是可以模拟方法的返回值 用when方法进行期望数据模拟
        PowerMockito.when(userService.getUserDo(userDo)).thenReturn(userDo);
        try {
            user = userService.getUserDo(userDo);
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
        }

        log.info("查找用户"+JsonUtil.objectToJson(user));
        Assert.assertEquals(user,userDo);//(actual, expected)
    }

    @DataProvider(name = "insertData") //提供测试数据 一般根据测试方法所需的参数设置
    public Object[][] insertData(){
        return new Object[][] {
                {1,"li","li"},
                {0,"chun","chun"}
        };
    }

    @DataProvider(name = "selectUser") //提供测试数据 一般根据测试方法所需的参数设置
    public Object[][] selectData(){
        return new Object[][] {
                {1,"li"},
                {0, "chun"}
        };
    }
}

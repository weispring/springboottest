package cn.vpclub.springboot.demo;

import cn.vpclub.springboot.demo.entity.UserDo;
import cn.vpclub.springboot.demo.service.IUserService;
import cn.vpclub.springboot.demo.service.UserServiceImpl;
import cn.vpclub.springboot.demo.web.UserControl;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.TestDatabaseAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.testng.TestNG;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.sql.DataSource;


@Slf4j
@SpringBootTest
/*@Rollback(value = false)
@Transactional
@EnableTransactionManagement*/
public class UserControlTests {

    private IUserService userService;
    private UserDo userDo;


    @BeforeClass  //只执行一次
    public void setUp() {
        log.info("beforClass");

    }


    @BeforeMethod  //每次测试方法执行前运行
    public void before() throws Exception{
        log.info("beforeMethod");
        userDo = new UserDo();
        userDo.setUserName("li");
        userDo.setPassword("123456");
        userService = PowerMockito.mock(UserServiceImpl.class);//不会真正调用
        RestAssuredMockMvc.standaloneSetup(new UserControl(userService));//模拟restful  api  每次方法前执行

        /*IUserDao userDao = PowerMockito.mock(IUserDao.class);
        userService = new UserServiceImpl(userDao);*/
    }

    @After
    public void after() throws Exception {
        log.info("测试结束");
    }

    @Test(dataProvider = "insertData")
    public void insertUser(Integer result, String userName, String password) throws Exception {
        log.info("进入测试方法注册用户");
        UserDo userDo = new UserDo();
        userDo.setPassword(password);
        userDo.setUserName(userName);
        //模拟返回数据

        PowerMockito.when(userService.addUser(userDo)).thenReturn(1); //

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(userDo)
                .log()
                .all()
                .when()
                .post("/signUp")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("message",org.hamcrest.Matchers.equalTo(1));

    }

    @DataProvider(name = "insertData")
    public Object[][] insertData(){
        return new Object[][] {
                {1,"li","li"},
                {0,"chun","chun"}
        };
    }


}

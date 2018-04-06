package cn.vpclub.springboot.demo;

import cn.vpclub.springboot.demo.entity.UserDo;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SpringBootTest
@Slf4j
public class UserControlIntegrations extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext context;

    @BeforeClass
    public void setUp(){
        //模拟http请求和web环境
        RestAssuredMockMvc.webAppContextSetup(context);
    }

    @Test(dataProvider = "insertData")
    public void testSignUp(String expected ,String userName, String password){

        UserDo userDo = new UserDo();
        userDo.setPassword(password);
        userDo.setUserName(userName);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(userDo)
                .log()
                .all()
                .when()
                .post("/signUp")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test(dataProvider = "insertData", dependsOnMethods = "testSignUp")
    public void testLogin(String expected ,String userName, String password){

        UserDo userDo = new UserDo();
        userDo.setPassword(password);
        userDo.setUserName(userName);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(userDo)
                .log()
                .all()
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @DataProvider(name = "insertData")
    public Object[][] insertData(){
        return new Object[][] {
                {"成功","li","li"},
                {"失败","chun","chun"}
        };
    }

}

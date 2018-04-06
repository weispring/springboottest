package cn.vpclub.springboot.demo.entity.response;

import lombok.Data;
import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * 响应基础实体
 */
@Data
public class BaseResponse {
    private Integer code;
    private String message;

    public BaseResponse(){
        this.init();
    }

    public void init(){
        code = ReturnCodeEnum.CODE_1000.getCode();
        message = ReturnCodeEnum.CODE_1000.getMessage();
    }
}

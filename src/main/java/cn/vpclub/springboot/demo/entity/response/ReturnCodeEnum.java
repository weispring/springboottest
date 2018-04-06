package cn.vpclub.springboot.demo.entity.response;

public enum ReturnCodeEnum {

    CODE_1000(1000,"success"),
    CODE_1001(1001,"failed");

    private Integer code;
    private String message;

    private ReturnCodeEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {

        return code;
    }

    public String getMessage() {
        return message;
    }
}

package com.cxf.sell.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnum {
    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功"), // 不用写失败，因为如果支付失败了，不改0为1
    ;

    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    PayStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}


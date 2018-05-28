package com.cxf.sell.VO;

import lombok.Data;

@Data
public class ResultVO<T> {
    // code等于0代表成功
    private Integer code;
    private String msg;
    private T data;
}


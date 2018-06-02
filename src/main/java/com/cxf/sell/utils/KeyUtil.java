package com.cxf.sell.utils;

import java.util.Random;

public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式：时间 + 随机数
     * @return
     */
    // 多线程并发时订单号可能重复，所以加synchronized
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        // 生成六位随机数
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}

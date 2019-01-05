package com.cxf.sell.utils;

import java.util.UUID;

public class UUID32 {
    public static String getID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}

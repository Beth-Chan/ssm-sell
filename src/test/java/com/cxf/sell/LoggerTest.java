package com.cxf.sell;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {

    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test1() {
        String name = "cxf";
        String password = "aaa";
        logger.debug("debug...");
       // 也可以用拼接字符串的形式，但下面这种方式比拼接字符串好
        logger.info("name: {}, password: {}", name, password);
        logger.error("error...");
    }
}

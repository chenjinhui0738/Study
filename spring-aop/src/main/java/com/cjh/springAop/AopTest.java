package com.cjh.springAop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AopTest {
    @Autowired
    private AopService aopService;
    //
    @Test
    public void BeforeTest(){
        aopService.func1();

    }

    /**
     * 在成功调用后才会通知
     */
    @Test
    public void AfterReturningTest(){
        aopService.func2();
    }

    /**
     * 不管成功与否，调用后都会通知
     */
    @Test
    public void AfterTest(){
        aopService.func3();
    }
    @Test
    public void AroundTest(){
        aopService.func4();
    }
    @Test
    public void AfterThrowingTest(){
        aopService.func5();
    }
}

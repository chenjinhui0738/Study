package com.cjh.springAop;

import org.springframework.stereotype.Component;

@Component
public class AopService {
    public void func1() {
        System.out.println("方法执行了");
    }

    public void func2() {
        System.out.println("方法执行了");
        int a = 1 / 0;

    }

    public void func3() {
        System.out.println("方法执行了");
        int a = 1 / 0;
    }

    public void func4() {
        System.out.println("方法执行了");
    }

    public void func5() {
        System.out.println("方法执行了");
        int a = 1 / 0;
    }
}

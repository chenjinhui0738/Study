package com.cjh.springAop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {

    //匹配所有springAop包下面的所有类的所有方法
    //任意返回值+包路径+任意类+任意方法+任意参数
    /*@Pointcut("execution(* com.cjh.springAop.*.*(..))")
    public void ponit1() {
    }*/
    @Pointcut("execution(* com.cjh.springAop.*.func1(..))")
    public void ponit1() {
    }
    @Pointcut("execution(* com.cjh.springAop.*.func2(..))")
    public void ponit2() {
    }
    @Pointcut("execution(* com.cjh.springAop.*.func3(..))")
    public void ponit3() {
    }
    @Pointcut("execution(* com.cjh.springAop.*.func4(..))")
    public void ponit4() {
    }
    @Pointcut("execution(* com.cjh.springAop.*.func5(..))")
    public void ponit5() {
    }
    //前置通知,在目标方法调用之前调用通知
    @Before("MyAspect.ponit1()")//配置切入点，即需要增强的方法
    public void before(){
        System.out.println("前置通知");
    }
    //返回通知,在目标方法成功执行之后调用通知
    @AfterReturning("MyAspect.ponit2()")//配置切入点，即需要增强的方法
    public void afterReturning(){
        System.out.println("返回通知");
    }
    //后置通知.目标方法完成之后调用通知
    @After("ponit3()")//配置切入点，即需要增强的方法
    public void after(){
        System.out.println("后置通知");
    }
    //环绕通知,在被通知的方法调用之前和调用之后执行自定义的方法
    @Around("ponit4()")//配置切入点，即需要增强的方法
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("环绕前通知");
        joinPoint.proceed();
        System.out.println("环绕后通知");

    }
    //异常抛出通知,在目标方法抛出异常之后调用通知
    @AfterThrowing("ponit5()")//配置切入点，即需要增强的方法
    public void afterThrowing(){
        System.out.println("异常抛出通知");
    }

}
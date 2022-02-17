package DesignPattern.Decorator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 装饰类
 * 在原有基础上添加其他功能
 */
public class Decorator implements Sourceable{
    private static Logger logger = LoggerFactory.getLogger(Decorator.class);
    private Source source;

    public Decorator(Source source) {
        this.source = source;
    }
    @Override
    public void createComputer() {
        source.createComputer();
        System.out.println("添加额外功能");
    }
}

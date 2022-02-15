package DesignPattern.Decorator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 实现类
 */
public class Source implements Sourceable{
    private static Logger logger = LoggerFactory.getLogger(Source.class);
    @Override
    public void createComputer() {
        System.out.println("生产电脑");
    }
}

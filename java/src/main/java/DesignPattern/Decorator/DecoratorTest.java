package DesignPattern.Decorator;


import org.junit.Test;

public class DecoratorTest {
    @Test
    public void Test() {
        Source source = new Source();
        Decorator decorator = new Decorator(source);
        decorator.createComputer();
    }
}

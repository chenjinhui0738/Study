package zookeeper;

public class Test {
    public static void main(String[] args) {
        System.out.println("1.0/0=" + 1.0f/0l);
        System.out.println("-1.0/0=" + -1.0/0);
        double positiveInfinity = 1.0/0;
        double negativeInfinity = -1.0/0;
        System.out.println("(positiveInfinity==negativeInfinity)=" + (positiveInfinity==negativeInfinity));
        System.out.println();

        System.out.println("100.0/0=" + 100.0/0);
        System.out.println("-100.0/0=" + -100.0/0);
        System.out.println();

        System.out.println("0.0/0=" + 0.0/0);
        System.out.println("(-0.0==0.0)=" + (-0.0==0.0));
    }
}

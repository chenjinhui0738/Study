package gc;

public class Student {
    @Override
    protected void finalize() throws Throwable {
        System.out.println("Student 被回收了");
    }
}

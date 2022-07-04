package clone;

public class CloneTest {
    public static void main(String[] args) {
        Student student = new Student(1, "张三", new ClassRoom("初一1班"));
        Student clone = student.clone();
        System.out.println(clone);
    }

}

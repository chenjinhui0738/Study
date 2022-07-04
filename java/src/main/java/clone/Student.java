package clone;

public class Student implements Cloneable {
    private Integer id;
    private String name;
    private ClassRoom classRoom;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public Student(Integer id, String name, ClassRoom classRoom) {
        this.id = id;
        this.name = name;
        this.classRoom = classRoom;
    }

    @Override
    protected Student clone() {
        Student t = null;
        try {
            t = (Student) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", classRoom=" + classRoom +
                '}';
    }
}

package serializable;

import java.io.Serializable;

public class Worker implements Serializable {
    private static final long serialVersionUID = 8275267833958127543L;
    //name属性将被序列化
    private String name;
    // transient修饰的变量不会被序列化
    private transient Integer salary;
    //静态变量属于类信息,不属于对象的状态,因此不会被序列化
    private static Integer age = 100;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", age=" + age +
                '}';
    }
}

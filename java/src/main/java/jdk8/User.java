package jdk8;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class User extends Person{
    //用户Id
    Integer userId;

    //用户姓名
    String userName;

    //用户工资
    Double salary;
    //执行顺序
    //先执行父类再执行子类
    //1.静态代码块（只执行一次）2.构造代码块（每次调用都会执行）3.构造方法

    //构造代码块
    {
        System.out.println("构造代码块运行");
    }
    //静态代码块
    static{
        System.out.println("静态代码块运行");
    }

    //
    List<Product> productList;

    public User(Integer userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
    //构造方法
    public User() {
        System.out.println("构造方法执行");
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public User(Integer userId, String userName, Double salary) {
        this.userId = userId;
        this.userName = userName;
        this.salary = salary;
    }

    public User(Integer userId, String userName, Double salary, List<Product> productList) {
        this.userId = userId;
        this.userName = userName;
        this.salary = salary;
        this.productList = productList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", salary=" + salary +
                ", productList=" + JSONObject.toJSONString(productList) +
                '}';
    }
}

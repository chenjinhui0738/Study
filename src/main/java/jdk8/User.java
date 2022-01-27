package jdk8;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
public class User {
    //用户Id
    Integer userId;

    //用户姓名
    String userName;

    //用户工资
    Double salary;

    //
    List<Product> productList;
    public User(Integer userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public User() {
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

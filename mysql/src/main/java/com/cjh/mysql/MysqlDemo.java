package com.cjh.mysql;

public class MysqlDemo {
    /**
     * 去除重复数据
     */
    public void Test1(){
        String table = "create table users( id int auto_increment primary key, identity_id varchar(20),name varchar(20) not null);";
        String data = "insert into users values(null,'3','张三'), " +
                "(null,'3','张三'), " +
                "(null,'4','李四'), " +
                "(null,'4','李四')," +
                " (null,'5','王五')," +
                " (null,'5','王五'), " +
                "(null,'6','赵六');";
        //去除重复数据，保留id最大的数据
        String sql = "delete from users where id not in (select t.max_id from (select max(id) as max_id from users group by identity_id,name) as t);";


    }
}

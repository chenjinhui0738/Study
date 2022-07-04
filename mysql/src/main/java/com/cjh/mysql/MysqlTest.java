package com.cjh.mysql;

public class MysqlTest {
    /**
     * 临时表
     */
    public void Test1() {
        String sql = "with temp as (select * from tableName) select * from temp;";

    }

    /**
     * 去除重复数据
     */
    public void Test2() {
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

    /**
     * 去重排名（相同分数为一样名次）
     */
    public void Test3() {
        String sql = "SELECT val,DENSE_RANK() OVER ( ORDER BY val ) my_rank FROM rankDemo;";

    }

    /**
     * 求分组中的第一条/最后一条
     */
    public void Test4() {
        String sql = "SELECT * from overtime where id in ( SELECT DISTINCT FIRST_VALUE(id) OVER (PARTITION BY department ORDER BY hours desc) least_over_time FROM overtime); ";

    }

    /**
     * 获取每个分组的第2名
     */
    public void Test5() {
        String sql1 = "select * from basic_pays where id in (SELECT DISTINCT NTH_VALUE(id, 2) OVER  ( PARTITION BY department ORDER BY salary DESC) second_highest_salary FROM basic_pays); ";
        String sql2 = "select * from (select *,DENSE_RANK() over(partition by department order by salary) ranks from basic_pays) a where a.ranks=2;";
        String sql3 = "select * from (select *,ROW_NUMBER() over(partition by department order by salary) ranks from basic_pays) a where a.ranks=2;";

    }

    /**
     * 获取所有记录的第2名
     */
    public void Test6() {
        //跳过第一条只取一条即为第二名
        String sql = "select * from basic_pays ORDER BY salary DESC LIMIT 1 , 1; ";

    }

    /**
     * 获取今年的数据与前一年/后一年的数据，环比
     */
    public void Test7() {
        //获取数据与数据前一年
        String sql1 = "SELECT productline, order_year, order_value,LAG(order_value, 1) OVER (PARTITION BY productLine ORDER BY order_year)  prev_year_order_value FROM productline_sales; ";
        //获取数据与数据后一年
        String sql2 = "SELECT productline, order_year, order_value,LEAD(order_value, 1) OVER (PARTITION BY productLine ORDER BY order_year)  prev_year_order_value FROM productline_sales; ";

    }

    /**
     * 复制并创建表
     */
    public void Test8() {
        String sql = "CREATE TABLE IF NOT EXISTS offices_bk SELECT * FROM offices;";

    }

    /**
     * 将数据库的一张表复制到另一个数据库
     */
    public void Test9() {
        String sql = "CREATE TABLE target_db.new_table LIKE source_db.existing_table;" +
                "INSERT target_db.new_table SELECT * FROM source_db.existing_table; ";

    }
}

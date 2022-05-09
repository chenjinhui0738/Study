package com.cjh.mybatisplus;

import com.cjh.mybatisplus.dao.ProductMapper;
import com.cjh.mybatisplus.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 乐观锁测试类
 * 需求：老板叫小李将价格提高50，后觉得太贵，又让小王把价格-30，商品原价为100
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MybatisPlusConcurrentVersionTest {
    @Autowired
    private ProductMapper productMapper;

    /**
     * 未设置乐观锁的情况.需去掉version字段同时MybatisPlusConfig去掉乐观锁配置
     * 小李：100+50
     * 小王：100-30
     * 最终结果被覆盖：70
     */
    @Test
    public void Test() {
        //1、小李
        Product p1 = productMapper.selectById(1L);
        System.out.println("小李取出的价格：" + p1.getPrice());
        //2、小王
        Product p2 = productMapper.selectById(1L);
        System.out.println("小王取出的价格：" + p2.getPrice());
        //3、小李将价格加了50元，存入了数据库
        p1.setPrice(p1.getPrice() + 50);
        int result1 = productMapper.updateById(p1);
        System.out.println("小李修改结果：" + result1);
        //4、小王将商品减了30元，存入了数据库
        p2.setPrice(p2.getPrice() - 30);
        int result2 = productMapper.updateById(p2);
        System.out.println("小王修改结果：" + result2);
        //最后的结果
        Product p3 = productMapper.selectById(1L);
        //价格覆盖，最后的结果：70
        System.out.println("最后的结果：" + p3.getPrice());
    }
    /**
     * 设置乐观锁的情况
     * 小李：100+50
     * 小王：100-30
     * 最终结果被覆盖：100+50-30
     */
    @Test
    public void Test2() {
        //小李取数据
        Product p1 = productMapper.selectById(1L);
        //小王取数据
        Product p2 = productMapper.selectById(1L);
        //小李修改 + 50
        p1.setPrice(p1.getPrice() + 50);
        int result1 = productMapper.updateById(p1);
        System.out.println("小李修改的结果：" + result1);
        //小王修改 - 30
        p2.setPrice(p2.getPrice() - 30);
        int result2 = productMapper.updateById(p2);
        System.out.println("小王修改的结果：" + result2);
        //未更新成功的情况下
        if(result2 == 0){
            //失败重试，重新获取version并更新
            p2 = productMapper.selectById(1L);
            p2.setPrice(p2.getPrice() - 30);
            result2 = productMapper.updateById(p2);
        }
        System.out.println("小王修改重试的结果：" + result2);
        //老板看价格
        Product p3 = productMapper.selectById(1L);
        System.out.println("老板看价格：" + p3.getPrice());
    }
}

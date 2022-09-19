package com.cjh.mapStruct;

import com.cjh.mapStruct.dto.SourceDto;
import com.cjh.mapStruct.dto.TargetDto;
import com.cjh.mapStruct.mapper.DemoMapper;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class MapStructApplicationTests {
    /**
     * 属性名相同的转换
     */
    @Test
    public void test1() {
        SourceDto source = new SourceDto();
        source.setId(111);
        source.setName("hello");
        TargetDto targetDto = DemoMapper.INSTANCE.toTargetDto(source);
        System.out.println("目标对象为：" + targetDto);
    }
    /**
     * 属性名不同的转换
     */
    @Test
    public void test2() {
        SourceDto source = new SourceDto();
        source.setId(111);
        source.setName("hello");
        source.setPhone("123456");
        source.setTime(new Date());
        TargetDto targetDto = DemoMapper.INSTANCE.toTargetDtoByDifferentName(source);
        System.out.println("目标对象为：" + targetDto);
    }
    /**
     * 固定属性值的转换
     */
    @Test
    public void test3() {
        SourceDto source = new SourceDto();
        source.setName("张三");
        TargetDto targetDto = DemoMapper.INSTANCE.toTargetDtoByConstant(source);
        System.out.println("目标对象为：" + targetDto);
    }
    /**
     * 日期格式化
     */
    @Test
    public void test4() {
        SourceDto source = new SourceDto();
        source.setTime(new Date());
        TargetDto targetDto = DemoMapper.INSTANCE.toTargetDtoByTime(source);
        System.out.println("目标对象为：" + targetDto);
    }
    /**
     * 自定义属性值的转换
     */
    @Test
    public void test5() {
        SourceDto source = new SourceDto();
        source.setName("张三");
        TargetDto targetDto = DemoMapper.INSTANCE.toTargetDtoByCustomize(source);
        System.out.println("目标对象为：" + targetDto);
    }

}

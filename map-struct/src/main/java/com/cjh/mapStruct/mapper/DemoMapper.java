package com.cjh.mapStruct.mapper;

import com.cjh.mapStruct.dto.SourceDto;
import com.cjh.mapStruct.dto.TargetDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.text.SimpleDateFormat;
import java.util.Date;
//设置未映射到的处理策略
//nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE 代表null值不进行赋值。
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DemoMapper {
    //MapStruct会将所有匹配到的：
    //源类型为Date、目标类型为String的属性，
    //按以下方法进行转换
    /*static String date2String(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = simpleDateFormat.format(date);
        return strDate;
    }*/
    //使用Mappers工厂获取DemoMapper实现类
    DemoMapper INSTANCE = Mappers.getMapper(DemoMapper.class);

    //定义接口方法，参数为来源对象，返回值为目标对象
    TargetDto toTargetDto(SourceDto sourceDto);

    //不同属性名的转换
    @Mapping(target = "telphone", source = "phone")
    TargetDto toTargetDtoByDifferentName(SourceDto sourceDto);

    //不同属性类型的转换
    @Mapping(target = "time", source = "time")
    TargetDto toTargetDtoByDifferentType(SourceDto sourceDto);

    //固定值类型的转换
    @Mapping(target = "name", constant = "hello")
    TargetDto toTargetDtoByConstant(SourceDto sourceDto);

    //日期类型的转换
    @Mapping(target = "time", dateFormat = "yyyy-MM-dd HH:mm")
    TargetDto toTargetDtoByTime(SourceDto sourceDto);

    //自定义转换
    @Mapping(target = "name", qualifiedByName = "convertName")
    TargetDto toTargetDtoByCustomize(SourceDto sourceDto);
    @Named("convertName")
    static String aaa(String name) {
        return "姓名为：" + name;
    }
}
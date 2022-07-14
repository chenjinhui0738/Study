package com.cjh.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//默认使用mapper中的泛型来匹配表，如果有设置全局表前缀，则默认匹配规则为前缀+实体类名称
//@TableName("t_user")
//lombok注释,maven中执行compile命令后编译文件中会自动帮我们生成这些
//@NoArgsConstructor//无参构造
//@AllArgsConstructor//全参构造
//@ToString//toString方法
//@Getter
//@Setter
//@EqualsAndHashCode
//以上几个注解等于@Data
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    //默认id为主键,value为表中主键对应的字段值,type为自增方式，默认为雪花分布式id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("user_name")
    private String userName;
    private Integer age;
    private String email;
    //@TableLogic(value = "1",delval = "0")
    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date createTime;
}

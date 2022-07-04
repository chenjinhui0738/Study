package com.cjh.mybatisplus;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * 代码生成器
 */
public class FastAutoGeneratorStart {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/mybatis-plus?serverTimezone=UTC&&characterEncoding=utf-8&userSSL=false", "root", "root").
                //全局配置
                        globalConfig(builder -> {
                    builder.author("cjh") // 设置作者
                           .enableSwagger() // 开启 swagger 模式
                           .fileOverride() // 覆盖已生成文件
                           .outputDir("F:\\cjh_idea_workspace\\Study\\mybatis-plus\\src\\main\\java\\com\\cjh\\mybatisplus"); // 指定输出目录
                }).
                //包配置
                        packageConfig(builder -> {
                    builder.
                                   parent("") // 设置父包名
                           //.moduleName("mybatisplus") // 设置父包模块名
                           .entity("entity")
                           .service("service")
                           .serviceImpl("service.impl")
                           .mapper("mapper")
                           .xml("mapper.xml")
                           .controller("controller")
                           .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "F:\\cjh_idea_workspace\\Study\\mybatis-plus\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                }).
                //策略配置
                        strategyConfig(builder -> {
                    builder.addInclude("t_template") // 设置需要生成的表名
                           .addTablePrefix("t_", "c_");// 设置过滤表前缀
                }).
                        templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker 引擎模板，默认的是Velocity引擎模板
                .execute();
    }

}

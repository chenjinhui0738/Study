package com.cjh.SpringRegistBean.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * lombok注解
 */
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class User {
    private String name;
    private Integer age;
}

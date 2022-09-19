package com.cjh.mapStruct.dto;

import lombok.Data;

import java.util.Date;

/**
 * 源对象
 */
@Data
public class SourceDto {
    private Integer id;
    private String name;
    private String phone;
    private Date time;
}

package com.cjh.mybatis.mybatis.service;

import com.cjh.mybatis.mybatis.entity.Student;

public interface StudentService {
    int add(Student student);
    int update(Student student) throws Exception;
    int deleteBysno(String sno);
    Student queryStudentBySno(String sno);
}

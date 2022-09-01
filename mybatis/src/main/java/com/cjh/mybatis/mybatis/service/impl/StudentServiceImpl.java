package com.cjh.mybatis.mybatis.service.impl;

import com.cjh.mybatis.mybatis.entity.Student;
import com.cjh.mybatis.mybatis.dao.StudentMapper;
import com.cjh.mybatis.mybatis.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public int add(Student student) {
        return studentMapper.add(student);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(Student student) throws Exception{

        int update = studentMapper.update(student);
        try {

            int a = 1/0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return update;
    }

    @Override
    public int deleteBysno(String sno) {
        return studentMapper.deleteBysno(sno);
    }

    @Override
    public Student queryStudentBySno(String sno) {
        return studentMapper.queryStudentBySno(sno);
    }
}

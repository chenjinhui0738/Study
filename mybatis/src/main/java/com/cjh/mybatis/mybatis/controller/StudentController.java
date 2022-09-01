package com.cjh.mybatis.mybatis.controller;

import com.cjh.mybatis.mybatis.entity.Student;
import com.cjh.mybatis.mybatis.service.StudentService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping( value = "/querystudent", method = RequestMethod.GET)
    @ResponseBody
    public Student queryStudentBySno(String sno) {
        return studentService.queryStudentBySno(sno);
    }
    @RequestMapping( value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public void test() throws Exception {
        Student student = new Student();
        student.setSno("004");
        student.setName("test1");
        studentService.update(student);
    }

}

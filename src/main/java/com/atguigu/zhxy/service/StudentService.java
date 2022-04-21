package com.atguigu.zhxy.service;

import com.atguigu.zhxy.pojo.LoginForm;
import com.atguigu.zhxy.pojo.Student;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface StudentService extends IService<Student> {
    Student login(LoginForm loginForm);

    Student getStudentById(Long userId);

    IPage getStudent(Page page, String clazzName);
}

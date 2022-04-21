package com.atguigu.zhxy.service;

import com.atguigu.zhxy.pojo.LoginForm;
import com.atguigu.zhxy.pojo.Teacher;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface TeacherService extends IService<Teacher> {
    Teacher login(LoginForm loginForm);

    Teacher getTeacherById(Long userId);

    IPage getTeachers(Page page, Teacher teacher);
}

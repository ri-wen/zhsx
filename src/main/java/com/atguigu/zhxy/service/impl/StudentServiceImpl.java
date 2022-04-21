package com.atguigu.zhxy.service.impl;

import com.atguigu.zhxy.mapper.StudentMapper;
import com.atguigu.zhxy.pojo.LoginForm;
import com.atguigu.zhxy.pojo.Student;
import com.atguigu.zhxy.service.StudentService;
import com.atguigu.zhxy.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service("studentServiceImpl")
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Override
    public Student login(LoginForm loginForm) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        Student student = baseMapper.selectOne(queryWrapper);
        return student;
    }

    @Override
    public Student getStudentById(Long userId) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",userId);
        Student student = baseMapper.selectOne(queryWrapper);
        return student;
    }



    @Override
    public IPage getStudent(Page page, String clazzName) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if(!StringUtils.isEmpty(clazzName)){
            queryWrapper.like("clazz_name", clazzName);
        }
        queryWrapper.orderByDesc("id");
        Page page1 = baseMapper.selectPage(page,queryWrapper);
        return page1;
    }
}

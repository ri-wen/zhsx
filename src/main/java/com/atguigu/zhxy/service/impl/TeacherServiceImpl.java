package com.atguigu.zhxy.service.impl;

import com.atguigu.zhxy.mapper.TeacherMapper;
import com.atguigu.zhxy.pojo.LoginForm;
import com.atguigu.zhxy.pojo.Teacher;
import com.atguigu.zhxy.service.TeacherService;
import com.atguigu.zhxy.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service("teacherServiceImpl")
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Override
    public Teacher login(LoginForm loginForm) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        Teacher teacher = baseMapper.selectOne(queryWrapper);
        return teacher;
    }

    @Override
    public Teacher getTeacherById(Long userId) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",userId);
        Teacher teacher = baseMapper.selectOne(queryWrapper);
        return teacher;
    }

    @Override
    public IPage getTeachers(Page page, Teacher teacher) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (!StringUtils.isEmpty(teacher.getName())) {
            queryWrapper.eq("name",teacher.getName());
        }
        if (!StringUtils.isEmpty(teacher.getClazzName())) {
            queryWrapper.eq("clazz_name",teacher.getClazzName());
        }
        queryWrapper.orderByDesc("id");
        Page<Teacher> page1 = baseMapper.selectPage(page, queryWrapper);
        return page1;
    }


}

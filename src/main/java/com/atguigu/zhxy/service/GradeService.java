package com.atguigu.zhxy.service;

import com.atguigu.zhxy.pojo.Admin;
import com.atguigu.zhxy.pojo.Grade;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface GradeService extends IService<Grade> {
    IPage<Grade> getGradeByOpr(Page<Grade> pageRs, String gradeName);

    List<Grade> getGrades();
}

package com.atguigu.zhxy.service.impl;
;
import com.atguigu.zhxy.mapper.GradeMapper;
import com.atguigu.zhxy.pojo.Grade;
import com.atguigu.zhxy.service.GradeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service("gradeServiceImpl")
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {
    @Override
    public IPage<Grade> getGradeByOpr(Page<Grade> pageParam, String gradeName) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if(!StringUtils.isEmpty(gradeName)){
            queryWrapper.like("name",gradeName);
        }
        queryWrapper.orderByDesc("id");
        Page<Grade> page = baseMapper.selectPage(pageParam, queryWrapper);
        return page;
    }

    @Override
    public List<Grade> getGrades() {
        List<Grade> grades = baseMapper.selectList(null);
        return grades;
    }
}

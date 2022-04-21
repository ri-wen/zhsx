package com.atguigu.zhxy.service.impl;

import com.atguigu.zhxy.mapper.ClazzMapper;
import com.atguigu.zhxy.pojo.Clazz;
import com.atguigu.zhxy.service.ClazzService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service("clazzServiceImpl")
@Transactional
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {
    @Override
    public IPage getClazzByOpr(Page<Clazz> page,Clazz clazz) {
        QueryWrapper queryWrapper = new QueryWrapper();
        String gradeName = clazz.getGradeName();
        String name = clazz.getName();
        if (!StringUtils.isEmpty(gradeName)){
            queryWrapper.eq("grade_name",clazz.getGradeName());
        }
        if(!StringUtils.isEmpty(name)){
            queryWrapper.eq("name",clazz.getName());
        }
        queryWrapper.orderByDesc("id");
        Page clazzPage = baseMapper.selectPage(page,queryWrapper);
        return clazzPage;
    }

    @Override
    public List<Clazz> getClazzs() {
        List<Clazz> clazzes = baseMapper.selectList(null);
        return clazzes;
    }
}

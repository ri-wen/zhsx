package com.atguigu.zhxy.service;

import com.atguigu.zhxy.pojo.Clazz;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ClazzService extends IService<Clazz> {
    IPage getClazzByOpr(Page<Clazz> page,Clazz clazz);

    List<Clazz> getClazzs();
}

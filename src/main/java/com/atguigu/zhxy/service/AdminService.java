package com.atguigu.zhxy.service;

import com.atguigu.zhxy.pojo.Admin;
import com.atguigu.zhxy.pojo.LoginForm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface AdminService extends IService<Admin> {

    Admin login(LoginForm loginForm);

    Admin getAdminById(Long userId);

    IPage<Admin> getAdminByOpr(Page<Admin> pagePara, String adminName);
}

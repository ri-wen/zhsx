package com.atguigu.zhxy.controller;

import com.atguigu.zhxy.pojo.Admin;
import com.atguigu.zhxy.service.AdminService;
import com.atguigu.zhxy.util.MD5;
import com.atguigu.zhxy.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "管理员控制器")
@RestController
@RequestMapping("/sms/adminController")
public class AdminController {


    @Autowired
    private AdminService adminService;

    @ApiOperation("得到所有管理员")
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(
            @PathVariable("pageNo") Integer pageNo,
            @PathVariable("pageSize") Integer pageSize,
            String adminName
    ){
        Page<Admin> pagePara = new Page<>(pageNo,pageSize);
        IPage<Admin> iPage = adminService.getAdminByOpr(pagePara,adminName);
        return Result.ok(iPage);
    }

    @ApiOperation("保存或增加管理员")
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(
            @ApiParam("管理员") @RequestBody Admin admin
    )
    {
        Integer id = admin.getId();
        if(null == id || id == 0){
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }
        adminService.saveOrUpdate(admin);
        return Result.ok();
    }

    @ApiOperation("删除管理员")
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(
            @RequestBody List<Integer> ids
    ){
        adminService.removeByIds(ids);
        return Result.ok();
    }
}

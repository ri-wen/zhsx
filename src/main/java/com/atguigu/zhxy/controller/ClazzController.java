package com.atguigu.zhxy.controller;

import com.atguigu.zhxy.pojo.Clazz;
import com.atguigu.zhxy.service.ClazzService;
import com.atguigu.zhxy.service.StudentService;
import com.atguigu.zhxy.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "班级控制器")
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {
    @Autowired
    ClazzService clazzService;

    ///sms/clazzController/getClazzs
    @GetMapping("/getClazzs")
    public Result getClazzs(){
        List<Clazz> clazzs = clazzService.getClazzs();
        return Result.ok(clazzs);
    }

    @ApiOperation("查询班级")
    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzByOpr(
            @ApiParam("分页查询数量") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页查询页大小")@PathVariable("pageSize") Integer pageSize,
            @ApiParam("查询条件") Clazz clazz
    ){
        Page page = new Page(pageNo,pageSize);
        IPage<Clazz> ipage = clazzService.getClazzByOpr(page,clazz);
        return Result.ok(ipage);
    }

    @ApiOperation("保存或新增班级")
    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(
            @RequestBody Clazz clazz
    ){
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }

    @ApiOperation("删除班级")
    @DeleteMapping("/deleteClazz")
    public Result delete(
            @RequestBody ArrayList<Integer> ids
    ){
        clazzService.removeByIds(ids);
        return Result.ok();
    }
}

package com.atguigu.zhxy.controller;

import com.atguigu.zhxy.pojo.Grade;
import com.atguigu.zhxy.service.GradeService;
import com.atguigu.zhxy.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @ApiOperation("获取全部年级")
    @GetMapping("/getGrades")
    public Result getGrades(){
        List<Grade> grades = gradeService.getGrades();
        return Result.ok(grades);
    }

    @ApiOperation("带条件查询所有年级")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGrade(
            @ApiParam("查询页码")@PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页查询页大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("模糊匹配") String gradeName
    ){
        //创建分页信息
        Page<Grade> pageRs = new Page<>(pageNo,pageSize);
        //
        IPage<Grade> ipage = gradeService.getGradeByOpr(pageRs,gradeName);
        return Result.ok(pageRs);
    }

    @ApiOperation("新增或修改年级，如果有id属性，则为修改")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(
            @ApiParam("Json格式的grade对象")@RequestBody Grade grade
            ){
        //接受参数
        //调用服务层方法
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }

    @ApiOperation("删除年级")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(
           @ApiParam("要删除的id的json集合") @RequestBody List<Integer> ids
    ){
        gradeService.removeByIds(ids);
        return Result.ok();
    }
}

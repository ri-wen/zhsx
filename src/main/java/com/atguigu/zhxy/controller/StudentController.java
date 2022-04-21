package com.atguigu.zhxy.controller;

import com.atguigu.zhxy.pojo.Clazz;
import com.atguigu.zhxy.pojo.Student;
import com.atguigu.zhxy.service.StudentService;
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

@Api
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {
    @Autowired
    private StudentService studentService;

    //sms/studentController/getStudentByOpr/1/3
    @ApiOperation("获取学生页面")
    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(
            @ApiParam("页面数量")@PathVariable("pageNo") Integer pageNo,
            @ApiParam("页面尺寸")@PathVariable("pageSize") Integer pageSize,
            String clazzName
    ){
        Page page  = new Page(pageNo,pageSize);
        IPage iPage = studentService.getStudent(page,clazzName);
        return Result.ok(iPage);
    }

    @ApiOperation("修改学生信息")
    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(
            @ApiParam("学生信息")@RequestBody Student student
    ){
        Integer id = student.getId();
        if(null == id || id == 0){
            student.setPassword(MD5.encrypt(student.getPassword()));
        }
        studentService.saveOrUpdate(student);
        return Result.ok();
    }

    ///sms/studentController/delStudentById
    @ApiOperation("删除学生")
    @DeleteMapping("/delStudentById")
    public Result deleteStudentById(
            @RequestBody List<Integer> ids
    ){
        studentService.removeByIds(ids);
        return Result.ok();
    }
}

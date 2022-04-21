package com.atguigu.zhxy.controller;

import com.atguigu.zhxy.pojo.Teacher;
import com.atguigu.zhxy.service.TeacherService;
import com.atguigu.zhxy.util.MD5;
import com.atguigu.zhxy.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import javafx.scene.control.TableView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "老师信息管理")
@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    ///sms/teacherController/getTeachers/1/3
    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachersByOpr(
            @PathVariable("pageNo") Integer pageNo,
            @PathVariable("pageSize") Integer pageSize,
            Teacher teacher
    ){
        Page page = new Page(pageNo,pageSize);
        IPage ipage =  teacherService.getTeachers(page,teacher);
        return Result.ok(ipage);
    }



    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(
            @RequestBody Teacher teacher
    ){
        Integer id = teacher.getId();
        if(null == id || id == 0){
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }
        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }


    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(
            @RequestBody List<Integer> ids
    ){
        teacherService.removeByIds(ids);
        return Result.ok();
    }
}

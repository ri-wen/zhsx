package com.atguigu.zhxy.controller;

import com.atguigu.zhxy.pojo.Admin;
import com.atguigu.zhxy.pojo.LoginForm;
import com.atguigu.zhxy.pojo.Student;
import com.atguigu.zhxy.pojo.Teacher;
import com.atguigu.zhxy.service.AdminService;
import com.atguigu.zhxy.service.StudentService;
import com.atguigu.zhxy.service.TeacherService;
import com.atguigu.zhxy.util.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Api(tags = "系统控制器")
@ToString
@RestController
@RequestMapping("/sms/system")
public class SystemController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;

    @ApiOperation("获取验证码图片")
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response){
        //获取图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //获取验证码
        String verifiCode = new String(CreateVerifiCodeImage.getVerifiCode());

        //将验证码文本放入session域，为下一次验证做准备
        HttpSession session = request.getSession();
        session.setAttribute("verifiCode",verifiCode);
        //将验证码图片响应给浏览器
        try {
            ImageIO.write(verifiCodeImage,"JPEG",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/login")
    public Result login( @RequestBody LoginForm loginForm, HttpServletRequest request){
        //验证码校验
        HttpSession session = request.getSession();
        String session_verifiCode =(String)session.getAttribute("verifiCode");
        String login_verifiCode = loginForm.getVerifiCode();
        if("".equals(session_verifiCode) || null == session_verifiCode){
            return Result.fail().message("验证码失效，请刷新后重试");
        }

        if(!session_verifiCode.toUpperCase().equals(login_verifiCode.toUpperCase())){
            return Result.fail().message("验证码错误,请小心输入");
        }
        //从session中移除验证码
        session.removeAttribute("verifiCode");
        //分用户类型
        //准备一个map
        Map<String,Object> map = new HashMap<>();
        switch (loginForm.getUserType()){
            case 1:
            try{
                Admin admin = adminService.login(loginForm);
                if(null != admin){
                    //把用户ID和密文以token向客户端返回
                    String token = JwtHelper.createToken(admin.getId().longValue(), 1);
                    map.put("token",token);
                }else{
                    throw new RuntimeException("用户名或密码错误");
                }
                return Result.ok(map);
            }catch (RuntimeException e){
                e.printStackTrace();
                return Result.fail().message(e.getMessage());
            }
            case 2:
                try{
                    Student student = studentService.login(loginForm);
                    if(null != student){
                        //把用户ID和密文以token向客户端返回
                        String token = JwtHelper.createToken(student.getId().longValue(), 2);
                        map.put("token",token);
                    }else{
                        throw new RuntimeException("用户名或密码错误");
                    }
                    return Result.ok(map);
                }catch (RuntimeException e){
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 3:
                try{
                    Teacher teacher = teacherService.login(loginForm);
                    if(null != teacher){
                        //把用户ID和密文以token向客户端返回
                        String token = JwtHelper.createToken(teacher.getId().longValue(), 3);
                        map.put("token",token);
                    }else{
                        throw new RuntimeException("用户名或密码错误");
                    }
                    return Result.ok(map);
                }catch (RuntimeException e){
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
        }
        return Result.fail().message("查无此用户");
    }

    @GetMapping("/getInfo")
    public Result getInfoByToken(@RequestHeader String token){

        boolean expiration = JwtHelper.isExpiration(token);
        if(expiration){
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        //从token中解析
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        Map<String,Object> map = new HashMap<>();
        switch (userType){
            case 1:
                Admin admin = adminService.getAdminById(userId);
                map.put("userType",1);
                map.put("user",admin);
                break;
            case 2:
                Student student = studentService.getStudentById(userId);
                map.put("userType",2);
                map.put("user",student);
                break;
            case 3:
                Teacher teacher = teacherService.getTeacherById(userId);
                map.put("userType",3);
                map.put("user",teacher);
                break;
        }
        return Result.ok(map);
    }



    ///sms/system/headerImgUpload
    @ApiOperation("文件上传统一路口")
    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(
            @ApiParam("图片") @RequestPart("multipartFile") MultipartFile photo){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
        String originalFilename = photo.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newName = uuid+suffix;
        String portraitPath = "E:\\javaEE\\zhxy\\target\\classes\\public\\upload\\".concat(newName);
        try {
            photo.transferTo(new File(portraitPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //保存文件 将文件发到第三方独立服务器上
        String path = "upload\\" + newName;
        //全局文件上传
        return Result.ok(path);
    }


    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(
            @RequestHeader("token") String token,
            @PathVariable("oldPwd") String oldPwd,
            @PathVariable("newPwd") String newPwd
    ){
        boolean expiration = JwtHelper.isExpiration(token);
        if(expiration){
            //token过期
            return Result.fail().message("token失效，重新登录");
        }
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        switch (userType){
            case 1:
                QueryWrapper<Admin> queryWrapper1 = new QueryWrapper();
                queryWrapper1.eq("id",userId);
                queryWrapper1.eq("password",MD5.encrypt(oldPwd));
                Admin admin = adminService.getOne(queryWrapper1);
                if(admin != null){
                    admin.setPassword(MD5.encrypt(newPwd));
                    adminService.saveOrUpdate(admin);
                }else{
                    return Result.fail().message("原密码有误");
                }
                break;
            case 2:
                QueryWrapper<Student> queryWrapper2 = new QueryWrapper();
                queryWrapper2.eq("id",userId);
                queryWrapper2.eq("password",MD5.encrypt(oldPwd));
                Student student = studentService.getOne(queryWrapper2);
                if(student != null){
                    student.setPassword(MD5.encrypt(newPwd));
                    studentService.saveOrUpdate(student);
                }else{
                    return Result.fail().message("原密码有误");
                }
                break;
            case 3:
                QueryWrapper<Teacher> queryWrapper3 = new QueryWrapper();
                queryWrapper3.eq("id",userId);
                queryWrapper3.eq("password",MD5.encrypt(oldPwd));
                Teacher teacher = teacherService.getOne(queryWrapper3);
                if(teacher != null){
                    teacher.setPassword(MD5.encrypt(newPwd));
                    teacherService.saveOrUpdate(teacher);
                }else{
                    return Result.fail().message("原密码有误");
                }
                break;
        }
        return Result.ok();
    }


}

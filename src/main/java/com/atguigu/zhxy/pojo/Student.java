package com.atguigu.zhxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_student")
public class Student {

    @TableId(value = "id" ,type = IdType.AUTO)
    private Integer id;

    private Integer sno;

    private String name;

    private Character gender;

    private String password;

    private String email;

    private String telephone;

    private String address;

    private String portraitPath;

    private String clazzName;
}

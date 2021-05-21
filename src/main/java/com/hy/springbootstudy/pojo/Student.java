package com.hy.springbootstudy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("student")
public class Student {
    @TableId(value = "id",type = IdType.UUID)
    private String id;
    private String name;
    private String sex;
    private Integer classno;
    private String mgr;
    private Date bdate;
    private String img;
    private String pwd;
    private Classes classes;
}

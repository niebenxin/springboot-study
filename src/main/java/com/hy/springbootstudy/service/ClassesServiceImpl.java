package com.hy.springbootstudy.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.springbootstudy.mapper.ClassesMapper;
import com.hy.springbootstudy.pojo.Classes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ClassesServiceImpl extends ServiceImpl<ClassesMapper, Classes> implements ClassesService{

    @Resource
    private ClassesMapper classesMapper;


}

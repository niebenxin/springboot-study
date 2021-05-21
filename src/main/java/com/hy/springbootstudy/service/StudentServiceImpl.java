package com.hy.springbootstudy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.hy.springbootstudy.mapper.StudentMapper;
import com.hy.springbootstudy.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Resource
    private StudentMapper studentMapper;

    @Override
    public IPage<Student> queryAll(String search,String pageIndex) {
        if (!StringUtils.isEmpty(search)) {
            if ("男".equals(search)) {
                search = "M";
            }
            if ("女".equals(search)) {
                search = "F";
            }
        } else {
            search = "";
        }
        Page<Student> studentPage = new Page<>(Integer.parseInt(pageIndex),3);
        return studentMapper.queryAll(studentPage, search);
    }

    @Override
    public IPage<Student> queryAllLay(String search, Integer page, Integer limit) {
        if (!StringUtils.isEmpty(search)) {
            if ("男".equals(search)) {
                search = "M";
            }
            if ("女".equals(search)) {
                search = "F";
            }
        } else {
            search = "";
        }
        /*com.github.pagehelper.Page<Object> pages = PageHelper.startPage(page, 3);
        List<Student> students = studentMapper.queryAll(search);
        Page<Student> studentPage = new Page<>();
        studentPage.setTotal(pages.getTotal());
        studentPage.setRecords(students);
        studentPage.setCurrent(page);
        studentPage.setSize(limit);*/
        Page<Student> studentPage = new Page<>(page, limit);
        return studentMapper.queryAll(studentPage, search);
    }

    @Override
    public Student queryById(String id) {
        return studentMapper.queryById(id);
    }

    @Override
    public Student queryByName(String name) {
        return studentMapper.queryByName(name);
    }

}

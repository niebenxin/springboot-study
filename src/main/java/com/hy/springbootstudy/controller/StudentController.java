package com.hy.springbootstudy.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hy.springbootstudy.pojo.Student;
import com.hy.springbootstudy.service.StudentService;
import com.hy.springbootstudy.util.LayuiData;
import com.hy.springbootstudy.util.VueData;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("student")
@ComponentScan
@CrossOrigin
public class StudentController {

    @Autowired
    @Qualifier("studentServiceImpl")
    private StudentService studentService;

    @RequestMapping(value = "/queryAll")
    public String queryAll(Model model, @RequestParam(defaultValue = "1") String pageIndex, String name) {
        IPage<Student> studentIPage = studentService.queryAll(name, pageIndex);
        List<Student> records = studentIPage.getRecords();
        model.addAttribute("list", records);
        model.addAttribute("page", studentIPage);
        model.addAttribute("end", studentIPage.getPages());
        //${page.records} 查询结果
        //${page.current} 当前页数 2
        //${page.pages} 一页几条  3
        //${page.total} 总条数  10
        //当前总共10  第2页 3条数据
        return "page";
    }

    @RequestMapping(value = "/queryAllLay")
    @ResponseBody
    public LayuiData queryAllLay(Integer page, Integer limit, String name) {
        System.out.println(name + "===========");
        IPage<Student> studentIPage = studentService.queryAllLay(name, page, limit);
        LayuiData lay = new LayuiData();
        lay.setCode(0);
        lay.setMsg("");
        lay.setCount(Integer.valueOf(String.valueOf(studentIPage.getTotal())));
        lay.setData(studentIPage.getRecords());
        return lay;
    }

    @RequestMapping(value = "/queryAllVue")
    @ResponseBody
    public VueData queryAllVue(Integer page, Integer limit, String name) {
        IPage<Student> studentIPage = studentService.queryAllLay(name, page, limit);
        VueData vueData = new VueData();
        vueData.setVueTotal(Integer.parseInt(String.valueOf(studentIPage.getTotal())));
        vueData.setVueData(studentIPage.getRecords());
        vueData.setVueCurrentPage(page);
        vueData.setVuePageSize(limit);
        return vueData;
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public boolean save(Student student, @RequestParam("file") MultipartFile pictureFile, HttpServletRequest request) throws IOException {
        try {
            System.out.println(student);
            if (!StringUtils.isEmpty(pictureFile.getOriginalFilename())) {
                String picName = UUID.randomUUID().toString();
                String oriName = pictureFile.getOriginalFilename();
                String extName = oriName.substring(oriName.lastIndexOf("."));
                File context = new File(request.getServletContext().getRealPath("/"));
                String parent = context.getParent();
                File file = new File(parent + "/upload/" + picName + extName);
                pictureFile.transferTo(file);
                student.setImg(picName + extName);
            } else {
                student.setImg("");
            }
            studentService.save(student);
        } catch (IOException | IllegalStateException e) {
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/saves")
    @ResponseBody
    public boolean saves(@RequestBody Student student) {
        System.out.println(student);
        if(student.getSex().equals("男")){
            student.setSex("F");
        }else {
            student.setSex("M");
        }
        return studentService.save(student);
    }

    @RequestMapping("/queryById/{id}")
    @ResponseBody//如果直接返回list要到Jackson.jar包  还要加ResponseBody
    public Student queryById(@PathVariable("id") String id) {
        Student student = studentService.queryById(id);
        if(student.getSex().equals("F")){
            student.setSex("男");
        }else {
            student.setSex("女");
        }
        return student;
    }

    @RequestMapping("/queryByIds")
    @ResponseBody//如果直接返回list要到Jackson.jar包  还要加ResponseBody
    public Student queryByIds(String id) {
        return studentService.queryById(id);
    }

    @RequestMapping(value = "/update")
    public String update(Student student, @RequestParam("filename") MultipartFile pictureFile, HttpServletRequest request) throws IOException {
        if (!StringUtils.isEmpty(pictureFile.getOriginalFilename())) {
            String picName = UUID.randomUUID().toString();
            String oriName = pictureFile.getOriginalFilename();
            String extName = oriName.substring(oriName.lastIndexOf("."));
            File context = new File(request.getServletContext().getRealPath("/"));
            String parent = context.getParent();
            File file = new File(parent + "/upload/" + picName + extName);
            pictureFile.transferTo(file);
            student.setImg(picName + extName);
        }
        UpdateWrapper<Student> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", student.getId());
        studentService.update(student, updateWrapper);
        return "redirect:/student/queryAll";
    }

    @RequestMapping(value = "/updates")
    @ResponseBody
    public boolean updates(@RequestBody Student student){
        UpdateWrapper<Student> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", student.getId());
        return studentService.update(student, updateWrapper);
    }

    @GetMapping("/delete/{id}")
    @ResponseBody//如果直接返回list要到Jackson.jar包  还要加ResponseBody
    public String delete(@PathVariable("id") String id) {
        if (studentService.removeById(id)) {
            return "1";
        } else {
            return "0";
        }
    }

    @RequestMapping("/deletes")
    public String deletes(String[] ids) {
        for (int i = 0; i < ids.length; i++) {
            studentService.removeById(ids[i]);
        }
        return "redirect:/student/queryAll";
    }

    @RequestMapping("/deletesVue")
    public boolean deletesVue(String ids) {
        String[] idsList = ids.split(",");
        boolean flag=false;
        for (int i = 0; i < idsList.length; i++) {
            flag = studentService.removeById(idsList[i]);
        }
        return flag;
    }

    @RequestMapping("/deleteAll/{a}")
    @ResponseBody
    public String deleteAll(@PathVariable("a") String ids) {
        boolean flag = studentService.removeByIds(Arrays.asList(ids.split(",")));
        if (flag) {
            return "1";
        } else {
            return "0";
        }
    }

    @RequestMapping("/upload")
    @ResponseBody
    public LayuiData upload(@RequestParam("file") MultipartFile pictureFile, HttpServletRequest request) throws IOException {
        String picName = UUID.randomUUID().toString();
        String oriName = pictureFile.getOriginalFilename();
        String extName = oriName.substring(oriName.lastIndexOf("."));
        File context = new File(request.getServletContext().getRealPath("/"));
        String parent = context.getParent();
        File file = new File("D:/apache-tomcat-8.5.56/webapps" + "/upload/" + picName + extName);
        pictureFile.transferTo(file);
        LayuiData layuiData = new LayuiData();
        List<Student> list = new ArrayList<>();
        Student student = new Student();
        student.setImg(picName + extName);
        list.add(student);
        layuiData.setData(list);
        return layuiData;
    }

    @RequestMapping("/uploadVue")
    @ResponseBody
    public LayuiData uploadVue(@RequestParam("file") MultipartFile pictureFile, HttpServletRequest request) throws IOException {
        String picName = UUID.randomUUID().toString();
        String oriName = pictureFile.getOriginalFilename();
        String extName = oriName.substring(oriName.lastIndexOf("."));
        File context = new File(request.getServletContext().getRealPath("/"));
        File file = new File("D:/idea2020work/springboot-study/src/main/resources/static/images/" + picName + extName);
        pictureFile.transferTo(file);
        LayuiData layuiData = new LayuiData();
        List<Student> list = new ArrayList<>();
        Student student = new Student();
        student.setImg(picName + extName);
        list.add(student);
        layuiData.setData(list);
        return layuiData;
    }

}

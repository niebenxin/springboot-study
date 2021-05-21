package com.hy.springbootstudy.controller;

import com.hy.springbootstudy.pojo.Classes;
import com.hy.springbootstudy.service.ClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@Transactional
@RequestMapping("/classes")
@CrossOrigin
public class ClassesController {

    @Autowired
    private ClassesService classesService;

    @RequestMapping("/queryAll")
    public ArrayList<Classes> queryAll() {
        return (ArrayList<Classes>)classesService.list();
    }

}

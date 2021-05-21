package com.hy.springbootstudy.mapper;

import com.hy.springbootstudy.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisDao {

    /*@Autowired
    private RedisTemplate redisTemplate;

    public void add(Student student){
        redisTemplate.opsForList().leftPush("list",student);
    }*/

}

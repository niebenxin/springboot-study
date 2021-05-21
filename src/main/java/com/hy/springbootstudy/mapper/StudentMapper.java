package com.hy.springbootstudy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.springbootstudy.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    IPage<Student> queryAll(Page<Student> page, @Param("name")String name);

//    List<Student> queryAll(@Param("name") String name);

    @Select("select * from student where id=#{stuId}")
    Student queryById(@Param("stuId") String id);

    @Select("select * from student where name=#{name}")
    Student queryByName(@Param("name") String name);

}

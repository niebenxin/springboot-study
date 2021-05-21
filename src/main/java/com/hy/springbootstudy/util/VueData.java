package com.hy.springbootstudy.util;

import com.hy.springbootstudy.pojo.Student;
import lombok.Data;

import java.util.List;

@Data
public class VueData {

    private Integer vueTotal;
    private Integer vueCurrentPage;
    private Integer vuePageSize;
    private List<Student> vueData;
}

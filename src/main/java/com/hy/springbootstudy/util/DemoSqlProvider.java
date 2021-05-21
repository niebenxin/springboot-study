package com.hy.springbootstudy.util;

import com.hy.springbootstudy.pojo.Student;

public class DemoSqlProvider {
    public String selectAll(Student student) {
        StringBuffer sb = new StringBuffer("select s.*,c.* from student s left join classes c on s.classno = c.classno");
        if (student.getName() != null && student.getName() != "") {
            //<bind name="names" value="'%'+name+'%'" /> '%name%'
            sb.append("  and s.name LIKE CONCAT(CONCAT('%', #{name}),'%')");
        }
        if (student.getSex() != null && student.getSex() != "") {
            sb.append("  and s.sex = #{sex}");
        }
        if (student.getClassno() != null) {
            sb.append(" and  s.classno = #{classno}");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }
}

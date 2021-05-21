package com.hy.springbootstudy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hy.springbootstudy.pojo.Role;
import com.hy.springbootstudy.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户ID查询对应的角色
     * @param uids
     * @return
     */
    @Select("select r.rid,r.rname from USERANDROLE u,role r where u.rid=r.rid and u.uids=#{uids}")
    List<Role> queryRole(@Param("uids") String uids);

    @Select("select j.jname from  ROLEANDJURISDICTION r,JURISDICTION j where r.JID = j.JID and r.RID = #{rid}")
    List<String> queryJurisdiction(@Param("rid") int rid);
}

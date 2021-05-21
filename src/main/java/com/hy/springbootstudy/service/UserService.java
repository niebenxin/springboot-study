package com.hy.springbootstudy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hy.springbootstudy.pojo.Role;
import com.hy.springbootstudy.pojo.User;

import java.util.List;

public interface UserService extends IService<User> {

    List<Role> queryRole(String uids);
    List<String> queryJurisdiction(int rid);
}

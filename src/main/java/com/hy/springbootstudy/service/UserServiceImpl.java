package com.hy.springbootstudy.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.springbootstudy.mapper.UserMapper;
import com.hy.springbootstudy.pojo.Role;
import com.hy.springbootstudy.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{
    @Resource
    private UserMapper userMapper;

    @Override
    public List<Role> queryRole(String uids) {
        return userMapper.queryRole(uids);
    }

    @Override
    public List<String> queryJurisdiction(int rid) {
        return userMapper.queryJurisdiction(rid);
    }
}

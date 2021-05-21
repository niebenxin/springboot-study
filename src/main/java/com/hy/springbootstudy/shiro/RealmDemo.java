package com.hy.springbootstudy.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hy.springbootstudy.pojo.Role;
import com.hy.springbootstudy.pojo.User;
import com.hy.springbootstudy.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RealmDemo extends AuthorizingRealm {

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     *  授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权");
        //授权
        //1、先拿到用户名
        Object object=principalCollection.getPrimaryPrincipal();
        System.out.println(object);
        //2、根据用户名查询数据库得到角色和权限
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",object);
        User user = userService.getOne(queryWrapper);

        //角色
        List<Role> list = userService.queryRole(user.getUids());
        Set<String> set = new HashSet<>();
        List<String> jlist=null;
        for (Role role:list){
            jlist = userService.queryJurisdiction(role.getRid());
            set.add(role.getRname());
        }
        //权限
        Set<String> jset = new HashSet<>();
        for (String s:jlist){
            jset.add(s);
        }
        //3、返回授权的信息类
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(set);
        authorizationInfo.addStringPermissions(jset);
        return authorizationInfo;
    }

    /**
     *  认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证");
        UsernamePasswordToken upt = (UsernamePasswordToken)authenticationToken;
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",upt.getUsername());
        User user = userService.getOne(queryWrapper);
        if(user==null){
            throw new UnknownAccountException("此用户不存在");
        }
        ByteSource bytes = ByteSource.Util.bytes(upt.getUsername());
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user.getUsername(),user.getPwd(),bytes,getName());
        return simpleAuthenticationInfo;
    }
}

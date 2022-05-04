package com.cloudmusic.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloudmusic.admin.entity.Admin;
import com.cloudmusic.admin.entity.ResultVO;
import com.cloudmusic.admin.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public ResultVO login(String account, String pwd) {
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<Admin>();
        adminQueryWrapper.eq("account", account);
        Admin admin = adminMapper.selectOne(adminQueryWrapper);
        if(Objects.nonNull(admin)) {
            if(pwd.equals(admin.getPassword())) {
                return ResultVO.builder().message("登录成功").success(true).build();
            }
            return ResultVO.builder().success(false).message("账号或密码不正确").build();
        }
        return ResultVO.builder().success(false).message("用户不存在").build();
    }
}

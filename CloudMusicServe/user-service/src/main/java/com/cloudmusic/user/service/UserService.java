package com.cloudmusic.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloudmusic.feign.clients.SongClients;
import com.cloudmusic.feign.entity.QueryInfo;
import com.cloudmusic.feign.entity.SongVO;
import com.cloudmusic.user.entity.ResultVO;
import com.cloudmusic.user.entity.User;
import com.cloudmusic.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SongClients songClients;

    public ResultVO insertUser(User user) {
        user.setUserAccess(0);
        //todo 此处应该写sql
        List<User> users = userMapper.selectList(null);
        for (User exitUser : users) {
            if(exitUser.getUserName().equals(user.getUserName())) {
                return ResultVO.builder().success(false).message("用户已存在").build();
            }
        }
        userMapper.insert(user);
        return ResultVO.builder().success(true).message("创建成功").build();
    }

    public User selectUser(String name) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name", name);
        return userMapper.selectOne(userQueryWrapper);
    }

    public ResultVO login(User user){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name", user.getUserName());
        User selectedUser = userMapper.selectOne(userQueryWrapper);
        if(Objects.nonNull(selectedUser)&&selectedUser.getUserPwd().equals(user.getUserPwd())) {
            return ResultVO.builder().message("登录成功").success(true).build();
        }
        return ResultVO.builder().message("账号或密码错误").success(false).build();
    }

    public HashMap<String, Object> getDefaultSong(String userName) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name", userName);
        User selectedUser = userMapper.selectOne(userQueryWrapper);
        QueryInfo queryInfo = new QueryInfo();
        queryInfo.setMessage(selectedUser.getUserLike());
        List<SongVO> song = songClients.getSong(queryInfo);
        HashMap<String, Object> map = new HashMap<>();
        map.put("songs",song);
        return map;
    }

    public User getUserInfo(String userName) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name", userName);
        return userMapper.selectOne(userQueryWrapper);
    }
}

package com.cloudmusic.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudmusic.feign.clients.SongClients;
import com.cloudmusic.feign.entity.QueryInfo;
import com.cloudmusic.feign.entity.SongVO;
import com.cloudmusic.feign.util.StringToListUtil;
import com.cloudmusic.user.delayedTask.DeleteNoUseImage;
import com.cloudmusic.user.entity.PageInfo;
import com.cloudmusic.user.entity.ResultVO;
import com.cloudmusic.user.entity.User;
import com.cloudmusic.user.mapper.UserMapper;
import com.cloudmusic.user.util.MinioUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SongClients songClients;

    @Autowired
    private MinioUtil minioUtil;

    @Autowired
    private DeleteNoUseImage deleteNoUseImage;

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
        map.put("user", selectedUser);
        return map;
    }

    public User getUserInfo(String userName) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name", userName);
        return userMapper.selectOne(userQueryWrapper);
    }

    public String addSongSheet(String songSheetName, String userName) {
        Long songSheetId = songClients.addSongSheet(songSheetName, userName);
        if(songSheetId == 0) {
            return "添加失败，后端服务出现异常";
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name", userName);
        User selectedUser = userMapper.selectOne(userQueryWrapper);
        String userSongSheet = selectedUser.getUserSongSheet();
        //用户原来有歌单
        if(StringUtils.isNotBlank(StringUtils.strip(userSongSheet, "[]"))) {
            List<String> songSheetList = StringToListUtil.StringToList(userSongSheet);
            songSheetList.add(Long.toString(songSheetId).replaceAll(" ",""));
            selectedUser.setUserSongSheet(songSheetList.toString().replaceAll(" ", ""));
            userMapper.updateById(selectedUser);
        } else {
            //用户原来没有歌单
            List<String> songSheetList = new ArrayList<>();
            songSheetList.add(Long.toString(songSheetId).replaceAll(" ",""));
            selectedUser.setUserSongSheet(songSheetList.toString().replaceAll(" ", ""));
            userMapper.updateById(selectedUser);
        }
        return "添加成功";
    }

    public String deleteSongSheet(String userName, String songSheetId) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name", userName);
        User selectedUser = userMapper.selectOne(userQueryWrapper);
        String userSongSheet = selectedUser.getUserSongSheet();
        String strip = StringUtils.strip(userSongSheet, "[]");
        List<String> sheetList = Arrays.stream(strip.split(",")).collect(Collectors.toList());
        sheetList.remove(songSheetId);
        if(songClients.deleteSongSheet(songSheetId)) {
            selectedUser.setUserSongSheet(sheetList.toString().replaceAll(" ", ""));
            userMapper.updateById(selectedUser);
            return "删除成功";
        }
        return "删除失败";
    }

    public ResultVO addUserCollection(String userName, String songSheetId) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name", userName);
        User selectedUser = userMapper.selectOne(userQueryWrapper);
        String userCollection = selectedUser.getUserCollection();
        if(StringUtils.isNotBlank(userCollection)) {
            if(userCollection.contains(songSheetId)) {
                return ResultVO.builder().message("不能重复添加").success(false).build();
            }
            selectedUser.setUserCollection(userCollection+","+songSheetId);
            userMapper.updateById(selectedUser);
        } else {
            selectedUser.setUserCollection(songSheetId);
            userMapper.updateById(selectedUser);
        }
        return ResultVO.builder().message("添加成功").success(true).build();
    }

    public HashMap<String, Object> getAllUser(PageInfo pageInfo) {
        QueryWrapper<User> userQueryWrapper = null;
        if(StringUtils.isNotBlank(pageInfo.getQuery())) {
            userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("user_nickName", pageInfo.getQuery());
        }

        Page<User> userPage = new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize());
        Page<User> page = userMapper.selectPage(userPage, userQueryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("user", page.getRecords());
        map.put("total", page.getTotal());
        return map;
    }

    public String uploadAvatar(MultipartFile[] multipartFile) {
        List<String> upload = minioUtil.upload(multipartFile);
        deleteNoUseImage.addItem(upload.get(0).replace("http://121.40.137.246:9000/cloudmusic/", ""));
        return upload.get(0);
    }

    public ResultVO updateUser(String userName, String nickName, String userImage) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name", userName);
        User user = userMapper.selectOne(userQueryWrapper);
        if(Objects.nonNull(user)) {
            user.setUserImage(userImage);
            user.setUserNickname(nickName);
            userMapper.updateById(user);
            deleteNoUseImage.deleteItem(userImage.replace("http://121.40.137.246:9000/cloudmusic/", ""));
            return ResultVO.builder().message("修改成功").success(true).build();
        }
        return ResultVO.builder().message("用户不存在").success(false).build();
    }

    public ResultVO updatePassword(String userName, String password) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name", userName);
        User user = userMapper.selectOne(userQueryWrapper);
        if(Objects.nonNull(user)) {
            user.setUserPwd(password);
            userMapper.updateById(user);
            return ResultVO.builder().success(true).message("修改成功").build();
        }
        return ResultVO.builder().success(false).message("用户不存在").build();
    }
}

package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/4/26.
*/

import com.alibaba.fastjson.JSONObject;
import com.wizzstudio.languagerank.dao.ClazzDAO.ClazzDAO;
import com.wizzstudio.languagerank.dao.ClazzDAO.ClazzStudyPlanDAO;
import com.wizzstudio.languagerank.dao.ClazzDAO.UserClazzDAO;
import com.wizzstudio.languagerank.dao.UserDAO.UserDAO;
import com.wizzstudio.languagerank.domain.Clazz.Clazz;
import com.wizzstudio.languagerank.domain.Clazz.ClazzStudyPlan;
import com.wizzstudio.languagerank.domain.Clazz.UserClazz;
import com.wizzstudio.languagerank.dto.AllClazzListDTO;
import com.wizzstudio.languagerank.dto.CreateClazzDTO;
import com.wizzstudio.languagerank.dto.UserClazzListDTO;
import com.wizzstudio.languagerank.service.ClazzService;
import com.wizzstudio.languagerank.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    ClazzDAO clazzDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    UserClazzDAO userClazzDAO;
    @Autowired
    ClazzStudyPlanDAO clazzStudyPlanDAO;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public Clazz createClazz(CreateClazzDTO createClazzDTO) {
        Clazz clazz = new Clazz();
        clazz.setClazzName(createClazzDTO.getClazzName());
        clazz.setMonitor(createClazzDTO.getMonitor());
        clazz.setClazzTag(createClazzDTO.getClazzTag());
        clazz.setClazzImage(createClazzDTO.getClazzImage());
        clazz.setStudentNumber(1);

//        List<User> userList = new ArrayList<>();
//        userList.add(userDAO.findByUserId(createClazzDTO.getMonitor()));
//        clazz.setUserList(userList);
        clazzDAO.save(clazz);
        ListIterator listIterator = createClazzDTO.getClazzStudyPlanList().listIterator();
        while (listIterator.hasNext()) {
            ClazzStudyPlan clazzStudyPlan = (ClazzStudyPlan)listIterator.next();
            clazzStudyPlan.setClazzId(clazz.getClazzId());
        }
        clazzStudyPlanDAO.saveAll(createClazzDTO.getClazzStudyPlanList());

        joinClazz(createClazzDTO.getMonitor(), clazz.getClazzId());

        return clazz;
    }

    @Override
    public List<UserClazzListDTO> getUserClazzList(Integer userId) {
        List<Clazz> clazzList = userDAO.findUserClazz(userId);
        List<UserClazzListDTO> userClazzListDTOList = new ArrayList<>();
        // 更大的网络包与更长的执行时间如何选择
        for (Clazz clazz : clazzList) {
            UserClazzListDTO userClazzListDTO = new UserClazzListDTO();
            userClazzListDTO.setClazzId(clazz.getClazzId());
            userClazzListDTO.setClazzImage(clazz.getClazzImage());
            userClazzListDTO.setClazzName(clazz.getClazzName());
            userClazzListDTO.setMonitor(clazz.getMonitor());

            userClazzListDTOList.add(userClazzListDTO);
        }
        return userClazzListDTOList;
    }

    @Override
    public List<AllClazzListDTO> getAllClazzList() {
        return clazzDAO.findAllClazz();
    }

    @Override
    public void joinClazz(Integer userId, Integer clazzId) {
        UserClazz userClazz = new UserClazz();
        userClazz.setUserId(userId);
        userClazz.setClazzId(clazzId);
        userClazz.setJoinedTime(new Date());
        // 还有问题，当用户一天重复加入某一班级时
        userClazz.setAllStudyPlanDay(1);
        userClazz.setUninterruptedStudyPlanDay(1);

        userClazzDAO.save(userClazz);
    }

    @Override
    public List<String> getClazzStudyPlan(Integer clazzId) {
        return clazzStudyPlanDAO.getAllContent(clazzId);
    }

    @Override
    public Map<String, Object> getClazzMember(JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");
        Integer clazzId = jsonObject.getInteger("clazzId");
        Integer pageIndex = jsonObject.getInteger("pageIndex");




        // 没有传用户昵称头像
        Map<String, Object> clazzMemberMap = new HashMap<>();
        List<UserClazz> memberList = userClazzDAO.findByClazzId(clazzId);

        UserClazz monitor = userClazzDAO.findByClazzIdAndUserId(clazzId, clazzDAO.findMonitorByClazzId(clazzId));
        clazzMemberMap.put("monitor", monitor);
        memberList.remove(monitor);

        List<String> stringList = new ArrayList<>(redisUtil.getUserRelationship(userId));
        List<Integer> integerList = stringList.stream().map(Integer::parseInt).collect(Collectors.toList());
        List<UserClazz> friendList = new ArrayList<>();
        for (Integer user : integerList) {
            friendList.add(userClazzDAO.findByClazzIdAndUserId(clazzId, user));
        }

        clazzMemberMap.put("friend", friendList);
        memberList.removeAll(friendList);

        clazzMemberMap.put("others", memberList);
        return clazzMemberMap;
    }
}

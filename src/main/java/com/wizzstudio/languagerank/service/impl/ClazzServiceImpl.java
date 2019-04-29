package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/4/26.
*/

import com.alibaba.fastjson.JSONObject;
import com.wizzstudio.languagerank.constants.Constant;
import com.wizzstudio.languagerank.dao.ClazzDAO.ClazzDAO;
import com.wizzstudio.languagerank.dao.ClazzDAO.ClazzStudyPlanDAO;
import com.wizzstudio.languagerank.dao.ClazzDAO.UserClazzDAO;
import com.wizzstudio.languagerank.dao.ClazzDAO.UserJoinedClazzDAO;
import com.wizzstudio.languagerank.dao.UserDAO.UserDAO;
import com.wizzstudio.languagerank.domain.Clazz.Clazz;
import com.wizzstudio.languagerank.domain.Clazz.ClazzStudyPlan;
import com.wizzstudio.languagerank.domain.Clazz.UserClazz;
import com.wizzstudio.languagerank.domain.Clazz.UserJoinedClazz;
import com.wizzstudio.languagerank.domain.User.User;
import com.wizzstudio.languagerank.dto.AllClazzListDTO;
import com.wizzstudio.languagerank.dto.ClazzMemberDTO;
import com.wizzstudio.languagerank.dto.CreateClazzDTO;
import com.wizzstudio.languagerank.dto.UserClazzListDTO;
import com.wizzstudio.languagerank.service.ClazzService;
import com.wizzstudio.languagerank.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    UserJoinedClazzDAO userJoinedClazzDAO;
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
        userClazz.setAllStudyPlanDay(1);
        // 还有问题，当用户一天重复加入某一班级时;
        userClazz.setUninterruptedStudyPlanDay(1);

        userClazzDAO.save(userClazz);
    }

    // 重复加入班级的一串逻辑
    @Override
    public void quitClazz(Integer userId, Integer clazzId) {
        UserClazz userClazz = userClazzDAO.findByClazzIdAndUserId(userId, clazzId);
        UserJoinedClazz userJoinedClazz = new UserJoinedClazz();
        userJoinedClazz.setUserId(userId);
        userJoinedClazz.setJoinedClazzId(clazzId);
        userJoinedClazz.setIsJoinedToday(true);
        userJoinedClazz.setStudyPlanDay(userClazz.getAllStudyPlanDay());
        userClazzDAO.delete(userClazz);
    }

    @Override
    public List<String> getClazzStudyPlan(Integer clazzId) {
        return clazzStudyPlanDAO.getAllContent(clazzId);
    }

    @Override
    public Map<String, Object> getSpecialClazzMember(JSONObject jsonObject) {
        Integer userId = jsonObject.getInteger("userId");
        Integer clazzId = jsonObject.getInteger("clazzId");

        Map<String, Object> clazzMemberMap = new HashMap<>();

        ClazzMemberDTO monitor = new ClazzMemberDTO();
        User monitorDomain = userDAO.findByUserId(clazzDAO.findMonitorByClazzId(clazzId));
        monitor.setNickName(monitorDomain.getNickName());
        monitor.setAvatarUrl(monitorDomain.getAvatarUrl());
        clazzMemberMap.put("monitor", monitor);

        List<String> stringList = new ArrayList<>(redisUtil.getUserRelationship(userId));
        List<Integer> integerList = stringList.stream().map(Integer::parseInt).collect(Collectors.toList());
        List<ClazzMemberDTO> friendList = new ArrayList<>();
        for (Integer friendId : integerList) {
            ClazzMemberDTO friend = new ClazzMemberDTO();
            User friendDomain = userDAO.findByUserId(friendId);
            friend.setAvatarUrl(friendDomain.getAvatarUrl());
            friend.setNickName(friendDomain.getNickName());
            friendList.add(friend);
        }
        clazzMemberMap.put("friend", friendList);

        return clazzMemberMap;
    }

    @Override
    public List<ClazzMemberDTO> getClazzMember(JSONObject jsonObject) {
        Integer clazzId = jsonObject.getInteger("clazzId");
        Integer pageIndex = jsonObject.getInteger("pageIndex");

        // 按连续打卡天数降序排列
        List<UserClazz> memberList =  userClazzDAO.findAll(
                (Specification<UserClazz>) (root, criteriaQuery, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("clazzId"), clazzId),
                PageRequest.of(pageIndex - 1, Constant.PAGE_SIZE,
                       Sort.Direction.DESC, "uninterruptedStudyPlanDay"))
                .getContent();

        List<ClazzMemberDTO> clazzMemberDTOList = new ArrayList<>();
        for (UserClazz userClazz : memberList) {
            User user = userDAO.findByUserId(userClazz.getUserId());
            ClazzMemberDTO clazzMemberDTO = new ClazzMemberDTO();

            clazzMemberDTO.setJoinedTime(userClazz.getJoinedTime());
            clazzMemberDTO.setUninterruptedStudyPlanDay(userClazz.getUninterruptedStudyPlanDay());
            clazzMemberDTO.setNickName(user.getNickName());
            clazzMemberDTO.setAvatarUrl(user.getAvatarUrl());

            clazzMemberDTOList.add(clazzMemberDTO);
        }

        return clazzMemberDTOList;
    }
}

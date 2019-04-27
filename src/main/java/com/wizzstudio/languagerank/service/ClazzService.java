package com.wizzstudio.languagerank.service;

/*
Created by Ben Wen on 2019/4/26.
*/

import com.alibaba.fastjson.JSONObject;
import com.wizzstudio.languagerank.domain.Clazz.Clazz;
import com.wizzstudio.languagerank.dto.AllClazzListDTO;
import com.wizzstudio.languagerank.dto.CreateClazzDTO;
import com.wizzstudio.languagerank.dto.UserClazzListDTO;

import java.util.List;
import java.util.Map;

public interface ClazzService {
    /**
     *  创建班级
     */
    Clazz createClazz(CreateClazzDTO createClazzDTO);

    /**
     *  获取用户已加入班级列表
     */
    List<UserClazzListDTO> getUserClazzList(Integer userId);

    /**
     * 获取班级列表
     */
    List<AllClazzListDTO> getAllClazzList();

    /**
     * 加入班级
     */
    void joinClazz(Integer userId, Integer clazzId);

    /**
     *  获取某班级课程详情
     */
    List<String> getClazzStudyPlan(Integer clazzId);

    /**
     *  获取班级成员列表(班长和好友优先显示)
     */
    Map<String, Object> getClazzMember(JSONObject jsonObject);
}

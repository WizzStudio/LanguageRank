package com.wizzstudio.languagerank.service;

/*
Created by Ben Wen on 2019/4/26.
*/

import com.wizzstudio.languagerank.domain.Clazz.Clazz;
import com.wizzstudio.languagerank.dto.ClazzDTO;
import com.wizzstudio.languagerank.dto.CreateClazzDTO;

import java.util.List;

public interface ClazzService {
    /**
     *  创建班级
     */
    Clazz createClazz(CreateClazzDTO createClazzDTO);

    /**
     * 获取班级列表
     */
    List<ClazzDTO> getClazzList();

    /**
     * 加入班级
     */
    void joinClazz(Integer userId, Integer clazzId);
}

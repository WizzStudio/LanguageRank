package com.wizzstudio.languagerank.dao.ClazzDAO;

/*
Created by Ben Wen on 2019/4/26.
*/

import com.wizzstudio.languagerank.domain.Clazz.UserClazz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserClazzDAO extends JpaRepository<UserClazz, Integer>, JpaSpecificationExecutor<UserClazz> {
    List<UserClazz> findByClazzId(Integer clazzId);

    UserClazz findByClazzIdAndUserId(Integer clazzId, Integer userId);
}

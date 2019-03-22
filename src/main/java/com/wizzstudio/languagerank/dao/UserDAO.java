package com.wizzstudio.languagerank.dao;

/*
Created by Ben Wen on 2019/3/16.
*/

import com.wizzstudio.languagerank.domain.User;
import com.wizzstudio.languagerank.enums.StudyPlanDayEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDAO extends JpaRepository<User, Integer> {
    User findByOpenId(String openId);

    @Modifying
    @Query("update User u set u.studyPlanDay = :studyPlanDay where u.openId = :openId")
    void updateStudyPlanDay(@Param("studyPlanDay") StudyPlanDayEnum studyPlanDay, @Param("openId") String openId);

    @Modifying
    @Query("update User u set u.myLanguage = :myLanguage where u.openId = :openId")
    void updateMyLanguage(@Param("myLanguage")String myLanguage, @Param("openId") String openId);
}

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

    User findByUserId(Integer userId);

    @Modifying
    @Query("update User u set u.isLogInToday = false")
    void resetIsLogInToday();

    @Modifying
    @Query("update User u set u.isLogInToday = true where u.userId = :userId")
    void updateIsLogInToday(@Param("userId") Integer userId);

    @Modifying
    @Query("update User u set u.studyPlanDay = :studyPlanDay where u.userId = :userId")
    void updateStudyPlanDay(@Param("studyPlanDay") StudyPlanDayEnum studyPlanDay, @Param("userId") Integer userId);

    @Modifying
    @Query("update User u set u.myLanguage = :myLanguage where u.userId = :userId")
    void updateMyLanguage(@Param("myLanguage") String myLanguage, @Param("userId") Integer userId);

    @Modifying
    @Query("update User u set u.isViewedJoinMyApplet = false where u.userId = :userId")
    void updateIsViewedJoinMyApplet(@Param("userId") Integer userId);
}

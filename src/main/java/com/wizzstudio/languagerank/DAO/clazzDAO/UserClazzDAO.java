package com.wizzstudio.languagerank.DAO.clazzDAO;

/*
Created by Ben Wen on 2019/4/26.
*/

import com.wizzstudio.languagerank.domain.clazz.UserClazz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserClazzDAO extends JpaRepository<UserClazz, Integer>, JpaSpecificationExecutor<UserClazz> {
    List<UserClazz> findByClazzId(Integer clazzId);

    /**
     * 通过clazzId与userId查询某学员在某班级的具体情况
     */
    UserClazz findByClazzIdAndUserId(Integer clazzId, Integer userId);

    /**
     * 查询某班级所有学员的userId
     */
    @Query("select u.userId from UserClazz u where u.clazzId = :clazzId")
    List<Integer> findAllUserIdInClazz(@Param("clazzId")Integer clazzId);

    /**
     * 计算某班级学生总数
     */
    @Query("select count(u) from UserClazz u where u.clazzId = :clazzId")
    Integer getTheNumberOfStudents(@Param("clazzId")Integer clazzId);

    /**
     * 查询用户已加入的班级的clazzId
     */
    @Query("select u.clazzId from UserClazz u where u.userId = :userId")
    List<Integer> findUserJoinedClazz(@Param("userId")Integer userId);

    @Query("select u.isStudyToday from UserClazz u where u.clazzId = :clazzId and u.userId = :userId")
    Boolean findIsStudyToday(@Param("clazzId")Integer clazzId, @Param("userId")Integer userId);

    @Modifying
    @Query("update UserClazz u set u.isStudyToday = false")
    void resetIsStudyToday();

    @Modifying
    @Query("update UserClazz u set u.uninterruptedStudyPlanDay = 0 where u.isStudyToday = false")
    void resetUninterruptedStudyPlanDay();

    /**
     * 删除班级
     */
    @Modifying
    @Transactional
    @Query("delete from UserClazz u where u.clazzId=:clazzId")
    void deleteUserClazz(@Param("clazzId") Integer clazzId);
}

package com.wizzstudio.languagerank.DAO.clazzDAO;

/*
Created by Ben Wen on 2019/4/26.
*/

import com.wizzstudio.languagerank.VO.ClazzMessageVO;
import com.wizzstudio.languagerank.domain.clazz.Clazz;
import com.wizzstudio.languagerank.VO.AllClazzVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClazzDAO extends JpaRepository<Clazz, Integer> {
    @Query("select new com.wizzstudio.languagerank.VO.AllClazzVO(c.clazzId, c.clazzName, c.studentNumber) from Clazz c")
    List<AllClazzVO> findAllClazz();

    @Query("select c.monitor from Clazz c where c.clazzId = :clazzId")
    Integer findMonitorByClazzId(@Param("clazzId") Integer clazzId);

    Clazz findByClazzId(Integer clazzId);

    @Query("select new com.wizzstudio.languagerank.VO.ClazzMessageVO(" +
            "c.clazzImage, c.clazzName, c.studentNumber, c.commentNumber) from Clazz c where c.clazzId = :clazzId")
    ClazzMessageVO getClazzMessage(@Param("clazzId") Integer clazzId);

    @Query("select c.clazzBriefIntroduction from Clazz c where c.clazzId = :clazzId")
    String findClazzBriefIntroduction(@Param("clazzId") Integer clazzId);

    @Query("select sum(c.studentNumber) from Clazz c where c.clazzTag = :clazzTag")
    Integer findClazzStudentNumberByClazzTag(@Param("clazzTag")String clazzTag);

//    @Query("select c.userList from clazz c")
//    List<user> findAllUser();
}

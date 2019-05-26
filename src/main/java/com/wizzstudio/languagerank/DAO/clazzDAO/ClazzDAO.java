package com.wizzstudio.languagerank.DAO.clazzDAO;

/*
Created by Ben Wen on 2019/4/26.
*/

import com.wizzstudio.languagerank.DTO.admin.AdminProjectDTO;
import com.wizzstudio.languagerank.VO.ClazzMessageVO;
import com.wizzstudio.languagerank.domain.clazz.Clazz;
import com.wizzstudio.languagerank.VO.AllClazzVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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

    @Query(nativeQuery = true, value = "select sum(commentNumber) from Clazz    ")
    Integer getCommentNumber();

    @Query("select new com.wizzstudio.languagerank.DTO.admin.AdminProjectDTO(" +
            "c.clazzImage, c.clazzName, c.studentNumber, c.commentNumber) from Clazz c ")
    List<AdminProjectDTO> findAllClazzBack(PageRequest pageRequest);

    /**
     * 后台更新班级信息
     * @param clazzName
     * @param monitor
     * @param clazzImage
     * @param clazzId
     */
    @Modifying
    @Transactional
    @Query("update Clazz as c set c.clazzName=:clazzName, c.monitor=:monitor, c.clazzImage=:clazzImage where c.clazzId=:clazzId")
    void updateAdminClazz(@Param("clazzName")String clazzName, @Param("monitor")Integer monitor,
                          @Param("clazzImage")String clazzImage, @Param("clazzId")Integer clazzId);

    /**
     * 删除班级
     */
    @Modifying
    @Transactional
    @Query("delete from Clazz as c where c.clazzId=clazzId")
    void deleteClazz(@Param("clazzId") Integer clazzId);

//    @Query(nativeQuery = true, value = "select sum(worship) from Clazz")
//    Integer getWorkShipNumber();

//    @Query("select c.userList from clazz c")
//    List<user> findAllUser();
}

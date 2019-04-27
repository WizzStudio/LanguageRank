package com.wizzstudio.languagerank.dao.ClazzDAO;

/*
Created by Ben Wen on 2019/4/26.
*/

import com.wizzstudio.languagerank.domain.Clazz.Clazz;
import com.wizzstudio.languagerank.domain.User.User;
import com.wizzstudio.languagerank.dto.AllClazzListDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClazzDAO extends JpaRepository<Clazz, Integer> {
    @Query("select new com.wizzstudio.languagerank.dto.AllClazzListDTO(c.clazzId, c.clazzName, c.clazzTag, c.monitor, c.studentNumber) from Clazz c")
    List<AllClazzListDTO> findAllClazz();

    @Query("select c.monitor from Clazz c where c.clazzId = :clazzId")
    Integer findMonitorByClazzId(@Param("clazzId") Integer clazzId);

    Clazz findByClazzId(Integer clazzId);

//    @Query("select c.userList from Clazz c")
//    List<User> findAllUser();
}

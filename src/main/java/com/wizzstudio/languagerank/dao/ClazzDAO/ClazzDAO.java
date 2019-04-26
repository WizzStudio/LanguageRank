package com.wizzstudio.languagerank.dao.ClazzDAO;

/*
Created by Ben Wen on 2019/4/26.
*/

import com.wizzstudio.languagerank.domain.Clazz.Clazz;
import com.wizzstudio.languagerank.dto.ClazzDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClazzDAO extends JpaRepository<Clazz, Integer> {
    @Query("select new com.wizzstudio.languagerank.dto.ClazzDTO(c.clazzId, c.clazzName, c.monitor, c.studentNumber) from Clazz c")
    List<ClazzDTO> findAllClazz();

    Clazz findByClazzId(Integer clazzId);
}

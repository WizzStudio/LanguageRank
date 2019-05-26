package com.wizzstudio.languagerank.DAO.clazzDAO;

/*
Created by Ben Wen on 2019/4/25.
*/

import com.wizzstudio.languagerank.domain.clazz.ClazzComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClazzCommentDAO extends JpaRepository<ClazzComment, Integer>, JpaSpecificationExecutor<ClazzComment> {
    @Query("select count(c) from ClazzComment c where c.clazzId = :clazzId")
    Integer getTheNumberOfComment(@Param("clazzId") Integer clazzId);
}

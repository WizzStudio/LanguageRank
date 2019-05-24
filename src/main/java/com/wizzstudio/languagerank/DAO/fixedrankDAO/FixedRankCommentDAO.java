package com.wizzstudio.languagerank.DAO.fixedrankDAO;

/*
Created by Ben Wen on 2019/4/24.
*/

import com.wizzstudio.languagerank.domain.fixedrank.FixedRankComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FixedRankCommentDAO extends JpaRepository<FixedRankComment, Integer>, JpaSpecificationExecutor<FixedRankComment> {
    @Query("select count(f) from FixedRankComment f where f.languageName = :languageName")
    Integer getTheNumberOfComment(@Param("languageName") String languageName);
}

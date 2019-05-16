package com.wizzstudio.languagerank.DAO.employeerankDAO;

/*
Created by Ben Wen on 2019/4/24.
*/

import com.wizzstudio.languagerank.domain.employeerank.EmployeeRankComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRankCommentDAO extends JpaRepository<EmployeeRankComment, Integer>, JpaSpecificationExecutor<EmployeeRankComment> {
    @Query("select count(e) from EmployeeRankComment e where e.languageName = languageName")
    Integer getTheNumberOfComment(@Param("languageName") String languageName);
}

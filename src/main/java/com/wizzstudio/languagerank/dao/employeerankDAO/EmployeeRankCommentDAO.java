package com.wizzstudio.languagerank.dao.employeerankDAO;

/*
Created by Ben Wen on 2019/4/24.
*/

import com.wizzstudio.languagerank.domain.EmployeeRank.EmployeeRankComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeeRankCommentDAO extends JpaRepository<EmployeeRankComment, Integer>, JpaSpecificationExecutor<EmployeeRankComment> {
}

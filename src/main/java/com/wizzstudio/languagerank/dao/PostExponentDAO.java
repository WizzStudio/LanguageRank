package com.wizzstudio.languagerank.dao;

import com.wizzstudio.languagerank.domain.CompanyPostNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostExponentDAO extends JpaRepository<CompanyPostNumber, Integer> {

//    获取某语言的岗位数量
    @Query(nativeQuery = true, value = "select o from CompanyPostNumber o where o.languageName = :languageName " )
    CompanyPostNumber findByLanguageName(@Param("languageName") String languageName);

}

package com.wizzstudio.languagerank.DAO.fixedrankDAO;

import com.wizzstudio.languagerank.domain.fixedrank.FixedRank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FixedRankDAO extends JpaRepository<FixedRank, Integer> {

    // 语言热度榜最终指数计算时需要
    FixedRank findByLanguageName(String languageName);

}

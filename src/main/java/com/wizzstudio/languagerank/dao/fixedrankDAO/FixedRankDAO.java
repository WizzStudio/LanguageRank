package com.wizzstudio.languagerank.dao.fixedrankDAO;

import com.wizzstudio.languagerank.domain.FixedRank.FixedRank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FixedRankDAO extends JpaRepository<FixedRank, Integer> {

    // 语言热度榜最终指数计算时需要
    FixedRank findByLanguageName(String languageName);

}

package com.wizzstudio.languagerank.dao;

import com.wizzstudio.languagerank.domain.FixedRank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinalRankExponentDAO extends JpaRepository<FixedRank, Integer> {

    FixedRank findByLanguageName(String languageName);

}

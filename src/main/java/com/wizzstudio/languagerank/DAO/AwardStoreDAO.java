package com.wizzstudio.languagerank.DAO;

/*
Created by Ben Wen on 2019/5/18.
*/

import com.wizzstudio.languagerank.domain.AwardStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AwardStoreDAO extends JpaRepository<AwardStore, Integer> {
    AwardStore findByAwardId(Integer awardId);
}

package com.wizzstudio.languagerank.dao;

/*
Created by Ben Wen on 2019/4/6.
*/

import com.wizzstudio.languagerank.domain.UserTranspond;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTranspondDAO extends JpaRepository<UserTranspond, Integer> {
    UserTranspond findByLanguageName(String languageName);
}

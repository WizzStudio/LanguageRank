package com.wizzstudio.languagerank.DAO.userDAO;

/*
Created by Ben Wen on 2019/4/6.
*/

import com.wizzstudio.languagerank.domain.user.UserTranspond;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTranspondDAO extends JpaRepository<UserTranspond, Integer> {
    UserTranspond findByLanguageNameAndUserId(String languageName, Integer userId);
}

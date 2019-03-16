package com.wizzstudio.languagerank.dao;

/*
Created by Ben Wen on 2019/3/16.
*/

import com.wizzstudio.languagerank.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Integer> {
    User findByOpenId(String openId);
}

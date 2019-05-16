package com.wizzstudio.languagerank.DAO.clazzDAO;

/*
Created by Ben Wen on 2019/4/30.
*/

import com.wizzstudio.languagerank.domain.clazz.UserJoinedClazz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJoinedClazzDAO extends JpaRepository<UserJoinedClazz, Integer> {
}

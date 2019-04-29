package com.wizzstudio.languagerank.dao.ClazzDAO;

/*
Created by Ben Wen on 2019/4/30.
*/

import com.wizzstudio.languagerank.domain.Clazz.UserJoinedClazz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJoinedClazzDAO extends JpaRepository<UserJoinedClazz, Integer> {
}

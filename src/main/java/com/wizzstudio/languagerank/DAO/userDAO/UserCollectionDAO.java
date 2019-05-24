package com.wizzstudio.languagerank.DAO.userDAO;

/*
Created by Ben Wen on 2019/5/20.
*/

import com.wizzstudio.languagerank.domain.user.UserCollection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCollectionDAO extends JpaRepository<UserCollection, Integer> {
    List<UserCollection> findByUserId(Integer userId);

    UserCollection findByUserIdAndClazzIdAndStudyPlanDay(Integer userId, Integer clazzId, Integer studyPlanDay);
}

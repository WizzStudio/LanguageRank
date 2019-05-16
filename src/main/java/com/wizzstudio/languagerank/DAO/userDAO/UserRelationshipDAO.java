package com.wizzstudio.languagerank.DAO.userDAO;

/*
Created by Ben Wen on 2019/4/18.
*/

import com.wizzstudio.languagerank.domain.user.UserRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRelationshipDAO extends JpaRepository<UserRelationship, Integer> {
    List<UserRelationship> findByUserOne(Integer userOne);
}

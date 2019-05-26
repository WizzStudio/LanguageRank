package com.wizzstudio.languagerank.DAO.userDAO;

/*
Created by Ben Wen on 2019/5/18.
*/

import com.wizzstudio.languagerank.domain.user.UserExchangedAward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserExchangedAwardDAO extends JpaRepository<UserExchangedAward, Integer> {
    @Query("select u.awardId from UserExchangedAward u where u.userId = :userId")
    List<Integer> findAwardIdByUserId(@Param("userId") Integer userId);
}

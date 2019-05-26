package com.wizzstudio.languagerank.DAO.clazzDAO;

/*
Created by Ben Wen on 2019/5/17.
*/

import com.wizzstudio.languagerank.domain.clazz.Worship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorshipDAO extends JpaRepository<Worship, Integer> {
    /**
     * 查询当天worshippingUser用户有没有给worshippedUser用户点过赞
     */
    List<Worship> findByWorshippingUserAndWorshippedUserOrderByWorshipTimeDesc(Integer worshippingUser, Integer worshippedUser);
}

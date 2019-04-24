package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/4/24.
*/

import com.alibaba.fastjson.JSONObject;
import com.wizzstudio.languagerank.constants.Constant;
import com.wizzstudio.languagerank.dao.employeerankDAO.EmployeeRankCommentDAO;
import com.wizzstudio.languagerank.dao.fixedrankDAO.FixedRankCommentDAO;
import com.wizzstudio.languagerank.domain.EmployeeRank.EmployeeRankComment;
import com.wizzstudio.languagerank.domain.FixedRank.FixedRankComment;
import com.wizzstudio.languagerank.dto.CommentDTO;
import com.wizzstudio.languagerank.enums.CommentDisplayModeEnum;
import com.wizzstudio.languagerank.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService, Constant {
    @Autowired
    EmployeeRankCommentDAO employeeRankCommentDAO;
    @Autowired
    FixedRankCommentDAO fixedRankCommentDAO;

    @Override
    @SuppressWarnings("serial")
    public List<EmployeeRankComment> getEmployeeRankComment(String languageName, Integer pageIndex, CommentDisplayModeEnum commentDisplayMode) {
        // 使用了JavaDoc注释就不能转换为Lambda表达式
        return employeeRankCommentDAO.findAll(new Specification<EmployeeRankComment>() {
            /**
             * @param root 代表查询的实体类
             * @param criteriaQuery 可以从中可到 root 对象, 即告知 JPA Criteria 查询要查询哪一个实体类
             * @param criteriaBuilder 用于创建 Criteria 相关对象的工厂. 当然可以从中获取到 Predicate 对象
             * @return Predicate 类型, 代表一个查询条件
             */
            @Override
            public Predicate toPredicate(Root<EmployeeRankComment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // 查询条件为root对象的languageName字段值与languageName变量相等
                return criteriaBuilder.equal(root.get("languageName"), languageName);
            }
            // 查询第pageIndex - 1页，查询Constant.PAGE_SIZE条记录，排序顺序为commentDisplayMode.getDirection()，以saveTime字段为基准排序
        }, PageRequest.of(pageIndex - 1, Constant.PAGE_SIZE, new Sort(commentDisplayMode.getDirection(), "saveTime"))).getContent();
    }

    @Override
    @SuppressWarnings("serial")
    public List<FixedRankComment> getFixedRankComment(String languageName, Integer pageIndex, CommentDisplayModeEnum commentDisplayMode) {
        return fixedRankCommentDAO.findAll((Specification<FixedRankComment>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("languageName"), languageName);
        }, PageRequest.of(pageIndex - 1, Constant.PAGE_SIZE, new Sort(commentDisplayMode.getDirection(), "saveTime"))).getContent();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEmployeeRankComment(JSONObject jsonObject) {
        EmployeeRankComment employeeRankComment = new EmployeeRankComment();
        employeeRankComment.setLanguageName(jsonObject.getString("languageName"));
        employeeRankComment.setUserId(jsonObject.getInteger("userId"));
        employeeRankComment.setComment(jsonObject.getString("comment"));
        employeeRankComment.setSaveTime(new Date());

        employeeRankCommentDAO.save(employeeRankComment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFixedRankComment(JSONObject jsonObject) {
        FixedRankComment fixedRankComment = new FixedRankComment();
        fixedRankComment.setLanguageName(jsonObject.getString("languageName"));
        fixedRankComment.setUserId(jsonObject.getInteger("userId"));
        fixedRankComment.setComment(jsonObject.getString("comment"));
        fixedRankComment.setSaveTime(new Date());

        fixedRankCommentDAO.save(fixedRankComment);
    }
}

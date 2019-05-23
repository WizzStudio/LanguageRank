package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/4/24.
*/

import com.alibaba.fastjson.JSONObject;
import com.wizzstudio.languagerank.VO.CommentVO;
import com.wizzstudio.languagerank.constants.Constant;
import com.wizzstudio.languagerank.DAO.clazzDAO.ClazzCommentDAO;
import com.wizzstudio.languagerank.DAO.userDAO.UserDAO;
import com.wizzstudio.languagerank.DAO.employeerankDAO.EmployeeRankCommentDAO;
import com.wizzstudio.languagerank.DAO.fixedrankDAO.FixedRankCommentDAO;
import com.wizzstudio.languagerank.domain.clazz.ClazzComment;
import com.wizzstudio.languagerank.domain.employeerank.EmployeeRankComment;
import com.wizzstudio.languagerank.domain.fixedrank.FixedRankComment;
import com.wizzstudio.languagerank.DTO.CommentMessageDTO;
import com.wizzstudio.languagerank.enums.CommentDisplayModeEnum;
import com.wizzstudio.languagerank.service. CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class CommentServiceImpl implements CommentService, Constant {
    @Autowired
    EmployeeRankCommentDAO employeeRankCommentDAO;
    @Autowired
    FixedRankCommentDAO fixedRankCommentDAO;
    @Autowired
    ClazzCommentDAO clazzCommentDAO;
    @Autowired
    UserDAO userDAO;

    @Override
    @SuppressWarnings("serial")
    public CommentVO getEmployeeRankComment(String languageName, Integer pageIndex, CommentDisplayModeEnum commentDisplayMode) {
        // 使用了JavaDoc注释就不能转换为Lambda表达式
        List<EmployeeRankComment> employeeRankCommentList =  employeeRankCommentDAO.findAll(new Specification<EmployeeRankComment>() {
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
        }, PageRequest.of(pageIndex - 1, Constant.COMMENT_PAGE_SIZE, new Sort(commentDisplayMode.getDirection(), "saveTime"))).getContent();

        List<CommentMessageDTO> commentMessageDTOList = new ArrayList<>();
        for (int i = 0; i < employeeRankCommentList.size(); i++) {
            EmployeeRankComment employeeRankComment = employeeRankCommentList.get(i);
            CommentMessageDTO commentMessageDTO = new CommentMessageDTO();
            commentMessageDTO.setUserId(employeeRankComment.getUser().getUserId());
            commentMessageDTO.setSaveTime(employeeRankComment.getSaveTime());
            commentMessageDTO.setComment(employeeRankComment.getComment());
            commentMessageDTO.setNickName(employeeRankComment.getUser().getNickName());
            commentMessageDTO.setAvatarUrl(employeeRankComment.getUser().getAvatarUrl());
            commentMessageDTO.setFloor((pageIndex - 1) * Constant.COMMENT_PAGE_SIZE + i + 1);

            commentMessageDTOList.add(commentMessageDTO);
        }

        return new CommentVO(
                commentMessageDTOList,
                employeeRankCommentDAO.getTheNumberOfComment(languageName),
                pageIndex,
                Constant.COMMENT_PAGE_SIZE);
    }

    @Override
    @SuppressWarnings("serial")
    public CommentVO getFixedRankComment(String languageName, Integer pageIndex, CommentDisplayModeEnum commentDisplayMode) {
        List<FixedRankComment> fixedRankCommentList =  fixedRankCommentDAO.findAll(
                (Specification<FixedRankComment>) (root, criteriaQuery, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("languageName"), languageName),
                        PageRequest.of(pageIndex - 1, Constant.COMMENT_PAGE_SIZE,
                        new Sort(commentDisplayMode.getDirection(), "saveTime")))
                .getContent();

        List<CommentMessageDTO> commentMessageDTOList = new ArrayList<>();
        for (int i = 0; i < fixedRankCommentList.size(); i++) {
            FixedRankComment fixedRankComment = fixedRankCommentList.get(i);
            CommentMessageDTO commentMessageDTO = new CommentMessageDTO();
            commentMessageDTO.setUserId(fixedRankComment.getUser().getUserId());
            commentMessageDTO.setSaveTime(fixedRankComment.getSaveTime());
            commentMessageDTO.setComment(fixedRankComment.getComment());
            commentMessageDTO.setNickName(fixedRankComment.getUser().getNickName());
            commentMessageDTO.setAvatarUrl(fixedRankComment.getUser().getAvatarUrl());
            commentMessageDTO.setFloor((pageIndex - 1) * Constant.COMMENT_PAGE_SIZE + i + 1);

            commentMessageDTOList.add(commentMessageDTO);
        }

        return new CommentVO(
                commentMessageDTOList,
                fixedRankCommentDAO.getTheNumberOfComment(languageName),
                pageIndex,
                Constant.COMMENT_PAGE_SIZE);
    }

    @Override
    public CommentVO getClazzComment(Integer clazzId, Integer pageIndex, CommentDisplayModeEnum commentDisplayMode) {
        List<ClazzComment> clazzCommentList =  clazzCommentDAO.findAll(
                (Specification<ClazzComment>) (root, criteriaQuery, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("clazzId"), clazzId),
                PageRequest.of(pageIndex - 1, Constant.COMMENT_PAGE_SIZE,
                        new Sort(commentDisplayMode.getDirection(), "saveTime")))
                .getContent();

        List<CommentMessageDTO> commentMessageDTOList = new ArrayList<>();
        for (int i = 0; i < clazzCommentList.size(); i++) {
            ClazzComment clazzComment = clazzCommentList.get(i);
            CommentMessageDTO commentMessageDTO = new CommentMessageDTO();
            commentMessageDTO.setUserId(clazzComment.getUser().getUserId());
            commentMessageDTO.setSaveTime(clazzComment.getSaveTime());
            commentMessageDTO.setComment(clazzComment.getComment());
            commentMessageDTO.setNickName(clazzComment.getUser().getNickName());
            commentMessageDTO.setAvatarUrl(clazzComment.getUser().getAvatarUrl());
            commentMessageDTO.setFloor((pageIndex - 1) * Constant.COMMENT_PAGE_SIZE + i + 1);

            commentMessageDTOList.add(commentMessageDTO);
        }

        return new CommentVO(
                commentMessageDTOList,
                clazzCommentDAO.getTheNumberOfComment(clazzId),
                pageIndex,
                Constant.COMMENT_PAGE_SIZE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEmployeeRankComment(JSONObject jsonObject) {
        EmployeeRankComment employeeRankComment = new EmployeeRankComment();
        employeeRankComment.setLanguageName(jsonObject.getString("languageName"));
        employeeRankComment.setUser(userDAO.findByUserId(jsonObject.getInteger("userId")));
        employeeRankComment.setComment(jsonObject.getString("comment"));
        employeeRankComment.setSaveTime(new Date());

        employeeRankCommentDAO.save(employeeRankComment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFixedRankComment(JSONObject jsonObject) {
        FixedRankComment fixedRankComment = new FixedRankComment();
        fixedRankComment.setLanguageName(jsonObject.getString("languageName"));
        fixedRankComment.setUser(userDAO.findByUserId(jsonObject.getInteger("userId")));
        fixedRankComment.setComment(jsonObject.getString("comment"));
        fixedRankComment.setSaveTime(new Date());

        fixedRankCommentDAO.save(fixedRankComment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateClazzComment(JSONObject jsonObject) {
        ClazzComment clazzComment = new ClazzComment();
        clazzComment.setClazzId(jsonObject.getInteger("clazzId"));
        clazzComment.setUser(userDAO.findByUserId(jsonObject.getInteger("userId")));
        clazzComment.setComment(jsonObject.getString("comment"));
        clazzComment.setSaveTime(new Date());

        clazzCommentDAO.save(clazzComment);
    }
}

package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/4/24.
*/

import com.alibaba.fastjson.JSONObject;
import com.wizzstudio.languagerank.constants.Constant;
import com.wizzstudio.languagerank.dao.ClazzDAO.ClazzCommentDAO;
import com.wizzstudio.languagerank.dao.UserDAO.UserDAO;
import com.wizzstudio.languagerank.dao.employeerankDAO.EmployeeRankCommentDAO;
import com.wizzstudio.languagerank.dao.fixedrankDAO.FixedRankCommentDAO;
import com.wizzstudio.languagerank.domain.Clazz.ClazzComment;
import com.wizzstudio.languagerank.domain.EmployeeRank.EmployeeRankComment;
import com.wizzstudio.languagerank.domain.FixedRank.FixedRankComment;
import com.wizzstudio.languagerank.dto.CommentDTO;
import com.wizzstudio.languagerank.enums.CommentDisplayModeEnum;
import com.wizzstudio.languagerank.service.CommentService;
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
    public Map<String, Object> getEmployeeRankComment(String languageName, Integer pageIndex, CommentDisplayModeEnum commentDisplayMode) {
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
        }, PageRequest.of(pageIndex - 1, Constant.PAGE_SIZE, new Sort(commentDisplayMode.getDirection(), "saveTime"))).getContent();

        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (int i = 0; i < employeeRankCommentList.size(); i++) {
            EmployeeRankComment employeeRankComment = employeeRankCommentList.get(i);
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setUserId(employeeRankComment.getUser().getUserId());
            commentDTO.setSaveTime(employeeRankComment.getSaveTime());
            commentDTO.setComment(employeeRankComment.getComment());
            commentDTO.setNickName(employeeRankComment.getUser().getNickName());
            commentDTO.setAvatarUrl(employeeRankComment.getUser().getAvatarUrl());
            commentDTO.setFloor((pageIndex - 1) * Constant.PAGE_SIZE + i + 1);

            commentDTOList.add(commentDTO);
        }

        // 是否是最后一页
        Boolean isAllComment = (employeeRankCommentDAO.getTheNumberOfComment(languageName)/Constant.PAGE_SIZE == pageIndex-1);

        Map<String, Object> map = new HashMap<>();
        map.put("commentList", commentDTOList);
        map.put("isAllComment", isAllComment);
        return map;
    }

    @Override
    @SuppressWarnings("serial")
    public Map<String, Object> getFixedRankComment(String languageName, Integer pageIndex, CommentDisplayModeEnum commentDisplayMode) {
        List<FixedRankComment> fixedRankCommentList =  fixedRankCommentDAO.findAll(
                (Specification<FixedRankComment>) (root, criteriaQuery, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("languageName"), languageName),
                        PageRequest.of(pageIndex - 1, Constant.PAGE_SIZE,
                        new Sort(commentDisplayMode.getDirection(), "saveTime")))
                .getContent();

        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (int i = 0; i < fixedRankCommentList.size(); i++) {
            FixedRankComment fixedRankComment = fixedRankCommentList.get(i);
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setUserId(fixedRankComment.getUser().getUserId());
            commentDTO.setSaveTime(fixedRankComment.getSaveTime());
            commentDTO.setComment(fixedRankComment.getComment());
            commentDTO.setNickName(fixedRankComment.getUser().getNickName());
            commentDTO.setAvatarUrl(fixedRankComment.getUser().getAvatarUrl());
            commentDTO.setFloor((pageIndex - 1) * Constant.PAGE_SIZE + i + 1);

            commentDTOList.add(commentDTO);
        }

        Boolean isAllComment = (fixedRankCommentDAO.getTheNumberOfComment(languageName)/Constant.PAGE_SIZE == pageIndex-1);

        Map<String, Object> map = new HashMap<>();
        map.put("commentList", commentDTOList);
        map.put("isAllComment", isAllComment);
        return map;
    }

    @Override
    public Map<String, Object> getClazzComment(Integer clazzId, Integer pageIndex, CommentDisplayModeEnum commentDisplayMode) {
        List<ClazzComment> clazzCommentList =  clazzCommentDAO.findAll(
                (Specification<ClazzComment>) (root, criteriaQuery, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("clazzId"), clazzId),
                PageRequest.of(pageIndex - 1, Constant.PAGE_SIZE,
                        new Sort(commentDisplayMode.getDirection(), "saveTime")))
                .getContent();

        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (int i = 0; i < clazzCommentList.size(); i++) {
            ClazzComment clazzComment = clazzCommentList.get(i);
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setUserId(clazzComment.getUser().getUserId());
            commentDTO.setSaveTime(clazzComment.getSaveTime());
            commentDTO.setComment(clazzComment.getComment());
            commentDTO.setNickName(clazzComment.getUser().getNickName());
            commentDTO.setAvatarUrl(clazzComment.getUser().getAvatarUrl());
            commentDTO.setFloor((pageIndex - 1) * Constant.PAGE_SIZE + i + 1);

            commentDTOList.add(commentDTO);
        }

        Boolean isAllComment = (clazzCommentDAO.getTheNumberOfComment(clazzId)/Constant.PAGE_SIZE == pageIndex-1);

        Map<String, Object> map = new HashMap<>();
        map.put("commentList", commentDTOList);
        map.put("isAllComment", isAllComment);
        return map;
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

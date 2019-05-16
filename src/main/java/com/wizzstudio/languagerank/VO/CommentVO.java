package com.wizzstudio.languagerank.VO;

/*
Created by Ben Wen on 2019/5/16.
*/

import com.wizzstudio.languagerank.DTO.CommentMessageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 评论相关接口的返回参数类型
 */
@Data
@AllArgsConstructor
public class CommentVO {
    /**
     * 评论内容列表
     */
    private List<CommentMessageDTO> commentList;

    /**
     * 评论总数
     */
    private Integer total;

    /**
     * 评论当前页数
     */
    private Integer pageIndex;

    /**
     * 一页最多显示的评论数
     */
    private Integer pageSize;
}

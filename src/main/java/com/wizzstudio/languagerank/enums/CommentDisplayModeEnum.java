package com.wizzstudio.languagerank.enums;

/*
Created by Ben Wen on 2019/4/24.
*/

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
@AllArgsConstructor
public enum CommentDisplayModeEnum {
    // 新评论优先显示(按时间降序查询)
    NEW_COMMENT_PRIORITIZED(Sort.Direction.DESC),
    // 老评论优先显示(按时间升序查询)
    OLD_COMMENT_PRIORITIZED(Sort.Direction.ASC);

    private Sort.Direction direction;
}

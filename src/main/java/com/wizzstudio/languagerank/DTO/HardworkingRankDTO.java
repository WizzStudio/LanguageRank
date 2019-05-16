package com.wizzstudio.languagerank.DTO;

/*
Created by Ben Wen on 2019/5/15.
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 勤奋排行榜的传输类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HardworkingRankDTO {
    private Integer userId;

    private String nickName;

    private String avatarUrl;

    private Integer todayScore;
}

package com.wizzstudio.languagerank.service;

import com.wizzstudio.languagerank.dto.FinalRankDTO;

import java.util.List;

public interface FixedRankService {

    /**
     * 获取语言热度榜四个字段
     * @return
     */
    List<FinalRankDTO> getFinalRank();

}

package com.wizzstudio.languagerank.service;

import com.wizzstudio.languagerank.dto.EmployeeRankDTO;

import java.util.List;

public interface EmployeeRankService {

    /**
     * 获取雇主需求榜的四个字段
     * @return
     */
    List<EmployeeRankDTO> getEmployeeRank();

}

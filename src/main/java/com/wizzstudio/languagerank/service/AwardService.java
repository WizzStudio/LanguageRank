package com.wizzstudio.languagerank.service;

/*
Created by Ben Wen on 2019/4/9.
*/

import com.wizzstudio.languagerank.domain.Award;
import com.wizzstudio.languagerank.domain.StudyPlan;
import com.wizzstudio.languagerank.enums.StudyPlanDayEnum;

public interface AwardService {
    /**
     * 查询该语言的奖励
     * @param languageName 语言名
     * @return 该语言的奖励
     */
    Award findAwardByLanguageName(String languageName);
}

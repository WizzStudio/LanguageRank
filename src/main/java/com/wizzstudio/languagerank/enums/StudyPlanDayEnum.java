package com.wizzstudio.languagerank.enums;

/*
Created by Ben Wen on 2019/3/10.
*/

import lombok.AllArgsConstructor;
import lombok.Getter;

// 七日学习计划的安排
@Getter
@AllArgsConstructor
public enum StudyPlanDayEnum {
    FIRST_DAY(1),
    SECOND_DAY(2),
    THIRD_DAY(3),
    FOURTH_DAY(4),
    FIFTH_DAY(5),
    SIXTH_DAY(6),
    SEVEN_DAY(7),
    ACCOMPLISHED(8);

    private Integer studyPlanDay;

    // 根据Integer类型的参数值获得String类型的属性值
    public static StudyPlanDayEnum getStudyPlanDayByInteger(Integer studyPlanDay) {
        for (StudyPlanDayEnum studyPlanDayEnum : StudyPlanDayEnum.values()) {
            if (studyPlanDay.equals(studyPlanDayEnum.getStudyPlanDay())) {
                return studyPlanDayEnum;
            }
        }
        return null;
    }
}

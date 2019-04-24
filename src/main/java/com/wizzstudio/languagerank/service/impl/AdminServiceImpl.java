package com.wizzstudio.languagerank.service.impl;

import com.wizzstudio.languagerank.domain.EmployeeRank.EmployeeRank;
import com.wizzstudio.languagerank.domain.FixedRank.FixedFinalExponent;
import com.wizzstudio.languagerank.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    // 有问题，还没处理
    private FixedFinalExponent fixedFinalExponent;
    private EmployeeRank employeeRank;

    // 将持久化对象从数据库中拿出再set是可以的
    @Override
    public void updateFixedExponent(String languageName, Double FArtificialExponent) {
        fixedFinalExponent.setFArtificialExponent(FArtificialExponent);
    }

    @Override
    public void updateEmployeeExponent(String languageName, Double EArtificialExponent) {
        employeeRank.setEArtificialExponent(EArtificialExponent);
    }

    @Override
    public String updateStudyPlan() {
        return null;
    }

}

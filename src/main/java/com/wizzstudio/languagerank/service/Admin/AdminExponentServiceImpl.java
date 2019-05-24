package com.wizzstudio.languagerank.service.Admin;

import com.wizzstudio.languagerank.domain.employeerank.EmployeeRank;
import com.wizzstudio.languagerank.domain.fixedrank.FixedFinalExponent;
import com.wizzstudio.languagerank.service.Admin.AdminExponentService;
import org.springframework.stereotype.Service;

@Service
public class AdminExponentServiceImpl implements AdminExponentService {

    // 有问题，还没处理
    private FixedFinalExponent fixedFinalExponent;
    private EmployeeRank employeeRank;

    // 将持久化对象从数据库中拿出再set是可以的
    @Override
    public void updateFixedExponent(String languageName, Double Fartificialexponent) {
        fixedFinalExponent.setFArtificialExponent(Fartificialexponent);
    }

    @Override
    public void updateEmployeeExponent(String languageName, Double Eartificialexponent) {
        employeeRank.setEArtificialExponent(Eartificialexponent);
    }

    @Override
    public String updateStudyPlan() {
        return null;
    }

}

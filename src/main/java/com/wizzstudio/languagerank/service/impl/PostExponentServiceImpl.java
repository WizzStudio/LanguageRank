package com.wizzstudio.languagerank.service.impl;

import com.wizzstudio.languagerank.dao.EmployeeRankDAO;
import com.wizzstudio.languagerank.dao.PostExponentDAO;
import com.wizzstudio.languagerank.domain.CompanyPostNumber;
import com.wizzstudio.languagerank.domain.EmployeeRank;
import com.wizzstudio.languagerank.service.PostExponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostExponentServiceImpl implements PostExponentService {


    @Autowired
    PostExponentDAO postExponentDAO;
    @Autowired
    EmployeeRankDAO employeeRankDAO;

    double a = 0.0;
    Map<String, Integer> map = new HashMap<>();

    @Override
    public void findOrdPostNumber(List<String> languageName) {

        List<EmployeeRank> employeeRanks = employeeRankDAO.languageEmployeeRank();
        for (EmployeeRank employeeRank : employeeRanks){
            String languageNameRank = employeeRank.getLanguageName();

            CompanyPostNumber companyPostNumber = postExponentDAO.findByLanguageName(languageNameRank);
            Integer number = companyPostNumber.getPostNumber();
            map.put(languageNameRank,number);
            a = number + a;
        }
        a = a/10;
    }

    @Override
    public Double findLanguagePostNumber(String languageName) {

        return 30*map.get(languageName) / a;
    }
}

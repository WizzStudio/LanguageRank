package com.wizzstudio.languagerank.service.impl;

import com.wizzstudio.languagerank.dao.employeerankDAO.CompanyPostDAO;
import com.wizzstudio.languagerank.dao.employeerankDAO.EmployeeRankDAO;
import com.wizzstudio.languagerank.domain.CompanyPost;
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
    CompanyPostDAO companyPostDAO;
    @Autowired
    EmployeeRankDAO employeeRankDAO;

    int number = 0;
    int size = 0;
    double a = 0.0;
    Map<String, Integer> map = new HashMap<>();

    @Override
    public void findOrdPostNumber(List<String> languageName) {

        List<EmployeeRank> employeeRanks = employeeRankDAO.languageEmployeeRank();

//        取排名前十的语言名
        for (EmployeeRank employeeRank : employeeRanks){
            String languageNameRank = employeeRank.getLanguageName();
            size = companyPostDAO.findCompanyPostByLanguageName(languageNameRank).size();

//            取该语言所有公司的岗位数之和，求其平均为a
            List<CompanyPost> companyPosts = companyPostDAO.findCompanyPostByLanguageName(languageNameRank);
            for (CompanyPost companyPost : companyPosts){
                number = companyPost.getCompanyPostNumber();
                map.put(languageNameRank,number);
                a = number + a;
            }
        }
        a = a/size;
    }

    @Override
    public Double findLanguagePostNumber(String languageName) {

        return 30*map.get(languageName) / a;
    }
}

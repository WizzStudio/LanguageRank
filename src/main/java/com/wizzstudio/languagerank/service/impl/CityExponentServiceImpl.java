package com.wizzstudio.languagerank.service.impl;

import com.wizzstudio.languagerank.dao.CityExponentDAO;
import com.wizzstudio.languagerank.service.CityExponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityExponentServiceImpl implements CityExponentService {

    @Autowired
    CityExponentDAO cityExponentDAO;

    @Override
    public Double findCityExponent(String languageName) {

        int topSum = 0;
        int allSum = cityExponentDAO.findLanguageAllSum(languageName);
        for (int i =0; i < 5; i++)
            topSum = topSum + cityExponentDAO.findLanguageTopSum(languageName).get(i).getPostNumber();

        double rate = topSum / allSum;
        return 15*rate + 5*(1-rate);
    }
}

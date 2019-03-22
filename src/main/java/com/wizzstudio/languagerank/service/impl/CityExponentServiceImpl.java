package com.wizzstudio.languagerank.service.impl;

import com.wizzstudio.languagerank.dao.employeerankDAO.LanguageCityDAO;
import com.wizzstudio.languagerank.domain.LanguageCity;
import com.wizzstudio.languagerank.service.CityExponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityExponentServiceImpl implements CityExponentService {

    @Autowired
    private LanguageCityDAO languageCityDAO;

    @Override
    public Double findCityExponent(String languageName) {

        int topSum = 0;
        int allSum = languageCityDAO.findLanguageAllSum(languageName);
        for (int i =0; i < 5; i++)
            topSum = topSum + languageCityDAO.findLanguageCityTopFiveByLanguageName(languageName).get(i).getCityPostNumber();

        double rate = topSum / allSum;
        return 15*rate + 5*(1-rate);
    }
}

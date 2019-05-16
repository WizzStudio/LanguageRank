package com.wizzstudio.languagerank.service.impl;

import com.wizzstudio.languagerank.DAO.LanguageDAO;
import com.wizzstudio.languagerank.DAO.fixedrankDAO.FixedRankDAO;
import com.wizzstudio.languagerank.DAO.fixedrankDAO.FixedFinalExponentDAO;
import com.wizzstudio.languagerank.domain.fixedrank.FixedFinalExponent;
import com.wizzstudio.languagerank.domain.Language;
import com.wizzstudio.languagerank.VO.FinalRankVO;
import com.wizzstudio.languagerank.service.FixedRankService;
import com.wizzstudio.languagerank.service.LanguageTendService;
import com.wizzstudio.languagerank.util.DateUtil;
import com.wizzstudio.languagerank.util.DoubleUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class FixedRankServiceImpl implements FixedRankService {

    @Autowired
    FixedRankDAO fixedRankDAO;
    @Autowired
    FixedFinalExponentDAO fixedRankLanguageNameDAO;
    @Autowired
    LanguageTendService languageTendService;
    @Autowired
    FixedFinalExponentDAO fixedFinalExponentDAO;
    @Autowired
    LanguageDAO languageDAO;

    // TIOBE指数*100*6*0.4+IEEE指数*0.5+百度指数/100*0.1
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveExponent() {
        List<Language> languageList = languageDAO.findAll();
        // temporary开头为临时变量
        for (Language language : languageList) {
            String temporaryLanguageName = language.getLanguageName();

            Double exponent = fixedRankDAO.findByLanguageName(temporaryLanguageName).getTIOBEExponent()*240
                    + fixedRankDAO.findByLanguageName(temporaryLanguageName).getIEEEExponent()*0.5
                    + fixedRankDAO.findByLanguageName(temporaryLanguageName).getBaiduExponent()*0.001;
            if (exponent > 100) {
                exponent = 100.0;
            }

            FixedFinalExponent fixedFinalExponent = new FixedFinalExponent();
            fixedFinalExponent.setLanguageName(temporaryLanguageName);
            fixedFinalExponent.setFixedFinalExponent(DoubleUtil.getDecimalFormat(exponent));
            try {
                fixedFinalExponent.setUpdateTime(DateUtil.getNextDate(new Date()));
            } catch (ParseException e) {
                log.error("时间转换异常");
                e.printStackTrace();
            }

            fixedRankLanguageNameDAO.save(fixedFinalExponent);
        }

    }


    private static List<FinalRankVO> list = new ArrayList<>();

    @Override
    public List<FinalRankVO> getFinalRank() {
        // 测试用
//        saveExponent();
        if (!list.isEmpty()) {
            return list;
        }

        List<FinalRankVO> finalRankVOList = new ArrayList<>();

        List<FixedFinalExponent> fixedFinalExponents = fixedFinalExponentDAO.languageFixedRank();
        for (FixedFinalExponent fixedFinalExponent : fixedFinalExponents){

            FinalRankVO finalRankVO = new FinalRankVO();
//            获取前十的语言名称
            String languageName = fixedFinalExponent.getLanguageName();

//            语言热度榜四个字段
            finalRankVO.setLanguageName(languageName);
            finalRankVO.setLanguageSymbol(languageDAO.findByLanguageName(languageName).getLanguageSymbol());
            finalRankVO.setFixedFinalExponent(fixedFinalExponentDAO.findTwoByLanguageName(languageName).get(0).getFixedFinalExponent());
            finalRankVO.setLanguageTend(languageTendService.findFixedLanguageTendNumber(languageName));

            finalRankVOList.add(finalRankVO);
        }

        list = finalRankVOList;
        return finalRankVOList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetList() {
        list = new ArrayList<>();
    }
}

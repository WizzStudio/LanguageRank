package com.wizzstudio.languagerank.service.impl;

import com.wizzstudio.languagerank.dao.LanguageDAO;
import com.wizzstudio.languagerank.dao.fixedrankDAO.FixedRankDAO;
import com.wizzstudio.languagerank.dao.fixedrankDAO.FixedFinalExponentDAO;
import com.wizzstudio.languagerank.domain.FixedFinalExponent;
import com.wizzstudio.languagerank.domain.FixedRank;
import com.wizzstudio.languagerank.domain.Language;
import com.wizzstudio.languagerank.dto.EmployeeRankDTO;
import com.wizzstudio.languagerank.dto.FinalRankDTO;
import com.wizzstudio.languagerank.service.FixedRankService;
import com.wizzstudio.languagerank.service.LanguageTendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void saveExponent(){
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

            // 设置小数点后位数
            DecimalFormat df = new DecimalFormat("#.#");
            exponent = Double.parseDouble(df.format(exponent));

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            FixedFinalExponent fixedFinalExponent = new FixedFinalExponent();
            fixedFinalExponent.setLanguageName(temporaryLanguageName);
            fixedFinalExponent.setFixedFinalExponent(exponent);
            try {
                fixedFinalExponent.setUpdateTime(dateFormat.parse(dateFormat.format(new Date())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
//            fixedFinalExponent.setUpdateTime(new Date());

            fixedRankLanguageNameDAO.save(fixedFinalExponent);
        }

    }


    private static List<FinalRankDTO> list = new ArrayList<>();

    @Override
    public List<FinalRankDTO> getFinalRank() {
        // 测试用
//        saveExponent();
        if (!list.isEmpty()) {
            return list;
        }

        List<FinalRankDTO> finalRankDTOList = new ArrayList<>();

        List<FixedFinalExponent> fixedFinalExponents = fixedFinalExponentDAO.languageFixedRank();
        for (FixedFinalExponent fixedFinalExponent : fixedFinalExponents){

            FinalRankDTO finalRankDTO = new FinalRankDTO();
//            获取前十的语言名称
            String languageName = fixedFinalExponent.getLanguageName();

//            语言热度榜四个字段
            finalRankDTO.setLanguageName(languageName);
            finalRankDTO.setLanguageSymbol(languageDAO.findByLanguageName(languageName).getLanguageSymbol());
            finalRankDTO.setFixedFinalExponent(fixedFinalExponentDAO.findTwoByLanguageName(languageName).get(0).getFixedFinalExponent());
            finalRankDTO.setLanguageTend(languageTendService.findFixedLanguageTendNumber(languageName));

            finalRankDTOList.add(finalRankDTO);
        }

        list = finalRankDTOList;
        return finalRankDTOList;
    }

    @Override
    @Scheduled(cron = "0 1 0 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void resetList() {
        list = new ArrayList<>();
    }
}

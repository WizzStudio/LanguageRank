package com.wizzstudio.languagerank.controller;

import com.wizzstudio.languagerank.dto.LanguageHomePageDTO;
import com.wizzstudio.languagerank.dto.MoreLanguageInformationDTO;
import com.wizzstudio.languagerank.service.FixedRankService;
import com.wizzstudio.languagerank.service.LanguageHomePageService;
import com.wizzstudio.languagerank.service.LanguageMoreInformationService;
import com.wizzstudio.languagerank.service.LanguageTendService;
import com.wizzstudio.languagerank.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class FixedRankController {

    @Autowired
    FixedRankService fixedRankService;
    @Autowired
    LanguageTendService languageTendService;
    @Autowired
    LanguageHomePageService languageHomePageService;
    @Autowired
    LanguageMoreInformationService languageMoreInformationService;

    @GetMapping("/languagerank")
    public ResponseEntity getFixedRank(){
        return ResultUtil.success(fixedRankService.getFinalRank());
    }

    @GetMapping("/languagerank/{languageName}")
    public ResponseEntity getLanguageHomePage(@PathVariable("languageName")String languageName) {
        LanguageHomePageDTO languageHomePageDTO = languageHomePageService.getLanguageHomePage(languageName);
        if (languageHomePageDTO != null) {
            log.info("获取" + languageName + "主页成功");
            return ResultUtil.success("获取" + languageName + "主页成功", languageHomePageDTO);
        } else {
            log.error("获取" + languageName + "主页失败");
            return ResultUtil.error("获取" + languageName + "主页失败");
        }
    }

    @GetMapping("/languagerank/{languageName}/more")
    public ResponseEntity getMoreLanguageInformation(@PathVariable("languageName")String languageName){
        MoreLanguageInformationDTO moreLanguageInformation = languageMoreInformationService.getMoreLanguageInformation(languageName);
        if (moreLanguageInformation != null) {
            log.info("获取" + languageName + "介绍页成功");
            return ResultUtil.success("获取" + languageName + "介绍页成功", moreLanguageInformation);
        } else {
            log.error("获取" + languageName + "介绍页失败");
            return ResultUtil.error("获取" + languageName + "介绍页失败");
        }
    }
}

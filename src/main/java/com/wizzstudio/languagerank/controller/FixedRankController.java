package com.wizzstudio.languagerank.controller;


import com.wizzstudio.languagerank.domain.FixedRank;
import com.wizzstudio.languagerank.dto.LanguageHomePageDTO;
import com.wizzstudio.languagerank.service.FinalRankService;
import com.wizzstudio.languagerank.service.LanguageHomePageService;
import com.wizzstudio.languagerank.service.LanguageTendService;
import com.wizzstudio.languagerank.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class FixedRankController {

    @Autowired
    FinalRankService finalRankService;
    @Autowired
    LanguageTendService languageTendService;
    @Autowired
    LanguageHomePageService languageHomePageService;

    @PostMapping("/languageRank")
    public ResponseEntity getFixedRank(){

//        List<FixedRank> fixedRanks =

        return null;
    }

    @GetMapping("/languagerank/{languageName}")
    public ResponseEntity getLanguageHomePage(@PathVariable("languageName")String languageName) {
        LanguageHomePageDTO languageHomePageDTO = languageHomePageService.getLanguageHomePage(languageName);
        if (languageHomePageDTO != null) {
            log.info("获取" + languageName + "主页成功");
            return ResultUtil.success("获取" + languageName + "主页成功", languageHomePageDTO);
        } else {
            log.info("获取" + languageName + "主页失败");
            return ResultUtil.error("获取" + languageName + "主页失败");
        }
    }
}

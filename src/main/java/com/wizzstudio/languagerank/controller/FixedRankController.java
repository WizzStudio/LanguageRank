package com.wizzstudio.languagerank.controller;


import com.wizzstudio.languagerank.domain.FixedRank;
import com.wizzstudio.languagerank.service.FinalRankService;
import com.wizzstudio.languagerank.service.LanguageTendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

    @PostMapping("/languageRank")
    public ResponseEntity getFixedRank(){

//        List<FixedRank> fixedRanks =

        return null;
    }

}

package com.wizzstudio.languagerank.controller;


import com.wizzstudio.languagerank.dao.employeerankDAO.EmployeeRankLanguageNameDAO;
import com.wizzstudio.languagerank.dto.FinalRankDTO;
import com.wizzstudio.languagerank.service.FinalRankService;
import com.wizzstudio.languagerank.service.LanguageTendService;
import com.wizzstudio.languagerank.service.impl.FixedRankServiceImpl;
import com.wizzstudio.languagerank.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class FixedRankController {

    @Autowired
    FixedRankServiceImpl fixedRankService;

    @PostMapping("/languageRank")
    public ResponseEntity getFixedRank(){

//        List<FinalRankDTO> finalRankDTOS = new ArrayList<>();
        return ResultUtil.success(fixedRankService.getFinalRank());
    }

    @GetMapping("/languagerank/{languageName}")
    public ResponseEntity getLanguageHomePage(@PathVariable("languageName")String languageName) {
        return null;
    }
}

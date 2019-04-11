package com.wizzstudio.languagerank.service.impl;

import com.wizzstudio.languagerank.service.ShareDimensionCodeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShareDimensionCodeServiceImpl implements ShareDimensionCodeService {

//    语言热度帮
    private static final String pageIndex = "Http://pgrk.wizzstudio.com/";
//    公司需求页
    private static final String detailDemandHome = "Http://pgrk.wizzstudio.com/";
//    语言主页
    private static final String detailLangHome = "Http://pgrk.wizzstudio.com/";
//    语言详情
    private static final String detailLangDetail = "Http://pgrk.wizzstudio.com/";


    @Override
    public List<String> getDimensionCode() {

        List<String> list = new ArrayList<>();
        list.add(pageIndex);
        list.add(detailDemandHome);
        list.add(detailLangHome);
        list.add(detailLangDetail);

        return list;
    }
}

package com.wizzstudio.languagerank.service.impl;

import com.wizzstudio.languagerank.service.ShareDimensionCodeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShareDimensionCodeServiceImpl implements ShareDimensionCodeService {

//    语言热度帮
    private static final String PAGE_INDEX = "Http://pgrk.wizzstudio.com/";
//    公司需求页
    private static final String DETAIL_DEMAND_HOME = "Http://pgrk.wizzstudio.com/";
//    语言主页
    private static final String DETAIL_LANG_HOME = "Http://pgrk.wizzstudio.com/";
//    语言详情
    private static final String DETAIL_LANG_DETAIL = "Http://pgrk.wizzstudio.com/";


    @Override
    public List<String> getDimensionCode() {

        List<String> list = new ArrayList<>();
        list.add(PAGE_INDEX);
        list.add(DETAIL_DEMAND_HOME);
        list.add(DETAIL_LANG_HOME);
        list.add(DETAIL_LANG_DETAIL);

        return list;
    }
}

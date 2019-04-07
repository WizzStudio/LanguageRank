package com.wizzstudio.languagerank.service.impl;

import com.wizzstudio.languagerank.service.ShareDimensionCodeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShareDimensionCodeServiceImpl implements ShareDimensionCodeService {

    private static final String page1 = "Http://pgrk.wizzstudio.com/";
    private static final String page2 = "Http://pgrk.wizzstudio.com/";
    private static final String page3 = "Http://pgrk.wizzstudio.com/";

    @Override
    public List<String> getDimensionCode() {

        List<String> list = new ArrayList<>();
        list.add(page1);
        list.add(page2);
        list.add(page3);

        return list;
    }
}

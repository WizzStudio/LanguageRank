package com.wizzstudio.languagerank.service.impl;

import com.wizzstudio.languagerank.service.ShareDimensionCodeService;

import java.util.ArrayList;
import java.util.List;

public class ShareDimensionCodeServiceImpl implements ShareDimensionCodeService {


    public static String page1 = "Http://www.wizz.stdio.com/";
    public static String page2 = "Http://www.wizz.stdio.com/";
    public static String page3 = "Http://www.wizz.stdio.com/";

    @Override
    public List<String> getDimensionCode() {

        List<String> list = new ArrayList<>();
        list.add(page1);
        list.add(page2);
        list.add(page3);

        return list;
    }
}

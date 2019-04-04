package com.wizzstudio.languagerank.enums;

/*
Created by Ben Wen on 2019/4/4.
*/

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LanguageApplicationFieldsEnum {
    // 大数据
    BIG_DATA("http://qiniu.ben286.top/blog/20190404/usbYsoBWD0YM.png?imageslim", "大数据"),
    // 云计算
    CLOUD_COMPUTING("http://qiniu.ben286.top/blog/20190404/rX8K4X6FRbnw.png?imageslim", "云计算"),
    // 区块链
    BLOCK_CHAIN("http://qiniu.ben286.top/blog/20190404/YMbcuHgff3oV.png?imageslim", "区块链"),
    // 人工智能
    ARTIFICIAL_INTELLIGENCE("http://qiniu.ben286.top/blog/20190404/XUD7MEHOmluv.png?imageslim", "人工智能"),
    // 物联网
    LOT("http://qiniu.ben286.top/blog/20190404/St2xUpcT0bKi.png?imageslim", "物联网");

    private String imageURL;
    private String imageContent;
}

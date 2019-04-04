package com.wizzstudio.languagerank.enums;

/*
Created by Ben Wen on 2019/4/4.
*/

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LanguageUseEnum {
    // 桌面应用开发
    DESKTOP_DEVELOPMENT("http://qiniu.ben286.top/blog/20190404/ikbCrRQg6q0c.png?imageslim", "桌面应用开发"),
    // 移动应用开发
    MOBILE_DEVELOPMENT("http://qiniu.ben286.top/blog/20190404/YcWW7qwv9WCJ.png?imageslim", "移动应用开发"),
    // 嵌入式开发
    EMBEDDED_DEVELOPMENT("http://qiniu.ben286.top/blog/20190404/IlyPiMybqMSc.png?imageslim", "嵌入式开发"),
    // Web开发
    WEB_DEVELOPMENT("http://qiniu.ben286.top/blog/20190404/1rPBsoLuyKoh.png?imageslim", "Web开发");

    private String imageURL;
    private String imageContent;
}

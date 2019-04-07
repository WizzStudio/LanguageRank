package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/4/5.
*/

import com.wizzstudio.languagerank.dao.CompanyDAO;
import com.wizzstudio.languagerank.dao.LanguageDAO;
import com.wizzstudio.languagerank.domain.Company;
import com.wizzstudio.languagerank.dto.MoreLanguageInformationDTO;
import com.wizzstudio.languagerank.enums.LanguageApplicationFieldsEnum;
import com.wizzstudio.languagerank.enums.LanguageUseEnum;
import com.wizzstudio.languagerank.service.LanguageMoreInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LanguageMoreInformationServiceImpl implements LanguageMoreInformationService {
    @Autowired
    private LanguageDAO languageDAO;
    @Autowired
    private CompanyDAO companyDAO;

    private static Map<String, MoreLanguageInformationDTO> map = new HashMap<>();

//    // 需不需要更新是个问题
//    @Override
//    @Scheduled(cron = "0 0 0 * * 1")
//    @Transactional(rollbackFor = Exception.class)
//    public void resetMap() {
//        map = new HashMap<>();
//    }

    @Override
    public MoreLanguageInformationDTO getMoreLanguageInformation(String languageName) {
        if (map.containsKey(languageName)) {
            return map.get(languageName);
        }

        Map<String, String> languageApplicationFields = new HashMap<>();
        Map<String, String> languageUse = new HashMap<>();
        List<Company> companyList = new ArrayList<>();
        List<String> languageAdvantage = new ArrayList<>();
        List<String> languageDisadvantage = new ArrayList<>();

        MoreLanguageInformationDTO moreLanguageInformation = new MoreLanguageInformationDTO();
        moreLanguageInformation.setLanguage(languageDAO.findByLanguageName(languageName));
        switch (languageName) {
            case "Java":
                languageApplicationFields.put(LanguageApplicationFieldsEnum.BIG_DATA.getImageContent(), LanguageApplicationFieldsEnum.BIG_DATA.getImageURL());
                languageApplicationFields.put(LanguageApplicationFieldsEnum.CLOUD_COMPUTING.getImageContent(), LanguageApplicationFieldsEnum.CLOUD_COMPUTING.getImageURL());
                languageApplicationFields.put(LanguageApplicationFieldsEnum.LOT.getImageContent(), LanguageApplicationFieldsEnum.LOT.getImageURL());
                languageApplicationFields.put(LanguageApplicationFieldsEnum.ARTIFICIAL_INTELLIGENCE.getImageContent(), LanguageApplicationFieldsEnum.ARTIFICIAL_INTELLIGENCE.getImageURL());
                languageApplicationFields.put(LanguageApplicationFieldsEnum.BLOCK_CHAIN.getImageContent(), LanguageApplicationFieldsEnum.BLOCK_CHAIN.getImageURL());

                languageUse.put(LanguageUseEnum.WEB_DEVELOPMENT.getImageContent(), LanguageUseEnum.WEB_DEVELOPMENT.getImageURL());
                languageUse.put(LanguageUseEnum.MOBILE_DEVELOPMENT.getImageContent(), LanguageUseEnum.MOBILE_DEVELOPMENT.getImageURL());
                languageUse.put(LanguageUseEnum.DESKTOP_DEVELOPMENT.getImageContent(), LanguageUseEnum.DESKTOP_DEVELOPMENT.getImageURL());

                companyList.add(companyDAO.findByCompanyName("Amazon"));
                companyList.add(companyDAO.findByCompanyName("Intel"));
                companyList.add(companyDAO.findByCompanyName("阿里巴巴"));

                languageAdvantage.add("Java开发者被大量需求");
                languageAdvantage.add("这个平台在继续增加新功能");

                languageDisadvantage.add("Java比C++使用更多的内存");
                languageDisadvantage.add("用Java写安卓应用启动慢");

                break;
            case "Python":
                languageApplicationFields.put(LanguageApplicationFieldsEnum.BIG_DATA.getImageContent(), LanguageApplicationFieldsEnum.BIG_DATA.getImageURL());
                languageApplicationFields.put(LanguageApplicationFieldsEnum.CLOUD_COMPUTING.getImageContent(), LanguageApplicationFieldsEnum.CLOUD_COMPUTING.getImageURL());
                languageApplicationFields.put(LanguageApplicationFieldsEnum.LOT.getImageContent(), LanguageApplicationFieldsEnum.LOT.getImageURL());
                languageApplicationFields.put(LanguageApplicationFieldsEnum.ARTIFICIAL_INTELLIGENCE.getImageContent(), LanguageApplicationFieldsEnum.ARTIFICIAL_INTELLIGENCE.getImageURL());
                languageApplicationFields.put(LanguageApplicationFieldsEnum.BLOCK_CHAIN.getImageContent(), LanguageApplicationFieldsEnum.BLOCK_CHAIN.getImageURL());

                languageUse.put(LanguageUseEnum.WEB_DEVELOPMENT.getImageContent(), LanguageUseEnum.WEB_DEVELOPMENT.getImageURL());
                languageUse.put(LanguageUseEnum.DESKTOP_DEVELOPMENT.getImageContent(), LanguageUseEnum.DESKTOP_DEVELOPMENT.getImageURL());
                languageUse.put(LanguageUseEnum.EMBEDDED_DEVELOPMENT.getImageContent(), LanguageUseEnum.EMBEDDED_DEVELOPMENT.getImageURL());

                companyList.add(companyDAO.findByCompanyName("Amazon"));
                companyList.add(companyDAO.findByCompanyName("Dell"));
                companyList.add(companyDAO.findByCompanyName("百度"));

                languageAdvantage.add("被认为总容易学习的编程语言");
                languageAdvantage.add("大量让人惊讶的库和函数");

                languageDisadvantage.add("比编译类语言慢很多");
                languageDisadvantage.add("在移动计算方面很弱");

                break;
            case "C":
                languageApplicationFields.put(LanguageApplicationFieldsEnum.LOT.getImageContent(), LanguageApplicationFieldsEnum.LOT.getImageURL());

                languageUse.put(LanguageUseEnum.MOBILE_DEVELOPMENT.getImageContent(), LanguageUseEnum.MOBILE_DEVELOPMENT.getImageURL());
                languageUse.put(LanguageUseEnum.DESKTOP_DEVELOPMENT.getImageContent(), LanguageUseEnum.DESKTOP_DEVELOPMENT.getImageURL());
                languageUse.put(LanguageUseEnum.EMBEDDED_DEVELOPMENT.getImageContent(), LanguageUseEnum.EMBEDDED_DEVELOPMENT.getImageURL());

                companyList.add(companyDAO.findByCompanyName("Amazon"));
                companyList.add(companyDAO.findByCompanyName("腾讯"));
                companyList.add(companyDAO.findByCompanyName("Intel"));

                languageAdvantage.add("C是高度可移动的，可以在平台运行");
                languageAdvantage.add("C很小，C完全基于变量、宏命令");

                languageDisadvantage.add("C没有运行时检查机制");
                languageDisadvantage.add("不支持面向对象编程");

                break;
            case "C++":
                languageApplicationFields.put(LanguageApplicationFieldsEnum.CLOUD_COMPUTING.getImageContent(), LanguageApplicationFieldsEnum.CLOUD_COMPUTING.getImageURL());
                languageApplicationFields.put(LanguageApplicationFieldsEnum.LOT.getImageContent(), LanguageApplicationFieldsEnum.LOT.getImageURL());
                languageApplicationFields.put(LanguageApplicationFieldsEnum.ARTIFICIAL_INTELLIGENCE.getImageContent(), LanguageApplicationFieldsEnum.ARTIFICIAL_INTELLIGENCE.getImageURL());
                languageApplicationFields.put(LanguageApplicationFieldsEnum.BLOCK_CHAIN.getImageContent(), LanguageApplicationFieldsEnum.BLOCK_CHAIN.getImageURL());

                languageUse.put(LanguageUseEnum.MOBILE_DEVELOPMENT.getImageContent(), LanguageUseEnum.MOBILE_DEVELOPMENT.getImageURL());
                languageUse.put(LanguageUseEnum.DESKTOP_DEVELOPMENT.getImageContent(), LanguageUseEnum.DESKTOP_DEVELOPMENT.getImageURL());
                languageUse.put(LanguageUseEnum.EMBEDDED_DEVELOPMENT.getImageContent(), LanguageUseEnum.EMBEDDED_DEVELOPMENT.getImageURL());

                companyList.add(companyDAO.findByCompanyName("Microsoft"));
                companyList.add(companyDAO.findByCompanyName("Amazon"));
                companyList.add(companyDAO.findByCompanyName("腾讯"));

                languageAdvantage.add("掌握C++能让你与众不同");
                languageAdvantage.add("C++允许调整你的应用性能");

                languageDisadvantage.add("C++学习起来非常困难");
                languageDisadvantage.add("会花费大量时间挑选程序的子部件");

                break;
            case "JavaScript":
                languageApplicationFields.put(LanguageApplicationFieldsEnum.LOT.getImageContent(), LanguageApplicationFieldsEnum.LOT.getImageURL());
                languageApplicationFields.put(LanguageApplicationFieldsEnum.BLOCK_CHAIN.getImageContent(), LanguageApplicationFieldsEnum.BLOCK_CHAIN.getImageURL());

                languageUse.put(LanguageUseEnum.WEB_DEVELOPMENT.getImageContent(), LanguageUseEnum.WEB_DEVELOPMENT.getImageURL());
                languageUse.put(LanguageUseEnum.MOBILE_DEVELOPMENT.getImageContent(), LanguageUseEnum.MOBILE_DEVELOPMENT.getImageURL());

                companyList.add(companyDAO.findByCompanyName("Microsoft"));
                companyList.add(companyDAO.findByCompanyName("阿里巴巴"));
                companyList.add(companyDAO.findByCompanyName("Google"));

                languageAdvantage.add("作为客户端，JavaScript非常快");
                languageAdvantage.add("能和其他语言相处融洽");

                languageDisadvantage.add("在某些情况下会被恶意利用");
                languageDisadvantage.add("有时会被不同的浏览器编译出不同的结果");

                break;
            case "PHP":
                languageApplicationFields.put(LanguageApplicationFieldsEnum.LOT.getImageContent(), LanguageApplicationFieldsEnum.LOT.getImageURL());

                languageUse.put(LanguageUseEnum.WEB_DEVELOPMENT.getImageContent(), LanguageUseEnum.WEB_DEVELOPMENT.getImageURL());

                companyList.add(companyDAO.findByCompanyName("IBM"));
                companyList.add(companyDAO.findByCompanyName("Facebook"));
                companyList.add(companyDAO.findByCompanyName("百度"));

                languageAdvantage.add("容易上手");
                languageAdvantage.add("对数据库友好");

                languageDisadvantage.add("有许多丑陋的代码");
                languageDisadvantage.add("错误处理考虑得不周全");

                break;
            case "C#":
                languageApplicationFields.put(LanguageApplicationFieldsEnum.LOT.getImageContent(), LanguageApplicationFieldsEnum.LOT.getImageURL());

                languageUse.put(LanguageUseEnum.WEB_DEVELOPMENT.getImageContent(), LanguageUseEnum.WEB_DEVELOPMENT.getImageURL());
                languageUse.put(LanguageUseEnum.MOBILE_DEVELOPMENT.getImageContent(), LanguageUseEnum.MOBILE_DEVELOPMENT.getImageURL());
                languageUse.put(LanguageUseEnum.DESKTOP_DEVELOPMENT.getImageContent(), LanguageUseEnum.DESKTOP_DEVELOPMENT.getImageURL());

                companyList.add(companyDAO.findByCompanyName("Microsoft"));
                companyList.add(companyDAO.findByCompanyName("Amazon"));

                languageAdvantage.add("完整的.NET库，提供接入大量功能");
                languageAdvantage.add("可以转移到其他语言，如Java,PHP等");

                languageDisadvantage.add("不适合初学者");
                languageDisadvantage.add("没有跨平台能力");

                break;
            case "Scala":
                languageApplicationFields.put(LanguageApplicationFieldsEnum.BIG_DATA.getImageContent(), LanguageApplicationFieldsEnum.BIG_DATA.getImageURL());

                languageUse.put(LanguageUseEnum.WEB_DEVELOPMENT.getImageContent(), LanguageUseEnum.WEB_DEVELOPMENT.getImageURL());
                languageUse.put(LanguageUseEnum.MOBILE_DEVELOPMENT.getImageContent(), LanguageUseEnum.MOBILE_DEVELOPMENT.getImageURL());

                companyList.add(companyDAO.findByCompanyName("Twitter"));
                companyList.add(companyDAO.findByCompanyName("Sony"));
                companyList.add(companyDAO.findByCompanyName("Linkedin"));


                languageAdvantage.add("Scala融合了静态类型系统、面向对象、函数式编程等语言特性");
                languageAdvantage.add("Scala有交互式命令行(REPL), 可以在上面快速的试各种语法和代码");

                languageDisadvantage.add("Scala对二进制不兼容");
                languageDisadvantage.add("Scala对于新手来说上手较难");

                break;
            case "SQL":
                languageApplicationFields.put(LanguageApplicationFieldsEnum.CLOUD_COMPUTING.getImageContent(), LanguageApplicationFieldsEnum.CLOUD_COMPUTING.getImageURL());

                languageUse.put(LanguageUseEnum.DESKTOP_DEVELOPMENT.getImageContent(), LanguageUseEnum.DESKTOP_DEVELOPMENT.getImageURL());

                companyList.add(companyDAO.findByCompanyName("Microsoft"));
                companyList.add(companyDAO.findByCompanyName("ORACLE"));

                languageAdvantage.add("使用方式灵活");
                languageAdvantage.add("语言简洁，语法简单，好学好用");

                languageDisadvantage.add("维护较为麻烦");
                languageDisadvantage.add("可移植性差");

                break;
            case "Objective-C":
                languageApplicationFields.put(LanguageApplicationFieldsEnum.ARTIFICIAL_INTELLIGENCE.getImageContent(), LanguageApplicationFieldsEnum.ARTIFICIAL_INTELLIGENCE.getImageURL());

                languageUse.put(LanguageUseEnum.MOBILE_DEVELOPMENT.getImageContent(), LanguageUseEnum.MOBILE_DEVELOPMENT.getImageURL());
                languageUse.put(LanguageUseEnum.DESKTOP_DEVELOPMENT.getImageContent(), LanguageUseEnum.DESKTOP_DEVELOPMENT.getImageURL());

                companyList.add(companyDAO.findByCompanyName("Apple"));

                languageAdvantage.add("可以桥接苹果开发的类库");
                languageAdvantage.add("写的程序更动态化，运行速度快");

                languageDisadvantage.add("不是最容易掌握的语言");
                languageDisadvantage.add("苹果有自己的编程方法");

                break;
            case "R":
                languageApplicationFields.put(LanguageApplicationFieldsEnum.BIG_DATA.getImageContent(), LanguageApplicationFieldsEnum.BIG_DATA.getImageURL());
                languageApplicationFields.put(LanguageApplicationFieldsEnum.CLOUD_COMPUTING.getImageContent(), LanguageApplicationFieldsEnum.CLOUD_COMPUTING.getImageURL());
                languageApplicationFields.put(LanguageApplicationFieldsEnum.LOT.getImageContent(), LanguageApplicationFieldsEnum.LOT.getImageURL());

                languageUse.put(LanguageUseEnum.DESKTOP_DEVELOPMENT.getImageContent(), LanguageUseEnum.DESKTOP_DEVELOPMENT.getImageURL());

                companyList.add(companyDAO.findByCompanyName("Amazon"));
                companyList.add(companyDAO.findByCompanyName("阿里巴巴"));
                companyList.add(companyDAO.findByCompanyName("京东"));

                languageAdvantage.add("免费，开源，体积小");
                languageAdvantage.add("同各种OS的兼容性好");

                languageDisadvantage.add("内存管理和平行处理都为人诟病");
                languageDisadvantage.add("对大文本处理较差");

                break;
            case "Go":
                languageApplicationFields.put(LanguageApplicationFieldsEnum.BIG_DATA.getImageContent(), LanguageApplicationFieldsEnum.BIG_DATA.getImageURL());
                languageApplicationFields.put(LanguageApplicationFieldsEnum.CLOUD_COMPUTING.getImageContent(), LanguageApplicationFieldsEnum.CLOUD_COMPUTING.getImageURL());
                languageApplicationFields.put(LanguageApplicationFieldsEnum.LOT.getImageContent(), LanguageApplicationFieldsEnum.LOT.getImageURL());
                languageApplicationFields.put(LanguageApplicationFieldsEnum.BLOCK_CHAIN.getImageContent(), LanguageApplicationFieldsEnum.BLOCK_CHAIN.getImageURL());

                languageUse.put(LanguageUseEnum.WEB_DEVELOPMENT.getImageContent(), LanguageUseEnum.WEB_DEVELOPMENT.getImageURL());
                languageUse.put(LanguageUseEnum.DESKTOP_DEVELOPMENT.getImageContent(), LanguageUseEnum.DESKTOP_DEVELOPMENT.getImageURL());

                companyList.add(companyDAO.findByCompanyName("Google"));
                companyList.add(companyDAO.findByCompanyName("IBM"));

                languageAdvantage.add("Go 极其地快。其性能与 Java 或 C++相似");
                languageAdvantage.add("Go 作为一门语言致力于使事情简单化，因此它使用起来非常快速并且简单");

                languageDisadvantage.add("Go 语言没有一个主要的框架，如 Ruby 的 Rails 框架、Python 的 Django 框架或 PHP 的 Laravel");
                languageDisadvantage.add("Go 语言的软件包管理不够完美的。默认情况下，它没有办法制定特定版本的依赖库，也无法创建可复写的 builds");

                break;
            case "Matlab":
                languageApplicationFields.put(LanguageApplicationFieldsEnum.BIG_DATA.getImageContent(), LanguageApplicationFieldsEnum.BIG_DATA.getImageURL());

                languageUse.put(LanguageUseEnum.DESKTOP_DEVELOPMENT.getImageContent(), LanguageUseEnum.DESKTOP_DEVELOPMENT.getImageURL());

                companyList.add(companyDAO.findByCompanyName("Amazon"));
                companyList.add(companyDAO.findByCompanyName("阿里巴巴"));
                companyList.add(companyDAO.findByCompanyName("Microsoft"));

                languageAdvantage.add("用户使用方便，编程效率高");
                languageAdvantage.add("拥有丰富的库函数");

                languageDisadvantage.add("循环运算的效率低");
                languageDisadvantage.add("代码的封装性不好");

                break;
            case "Assembly":
                languageApplicationFields.put(LanguageApplicationFieldsEnum.LOT.getImageContent(), LanguageApplicationFieldsEnum.LOT.getImageURL());

                languageUse.put(LanguageUseEnum.EMBEDDED_DEVELOPMENT.getImageContent(), LanguageUseEnum.EMBEDDED_DEVELOPMENT.getImageURL());

                companyList.add(companyDAO.findByCompanyName("Intel"));
                companyList.add(companyDAO.findByCompanyName("阿里巴巴"));
                companyList.add(companyDAO.findByCompanyName("Apple"));

                languageAdvantage.add("能够最大限度地发挥硬件的功能");
                languageAdvantage.add("不受编译器的限制，对生成的二进制代码进行完全的控制");
                languageAdvantage.add("用汇编语言编写的程序比用高级语言编写的程序所要求的存储空间与执行时间将显著减少");

                languageDisadvantage.add("编写的代码非常难懂，不好维护");
                languageDisadvantage.add("只能针对特定的体系结构和处理器进行优化");
                languageDisadvantage.add("很容易产生Bug，难于调试");

                break;
            case "Ruby":
                languageApplicationFields.put(LanguageApplicationFieldsEnum.CLOUD_COMPUTING.getImageContent(), LanguageApplicationFieldsEnum.CLOUD_COMPUTING.getImageURL());

                languageUse.put(LanguageUseEnum.WEB_DEVELOPMENT.getImageContent(), LanguageUseEnum.WEB_DEVELOPMENT.getImageURL());
                languageUse.put(LanguageUseEnum.DESKTOP_DEVELOPMENT.getImageContent(), LanguageUseEnum.DESKTOP_DEVELOPMENT.getImageURL());

                companyList.add(companyDAO.findByCompanyName("Amazon"));

                languageAdvantage.add("Ruby和Python都是公认的最好入门的语言");
                languageAdvantage.add("大量的扩展和库让创造东西十分简单");
                languageAdvantage.add("世界上每个主要城市都有Ruby社区，在Github上最受欢迎的语言之一");

                languageDisadvantage.add(" 关于Ruby和Rail的主要批评都是\"慢\"");
                languageDisadvantage.add("很难找到很好的文档，尤其是对于一些冷门的库或多个库复用");
                languageDisadvantage.add(" RoR应用运行起来不像Java和C写出来那么快，但大部分的Ruby on Rails的应用都足够快");
        }

        moreLanguageInformation.setCompanyList(companyList);
        moreLanguageInformation.setLanguageAdvantage(languageAdvantage);
        moreLanguageInformation.setLanguageDisadvantage(languageDisadvantage);
        moreLanguageInformation.setLanguageApplicationFields(languageApplicationFields);
        moreLanguageInformation.setLanguageUse(languageUse);

        map.put(languageName, moreLanguageInformation);

        return moreLanguageInformation;
    }
}

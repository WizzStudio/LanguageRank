package com.wizzstudio.languagerank.service;

public interface CityExponentService {

    /**
     * 计算雇主需求榜的城市指数
     * @param languageName
     * @return CityExponent
     */
    Double findCityExponent(String languageName);

}

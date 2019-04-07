package com.wizzstudio.languagerank.util;

/*
Created by Ben Wen on 2019/4/7.
*/

import java.text.DecimalFormat;

public class DoubleUtil {
    // 设置小数点后保留一位小数
    public static Double getDecimalFormat(Double exponent) {
        DecimalFormat df = new DecimalFormat("#.#");
        return Double.parseDouble(df.format(exponent));
    }
}

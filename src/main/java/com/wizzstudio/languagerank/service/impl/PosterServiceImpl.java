package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/4/11.
*/

import com.wizzstudio.languagerank.dao.CompanyDAO;
import com.wizzstudio.languagerank.dao.LanguageDAO;
import com.wizzstudio.languagerank.dao.employeerankDAO.CompanySalaryDAO;
import com.wizzstudio.languagerank.dao.employeerankDAO.EmployeeRankDAO;
import com.wizzstudio.languagerank.domain.EmployeeRank.CompanySalary;
import com.wizzstudio.languagerank.domain.EmployeeRank.EmployeeRank;
import com.wizzstudio.languagerank.service.PosterService;
import com.wizzstudio.languagerank.util.Graphics2DUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.List;

@Service
@Slf4j
public class PosterServiceImpl implements PosterService {
    @Autowired
    LanguageDAO languageDAO;
    @Autowired
    EmployeeRankDAO employeeRankDAO;
    @Autowired
    CompanySalaryDAO companySalaryDAO;
    @Autowired
    CompanyDAO companyDAO;

    @Override
    public String createPoster(String languageName) throws IOException {
        Image srcImg = ImageIO.read(getInputStreamFromURL("http://qiniu.ben286.top/eee.png"));
        BufferedImage bufferedImage = new BufferedImage(
                // 宽度
                srcImg.getWidth(null),
                // 高度
                srcImg.getHeight(null),
                // 图片类型
                BufferedImage.TYPE_INT_RGB);
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        // 得到2d画笔对象
        Graphics2D g = bufferedImage.createGraphics();
        // 设置对线段的锯齿状边缘处理
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 设置画布背景
        g.drawImage(srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);

        java.util.List<CompanySalary> companySalaryDAOList = companySalaryDAO.findTopThreeByLanguageName(languageName);
        String ranking = "未上榜";
        List<EmployeeRank> employeeRankList = employeeRankDAO.findTopTenLanguage();
        for (int i = 0; i < 10; i++) {
            if (employeeRankList.get(i).getLanguageName().equals(languageName)) {
                ranking = Integer.toString(i + 1);
                break;
            }
        }

        Graphics2DUtils.drawString(g, Color.black, new Font("PingFang SC Bold", Font.BOLD, 34), ranking, 130, 300);
        Graphics2DUtils.drawString(g, Color.black, new Font("PingFang SC Bold", Font.BOLD, 34), Double.toString(employeeRankDAO.findByLanguageName(languageName).getEmployeeFinalExponent()), 570, 300);
        g.drawImage(ImageIO.read(getInputStreamFromURL(languageDAO.findByLanguageName(languageName).getLanguageSymbol())), 340, 250, 100, 100, null);
        g.drawImage(ImageIO.read(getInputStreamFromURL(companyDAO.findByCompanyName(companySalaryDAOList.get(0).getCompanyName()).getCompanySymbol())), 130, 620, 100, 100, null);
        g.drawImage(ImageIO.read(getInputStreamFromURL(companyDAO.findByCompanyName(companySalaryDAOList.get(1).getCompanyName()).getCompanySymbol())), 320, 600, 100, 100, null);
        g.drawImage(ImageIO.read(getInputStreamFromURL(companyDAO.findByCompanyName(companySalaryDAOList.get(2).getCompanyName()).getCompanySymbol())), 520, 620, 100, 100, null);

        // 处理画作
        g.dispose();
        // 得到输出流
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", os);
        // 转成base64编码前端可以直接显示，也可转换成其它形式比如文件
        String encodeStr = Base64.getEncoder().encodeToString(os.toByteArray());
        // 保存为图片文件
        FileUtils.writeByteArrayToFile(new File("C:/Users/o文蚊蚊o/Desktop/" + languageName + ".jpg"), os.toByteArray());
        // 关闭输入输出流
        os.close();

        log.info("生成"+ languageName + "雇主需求详情页海报成功");
        return encodeStr;
    }

    private static InputStream getInputStreamFromURL(String destUrl){

        HttpURLConnection httpUrl = null;
        URL url = null;
        InputStream in = null;
        try{
            url = new URL(destUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            in = httpUrl.getInputStream();
            return in;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

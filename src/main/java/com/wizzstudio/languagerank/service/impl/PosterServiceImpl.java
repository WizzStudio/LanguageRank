package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/4/11.
*/

import cn.binarywang.wx.miniapp.api.WxMaQrcodeService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaCodeLineColor;
import com.wizzstudio.languagerank.DAO.CompanyDAO;
import com.wizzstudio.languagerank.DAO.LanguageDAO;
import com.wizzstudio.languagerank.DAO.employeerankDAO.CompanySalaryDAO;
import com.wizzstudio.languagerank.DAO.employeerankDAO.EmployeeRankDAO;
import com.wizzstudio.languagerank.domain.employeerank.CompanySalary;
import com.wizzstudio.languagerank.domain.employeerank.EmployeeRank;
import com.wizzstudio.languagerank.service.PosterService;
import com.wizzstudio.languagerank.util.Graphics2DUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
    @Autowired
    WxMaService wxService;

    @Override
    public String invitationCard(Integer userId, Integer clazzId) throws IOException {
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

//        List<CompanySalary> companySalaryDAOList = companySalaryDAO.findTopThreeByLanguageName(languageName);
//        String ranking = "未上榜";
//        List<EmployeeRank> employeeRankList = employeeRankDAO.findTopTenLanguage();
//        for (int i = 0; i < 10; i++) {
//            if (employeeRankList.get(i).getLanguageName().equals(languageName)) {
//                ranking = Integer.toString(i + 1);
//                break;
//            }
//        }
//
//        Graphics2DUtils.drawString(g, Color.black, new Font("PingFang SC Bold", Font.BOLD, 34), ranking, 130, 300);
//        Graphics2DUtils.drawString(g, Color.black, new Font("PingFang SC Bold", Font.BOLD, 34), Double.toString(employeeRankDAO.findByLanguageName(languageName).getEmployeeFinalExponent()), 570, 300);
//        g.drawImage(ImageIO.read(getInputStreamFromURL(languageDAO.findByLanguageName(languageName).getLanguageSymbol())), 340, 250, 100, 100, null);
//        g.drawImage(ImageIO.read(getInputStreamFromURL(companyDAO.findByCompanyName(companySalaryDAOList.get(0).getCompanyName()).getCompanySymbol())), 130, 620, 100, 100, null);
//        g.drawImage(ImageIO.read(getInputStreamFromURL(companyDAO.findByCompanyName(companySalaryDAOList.get(1).getCompanyName()).getCompanySymbol())), 320, 600, 100, 100, null);
//        g.drawImage(ImageIO.read(getInputStreamFromURL(companyDAO.findByCompanyName(companySalaryDAOList.get(2).getCompanyName()).getCompanySymbol())), 520, 620, 100, 100, null);

        // 处理画作
        g.dispose();
        // 得到输出流
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", os);
        // 转成base64编码前端可以直接显示，也可转换成其它形式比如文件
        String encodeStr = Base64.getEncoder().encodeToString(os.toByteArray());
        // 保存为图片文件
//        FileUtils.writeByteArrayToFile(new File("C:/Users/o文蚊蚊o/Desktop/" + languageName + ".jpg"), os.toByteArray());
        // 关闭输入输出流
        os.close();

//        log.info("生成"+ languageName + "雇主需求详情页海报成功");
        return encodeStr;
    }

    @Override
    public String achievementCard(Integer userId, Integer clazzId) throws IOException {
        return null;
    }


    // 头像昵称可以从redis中获取
    private Image getAvatarUrl(String avatarUrl) {
//        InputStream avatarUrlInputStream = getInputStreamFromURL(avatarUrl);
        BufferedImage resultImg = null;

        try {
            BufferedImage buffImg1 = ImageIO.read(new URL(avatarUrl));

            resultImg = new BufferedImage(buffImg1.getWidth(), buffImg1.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = resultImg.createGraphics();
            // 创建一个椭圆形的2D图像
            Ellipse2D.Double shape = new Ellipse2D.Double.Double(0, 0, buffImg1.getWidth(), buffImg1.getHeight());
            // 使用 setRenderingHint 设置抗锯齿
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            resultImg = g.getDeviceConfiguration().createCompatibleImage(buffImg1.getWidth(), buffImg1.getHeight(),Transparency.TRANSLUCENT);
//            g.fill(new Rectangle(buffImg2.getWidth(), buffImg2.getHeight()));
            g = resultImg.createGraphics();
            // 使用 setRenderingHint 设置抗锯齿
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setClip(shape);
            g.drawImage(buffImg1, 0, 0, null);
            g.dispose();
        } catch (MalformedURLException e) {
            log.error("URL格式异常" + e.getMessage(), e);
        } catch (IOException e) {
            log.error("读取图片异常" + e.getMessage(), e);
        }

//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        FileImageOutputStream imageOutputStream = new FileImageOutputStream();
        try {
            ImageIO.write(resultImg , "png", new File("C:/Users/o文蚊蚊o/Desktop/aaa.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    private Image getQrcode(Integer userId, Integer clazzId) throws Exception {
//        String path = "/pages/class/classHome?clazzId=" + clazzId + "&shareId=" + userId;
//
//        try {
//            // 获取小程序二维码生成实例
//            WxMaQrcodeService wxMaQrcodeService = wxService.getQrcodeService();
//            // 设置小程序二维码线条颜色为黑色
//            WxMaCodeLineColor lineColor = new WxMaCodeLineColor("0", "0", "0");
//
//            File qrCodeFile = wxMaQrcodeService.createWxaCode(path, 430, true, lineColor, true);
//            log.info("生成小程序码成功");
//            return ImageIO.read(new FileInputStream(qrCodeFile));
//        } catch (WxErrorException e) {
//            log.error("生成小程序码失败");
//            e.printStackTrace();
//            return null;
//        }
//    }

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

package com.wizzstudio.languagerank.util;

/*
Created by Ben Wen on 2019/4/10.
*/


// 模板，暂时先不删
import com.wizzstudio.languagerank.dao.CompanyDAO;
import com.wizzstudio.languagerank.dao.LanguageDAO;
import com.wizzstudio.languagerank.dao.employeerankDAO.CompanySalaryDAO;
import com.wizzstudio.languagerank.dao.employeerankDAO.EmployeeRankDAO;
import com.wizzstudio.languagerank.domain.CompanySalary;
import com.wizzstudio.languagerank.domain.EmployeeRank;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

//@Component
public class PosterUtils {
//    @Autowired
//    LanguageDAO languageDAO;
//    @Autowired
//    EmployeeRankDAO employeeRankDAO;
//    @Autowired
//    CompanySalaryDAO companySalaryDAO;
//    @Autowired
//    CompanyDAO companyDAO;

//    private static final String ACCESS_TOKEN = "20_1yf3XRA2u3mpXkN8U6_2XoTsqNZoHGNOCWN2q6UIRvYDMhcmqDw1qC2m9OrnGHOFMju5KNXlWdfr40USmhvez6W7hVc1G9XXScrnjLgsrAT0ZSqsjKldYGGzBcP7LY9SJTmvM8VGupOf--HOLYPdAEAGTK";

    public String createPoster(String languageName) throws Exception {
        return null;
//        String resourcePath = PosterUtils.class.getResource("/image/").getPath();
//        long nowTime = System.currentTimeMillis();
//        String qrcodeName = UUID.randomUUID().toString().replace("-", "");
//        String petUrlName = UUID.randomUUID().toString().replace("-", "");
//        URL petUnitUrl = new URL(petNameUrl);
//        HttpURLConnection conn = (HttpURLConnection) petUnitUrl.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setConnectTimeout(5 * 1000);
//        BufferedInputStream biss = new BufferedInputStream(conn.getInputStream());
//        OutputStream outputStream = new FileOutputStream(new File( "src/main/resources/image/aaa.png"));
//        int lens;
//        byte[] arrs = new byte[1024];
//        while ((lens = biss.read(arrs)) != -1) {
//            outputStream.write(arrs, 0, lens);
//            outputStream.flush();
//        }
//        outputStream.close();
//        //二维码图片
//        URL getCodeUrl = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + ACCESS_TOKEN);
//        HttpURLConnection httpURLConnection = (HttpURLConnection) getCodeUrl.openConnection();
//        httpURLConnection.setRequestMethod("POST");// 提交模式
//        httpURLConnection.setDoOutput(true);
//        httpURLConnection.setDoInput(true);
//        PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
//        JSONObject paramJson = new JSONObject();
//        paramJson.accumulate("scene", scene).accumulate("page", "pages/index/index");
//        paramJson.put("auto_color", false);
//        JSONObject lineColor = new JSONObject();
//        lineColor.put("r", 0);
//        lineColor.put("g", 0);
//        lineColor.put("b", 0);
//        paramJson.put("line_color", lineColor);
//        printWriter.write(paramJson.toString());
//        printWriter.flush();
//        BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
//        OutputStream ost = new FileOutputStream(new File("src/main/resources/image/bbb.png"));
//        int len;
//        byte[] arr = new byte[1024];
//        while ((len = bis.read(arr)) != -1) {
//            ost.write(arr, 0, len);
//            ost.flush();
//        }
//        ost.close();
//
//        //合成图片
//        String title = posterTitle;
//        if (title.length() > 21) {
//            title = StringUtils.substring(title, 0, 21) + "...";
//        }
//        //宠物图片文件
//        File petImg = FileUtils.toFile(PosterUtils.class.getResource("src/main/resources/image/aaa.png"));
//        System.out.println(petImg);
//        //二维码图片文件 1
//        File qrCodeImg = FileUtils.toFile(PosterUtils.class.getResource("src/main/resources/image/bbb.png"));
        //背景地址
        //为了方便演示放在resources中，可根据实际情况（上传后）将背景模板放入单独的资源文件夹或远程资源服务器
//        URL url = PosterUtils.class.getResource("/image/c5073a05c0d02953c0a357e6f3372b5.png");
//        URL url = new URL("http://qiniu.ben286.top/intel.png");
//        File fileBg = FileUtils.toFile(url);
        //1、背景图片输入流
//        InputStream fis = getInputStreamFromURL("http://qiniu.ben286.top/eee.png");
        //2、背景图片对象
//        Image srcImg = ImageIO.read(getInputStreamFromURL("http://qiniu.ben286.top/eee.png"));
////        System.out.println(fis);
//        //3、创建画布，根据背景图片的宽高
//        BufferedImage bufferedImage = new BufferedImage(
//                //宽度
//                srcImg.getWidth(null),
//                //高度
//                srcImg.getHeight(null),
//                //图片类型
//                BufferedImage.TYPE_INT_RGB);
//        int width = bufferedImage.getWidth();
//        int height = bufferedImage.getHeight();
//        //4、得到2d画笔对象
//        Graphics2D g = bufferedImage.createGraphics();
//        // 设置对线段的锯齿状边缘处理
//        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        //5、设置画布背景
//        g.drawImage(srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
//        //6、```````````开始作画```````````
////        Font rewardFirstFont = new Font("PingFang SC Bold", Font.PLAIN, 24);
////        Font rewardLastFont = new Font("PingFang SC Bold", Font.PLAIN, 48);
////        int rewardFirstWidth = Graphics2DUtils.getStringWidth(g, rewardFirstFont, "赏金￥");
////        int rewardLastWidth = Graphics2DUtils.getStringWidth(g, rewardLastFont, moneyReward);
//        //赏金内容
////        Graphics2DUtils.drawString(g, Color.decode("#ffd434"), rewardFirstFont, "赏金￥", (width - rewardFirstWidth - rewardLastWidth) / 2, 550);
////        Graphics2DUtils.drawString(g, Color.decode("#ffd434"), rewardLastFont, moneyReward, (width - rewardLastWidth + rewardFirstWidth) / 2, 550);
////        //标题
////        Graphics2DUtils.drawString(g, Color.decode("#323232"), new Font("PingFang SC Bold", Font.BOLD, 34), title, 0, 400, width, 12, 5, true);
//        //把宠物图片和二维码图片划入背景
////        System.out.println(new File("src/main/resources/image/aaa.png").exists());
//        try {
//            List<CompanySalary> companySalaryDAOList = companySalaryDAO.findTopThreeByLanguageName(languageName);
//            String ranking = "未上榜";
//            List<EmployeeRank> employeeRankList = employeeRankDAO.findTopTenLanguage();
//            for (int i = 0; i < 10; i++) {
//                if (employeeRankList.get(i).getLanguageName().equals(languageName)) {
//                    ranking = Integer.toString(i + 1);
//                    break;
//                }
//            }
//
//            Graphics2DUtils.drawString(g, Color.black, new Font("PingFang SC Bold", Font.BOLD, 34), ranking, 130, 300);
//            Graphics2DUtils.drawString(g, Color.black, new Font("PingFang SC Bold", Font.BOLD, 34), Double.toString(employeeRankDAO.findByLanguageName(languageName).getEmployeeFinalExponent()), 570, 300);
//            g.drawImage(ImageIO.read(getInputStreamFromURL(languageDAO.findByLanguageName(languageName).getLanguageSymbol())), 340, 250, 100, 100, null);
//            g.drawImage(ImageIO.read(getInputStreamFromURL(companyDAO.findByCompanyName(companySalaryDAOList.get(0).getCompanyName()).getCompanySymbol())), 130, 620, 100, 100, null);
//            g.drawImage(ImageIO.read(getInputStreamFromURL(companyDAO.findByCompanyName(companySalaryDAOList.get(1).getCompanyName()).getCompanySymbol())), 320, 600, 100, 100, null);
//            g.drawImage(ImageIO.read(getInputStreamFromURL(companyDAO.findByCompanyName(companySalaryDAOList.get(2).getCompanyName()).getCompanySymbol())), 520, 620, 100, 100, null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //   ```````````结束作画```````````
//        //7、处理画作
//        g.dispose();
//        //8、得到输出流
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        ImageIO.write(bufferedImage, "jpg", os);
//        //9、转成base64编码前端可以直接显示，也可转换成其它形式比如文件
//        String encodeStr = Base64.getEncoder().encodeToString(os.toByteArray());
//        //保存为图片文件
//        FileUtils.writeByteArrayToFile(new File("C:/Users/o文蚊蚊o/Desktop/" + languageName + ".jpg"), os.toByteArray());
//        //10、关闭输入输出流
////        fis.close();
//        os.close();
////        qrCodeImg.delete();
////        petImg.delete();
//        return encodeStr;

    }

//    private static InputStream getInputStreamFromURL(String destUrl){
//
//        HttpURLConnection httpUrl = null;
//        URL url = null;
//        InputStream in = null;
//        try{
//            url = new URL(destUrl);
//            httpUrl = (HttpURLConnection) url.openConnection();
//            httpUrl.connect();
//            in = httpUrl.getInputStream();
//            return in;
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}

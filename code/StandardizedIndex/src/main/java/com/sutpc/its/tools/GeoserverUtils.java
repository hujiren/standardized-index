package com.sutpc.its.tools;

import freemarker.template.Configuration;
import freemarker.template.Template;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 17:32 2020/4/26.
 * @Description
 * @Modified By:
 */
public class GeoserverUtils {

  /**
   * mapToWordByFtlWeekly.
   */
  public static void mapToWordByFtlWeekly(String outFilePath, String fileName,
      Map<String, Object> dataMap) {
    /** 初始化配置文件. **/
    Configuration configuration = new Configuration();
    /** 设置编码. **/
    configuration.setDefaultEncoding("utf-8");
    /** 我的ftl文件是放在D盘的,如果不是请自行修改,也可以放入配置文件读取.**/
    String fileDirectory = "D:\\weekReport\\";
    /** 加载文件. **/
    try {
      configuration.setDirectoryForTemplateLoading(new File(fileDirectory));
      /** 加载模板. **/
      Template template = configuration.getTemplate("weekreport.ftl");
      configuration.setDefaultEncoding("utf-8");
      File docFile = new File(outFilePath + fileName + ".doc");
      FileOutputStream fos = new FileOutputStream(docFile);
      Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
      template.process(dataMap, out);
      if (out != null) {
        out.close();
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * 向前端输出图片.
   */
  public static void makeImages(HttpServletResponse response, BufferedImage img) {
    //向页面输出图片
    response.setContentType("image/jpg");
    OutputStream os;
    try {
      os = response.getOutputStream();
      ImageIO.write(img, "jpg", os);
      os.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * 通过BufferedImage 转为base64.
   */
  public static String getBase64ByBufImage(BufferedImage img) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
    try {
      ImageIO.write(img, "png", baos);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    //写入流中
    byte[] bytes = baos.toByteArray();//转换成字节
    String pngBase64 = Base64.getEncoder().encodeToString(bytes);//转换成base64串
    pngBase64 = pngBase64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
    return pngBase64;
  }

  /**
   * 生成水印并返回java.awt.image.BufferedImage.
   *
   * @param waterFile 水印文件(图片)
   * @param x 距离右下角的X偏移量
   * @param y 距离右下角的Y偏移量
   * @param alpha 透明度, 选择值从0.0~1.0: 完全透明~完全不透明
   * @return BufferedImage
   * @Title: 构造图片
   * @Description: 生成水印并返回java.awt.image.BufferedImage
   */
  public static BufferedImage watermark(BufferedImage buffImg, File waterFile, int x, int y,
      float alpha) throws IOException {
    // 获取底图
    // BufferedImage buffImg = ImageIO.read(file);
    int diImgWidth = buffImg.getWidth();
    int diImgHeight = buffImg.getHeight();

    // 获取层图
    BufferedImage waterImg = ImageIO.read(waterFile);
    // 创建Graphics2D对象，用在底图对象上绘图
    int waterImgWidth = (int) (waterImg.getWidth() * 0.55);// 获取层图的宽度
    int waterImgHeight = (int) (waterImg.getHeight() * 0.55);// 获取层图的高度
    /** 图片原点为左上角，往右和往下分别是增加的x和y **/
    x = diImgWidth - waterImgWidth;
    x = 0;//左下角的x值為0//diImgWidth-waterImgWidth //右下角
    y = (diImgHeight - waterImgHeight - 10);
    // 在图形和图像中实现混合和透明效果
    // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
    // 绘制
    Graphics2D g2d = buffImg.createGraphics();
    g2d.setBackground(Color.white);
    g2d.drawImage(
        waterImg.getScaledInstance(waterImgWidth,
            waterImgHeight, Image.SCALE_SMOOTH),
        x, y, null);
    //    g2d.drawImage(waterImg, x, y, waterImgWidth, waterImgHeight, null);
    //g2d.dispose();// 释放图形上下文使用的系统资源

    return buffImg;
  }

  /**
   * 通过参数获取BufferedImage.
   *
   * @param type 类型
   * @param year 年份
   * @param month 月份
   * @param urlStr geoserver 服务前缀
   * @param waterImagePath 水印地址
   */
  public static BufferedImage getMonthImages(String type, String year, String month, String urlStr,
      String waterImagePath) {
    if ("subsect".equals(type)) {
      urlStr = urlStr + "/geoserver/TrafficIndex/wms?service=WMS&version=1.1.0&request=GetMap"
          + "&layers=TrafficIndex:month_report_subsect&styles="
          + "&bbox=110.120851,20.857242,110.646122,21.44639"
          + "&width=684&height=768&srs=EPSG:4326&format=image%2Fpng";
    } else if ("majorRoad".equals(type)) {
      urlStr = urlStr + "/geoserver/TrafficIndex/wms?service=WMS&version=1.1.0&request=GetMap"
          + "&layers=TrafficIndex:month_report_mainroad&styles="
          + "&bbox=110.120851,20.857242,110.646122,21.44639"
          + "&width=684&height=768&srs=EPSG:4326&format=image/png";
    } else if ("top10jam".equals(type)) {
      urlStr = urlStr + "/geoserver/TrafficIndex/wms?service=WMS&version=1.1.0&request=GetMap"
          + "&layers=TrafficIndex:month_report_worst_roadsect&styles="
          + "&bbox=110.120851,20.857242,110.646122,21.44639&width=684"
          + "&height=768&srs=EPSG:4326&format=image/png";
    }
    String sqlParam = "&VIEWPARAMS=year:" + year + ";month:" + month;
    urlStr = urlStr + sqlParam;
    URL url;
    try {
      url = new URL(urlStr);
      URLConnection connection = url.openConnection(); //打开连接
      connection.setDoOutput(true);
      InputStream is = connection.getInputStream();
      BufferedImage buffImage = ImageIO
          .read(connection.getInputStream()); //读取连接的流，赋值给BufferedImage对象
      BufferedImage img = buffImage;
      if ("top10jam".equals(type)) {
        img = buffImage;
      } else {
        String waterFilePath = waterImagePath;
        BufferedImage buffImg = watermark(buffImage, new File(waterFilePath), 0, 0, 1.0f);
        img = buffImg;
      }
      return img;

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } //声明url对象
    return null;
  }

  /**
   * 获取周图片.
   *
   * @param type 类型
   * @param year 年份
   * @param week 月份
   * @param geoserverUrl 地图服务前缀
   * @param waterImagePath 水印地址
   */
  public static BufferedImage getWeekImages(String type, String year, String week,
      String geoserverUrl, String waterImagePath) {
    String urlStr = geoserverUrl;
    if ("subsect".equals(type)) {
      urlStr = urlStr + "/geoserver/TrafficIndex/wms?service=WMS&version=1.1.0&request=GetMap"
          + "&layers=TrafficIndex:week_report_subsect&styles=&"
          + "bbox=110.120851,20.857242,110.646122,21.44639"
          + "&width=684&height=768&srs=EPSG:4326&format=image%2Fpng";
    } else if ("majorRoad".equals(type)) {
      urlStr = urlStr + "/geoserver/TrafficIndex/wms?service=WMS&version=1.1.0&request=GetMap"
          + "&layers=TrafficIndex:week_report_mainroad&styles=&"
          + "bbox=110.120851,20.857242,110.646122,21.44639"
          + "&width=684&height=768&srs=EPSG:4326&format=image/png";
    } else if ("top10jam".equals(type)) {
      urlStr = urlStr + "/geoserver/TrafficIndex/wms?service=WMS&version=1.1.0&request=GetMap"
          + "&layers=TrafficIndex:week_report_worst_roadsect&styles="
          + "&bbox=110.120851,20.857242,110.646122,21.44639"
          + "&width=684&height=768&srs=EPSG:4326&format=image/png";
    }

    String sqlParam = "&VIEWPARAMS=year:" + year + ";week:" + week;
    urlStr = urlStr + sqlParam;
    System.out.println("周报geoserver图片获取路径：" + urlStr);
    URL url;
    try {
      url = new URL(urlStr);
      URLConnection connection = url.openConnection(); //打开连接
      connection.setDoOutput(true);
      InputStream is = connection.getInputStream();
      BufferedImage buffImage = ImageIO
          .read(connection.getInputStream()); //读取连接的流，赋值给BufferedImage对象
      BufferedImage img = buffImage;
      if ("top10jam".equals(type)) {
        img = buffImage;
      } else {
        String waterFilePath = waterImagePath;
        BufferedImage buffImg = watermark(buffImage, new File(waterFilePath), 0, 0, 1.0f);
        img = buffImg;
      }
      return img;

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } //声明url对象
    return null;
  }

  /**
   * 通过参数获取BufferedImage.
   *
   * @param urlStr geoserver 服务前缀
   */
  public static BufferedImage getSzWordImages(Map<String, Object> map, String urlStr,
      HttpServletResponse response) {
    Map<String, String> m = getSzGerserverUrl(map, urlStr);
    String u = m.get("url");
    String type = m.get("type");
    String blockWaterImagePath = map.get("blockWaterImagePath").toString();
    String mainRoadWaterImagePath = map.get("mainRoadWaterImagePath").toString();
    String parkWaterImagePath = map.get("parkWaterImagePath").toString();
    URL url;

    try {
      url = new URL(u);
      URLConnection connection = url.openConnection(); //打开连接
      connection.setDoOutput(true);
      BufferedImage buffImage = ImageIO
          .read(connection.getInputStream()); //读取连接的流，赋值给BufferedImage对象
      BufferedImage img = null;
      if ("block".equals(type)) {
        BufferedImage buffImg = watermark(buffImage, new File(blockWaterImagePath), 0, 0, 1.0f);
        img = buffImg;
      } else if ("mainroad".equals(type)) {
        BufferedImage buffImg = watermark(buffImage, new File(mainRoadWaterImagePath), 0, 0, 1.0f);
        img = buffImg;
      } else if ("parkroad".equals(type)) {
        BufferedImage buffImg = watermark(buffImage, new File(parkWaterImagePath), 0, 0, 1.0f);
        img = buffImg;
      } else {
        img = buffImage;
      }
      //向页面输出图片
      response.setContentType("image/jpg");
      OutputStream os = response.getOutputStream();
      ImageIO.write(img, "jpg", os);
      //generateWaterFile(buffImg, fileUrl);
      os.close();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } //声明url对象
    return null;
  }

  /**
   * getSzGerserverUrl.
   */
  public static Map<String, String> getSzGerserverUrl(Map<String, Object> map, String urlStr) {
    Map<String, String> m = new HashMap<>();

    String type = map.get("type") == null ? "" : map.get("type").toString();
    String district = map.get("districtFid") == null ? "" : map.get("districtFid").toString();
    String date = map.get("date") == null ? "" : map.get("date").toString();
    String reportType =
        map.get("reportType") == null ? "" : map.get("reportType").toString();//周报-week，月报-month
    String time = date;
    String layers = "";
    String sect = district;
    String year = "";
    String month = "";
    String week = "";
    String width = "";
    String height = "";
    String bbox = "";

    String baseUrl = urlStr + "/geoserver/TrafficIndex/wms?SERVICE=WMS&VERSION=1.1.1"
        + "&REQUEST=GetMap&FORMAT=image%2Fjpeg&TRANSPARENT=true&SRS=EPSG%3A4326";

    if (reportType.equals("month")) {
      if (type.equals("block")) {   //街道
        layers = "TrafficIndex:sz_monthreport_block";
        m.put("type", "block");
      } else if (type.equals("mainroad")) { //主干路
        layers = "TrafficIndex:sz_reportmonth_mainroad";
        m.put("type", "mainroad");
      } else if (type.equals("betterroad")) {  //改善
        layers = "TrafficIndex:sz_reportmonth_change";
        m.put("type", "betterroad");
      } else if (type.equals("worseroad")) {  //恶化
        layers = "TrafficIndex:sz_reportmonth_change";
        m.put("type", "worseroad");
      } else {    //停车
        layers = "TrafficIndex:sz_reportmonth_parkroad";
        m.put("type", "parkroad");
      }
      sect = district;
      year = time.substring(0, 4);
      month = time.substring(4, 6);
      width = "555";
      height = getHeight(district);
      bbox = getBbox(district);
    } else {
      if (type.equals("block")) {
        layers = "TrafficIndex:sz_weekreport_block";
        m.put("type", "block");
      } else if (type.equals("mainroad")) {
        layers = "TrafficIndex:sz_reportweek_mainroad";
        m.put("type", "mainroad");
      } else if (type.equals("betterroad")) {
        layers = "TrafficIndex:sz_reportweek_change";
        m.put("type", "betterroad");
      } else if (type.equals("worseroad")) {
        layers = "TrafficIndex:sz_reportweek_change";
        m.put("type", "worseroad");
      } else {
        layers = "TrafficIndex:sz_reportweek_parkroad";
        m.put("type", "parkroad");
      }
      sect = district;
      year = time.substring(0, 4);
      week = "30";//reportService.getWeekReportDate(date);
      width = "555";
      height = getHeight(district);
      bbox = getBbox(district);
    }
    String picType = "&request=GetMap&layers=" + layers;
    String sqlParam = "";
    if (reportType.equals("month")) {
      sqlParam = "&VIEWPARAMS=district_id:" + sect + ";year:" + year + ";month:" + month;
    } else {
      sqlParam = "&VIEWPARAMS=district_id:" + sect + ";year:" + year + ";week:" + week;
    }
    String picInfo = "&WIDTH=" + width + "&HEIGHT=" + height + "&BBOX=" + bbox;

    baseUrl = baseUrl + picType + sqlParam + picInfo;
    m.put("url", baseUrl);
    return m;
  }

  /**
   * 获取BBox.
   *
   * @param district not null;
   */
  public static String getBbox(String district) {
    Map<String, String> map = new HashMap<>();
    map.put("1", "113.98195266723633,22.492103576660156,114.11928176879883,22.59510040283203");
    map.put("2", "114.06074523925781,22.519912719726562,114.23240661621094,22.622909545898438");
    map.put("3", "113.84204864501953,22.42584228515625,114.04804229736328,22.666168212890625");
    map.put("4", "114.20150756835938,22.536563873291016,114.35600280761719,22.656726837158203");
    map.put("5", "113.75141143798828,22.52025604248047,113.99173736572266,22.86357879638672");
    map.put("6", "114.0380859375,22.57793426513672,114.38140869140625,22.818260192871094");
    map.put("7", "113.85698318481445,22.682819366455078,114.01147842407227,22.8373146057128");
    map.put("8", "114.26227569580078,22.61432647705078,114.46826934814453,22.785987854003906");
    map.put("9", "113.9501953125,22.569351196289062,114.12185668945312,22.775344848632812");
    map.put("10", "114.32750701904297,22.43785858154297,114.6364974975586,22.678184509277344");
    return map.get(district);
  }

  /**
   * getHeight.
   */
  public static String getHeight(String district) {
    int baseWidth = 555;
    String height = "";
    if (district.equals("1")) {
      height = baseWidth * 600 / 800 + "";
    } else if (district.equals("2")) {
      height = baseWidth * 600 / 1000 + "";
    } else if (district.equals("3")) {
      height = baseWidth * 700 / 600 + "";
    } else if (district.equals("4")) {
      height = baseWidth * 700 / 900 + "";
    } else if (district.equals("5")) {
      height = baseWidth * 1000 / 700 + "";
    } else if (district.equals("6")) {
      height = baseWidth * 700 / 1000 + "";
    } else if (district.equals("7")) {
      height = baseWidth * 900 / 900 + "";
    } else if (district.equals("8")) {
      height = baseWidth * 500 / 600 + "";
    } else if (district.equals("9")) {
      height = baseWidth * 600 / 500 + "";
    } else if (district.equals("10")) {
      height = baseWidth * 700 / 900 + "";
    }
    return height;
  }
}

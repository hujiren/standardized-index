package com.sutpc.its.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Encoder;

/**
 * 指数工具类.
 *
 * @author admin
 * @date 2020/5/25 8:59
 */
public class TpiUtils {

  /**
   * 通过指数值获取当前道路状态等级.
   *
   * @param tpi 当前指数
   */
  public static String getStatusByTpi(double tpi) {
    if (tpi < 2) {
      return "畅通";
    } else if (tpi < 4) {
      return "基本畅通";
    } else if (tpi < 6) {
      return "缓行";
    } else if (tpi < 8) {
      return "较拥堵";
    } else {
      return "拥堵";
    }
  }

  /**
   * 根据日期获取当天是周几并组合成需要字符串.
   *
   * @param time 日期 例：20200525
   * @return 日期加周几 例：25日（周四）
   */
  public static String dateToWeek(String time) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    Calendar cal = Calendar.getInstance();
    Date date;
    try {
      date = sdf.parse(time);
      cal.setTime(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
    String result = String.format("%s日（%s）", time.substring(6, 8), weekDays[w]);
    return result;
  }

  /**
   * 速度转指数.
   *
   * @param speed 速度
   * @param type 道路类型
   * @return 指数
   */
  public static double speedToTpi(double speed, double type) {
    double exponent = 0;
    if (type == 1 || type == 2) {
      if (speed <= 15) {
        exponent = 10 - ((speed - 0) / (15 - 0)) * 2;
      } else if (speed > 15 && speed <= 30) {
        exponent = 8 - ((speed - 15) / (30 - 15)) * 2;
      } else if (speed > 30 && speed <= 45) {
        exponent = 6 - ((speed - 30) / (45 - 30)) * 2;
      } else if (speed > 45 && speed <= 60) {
        exponent = 4 - ((speed - 45) / (60 - 45)) * 2;
      } else if (speed > 60) {
        if (type == 1) {
          exponent = 2 - ((speed - 60) / (100 - 60)) * 2;
        } else {
          exponent = 2 - ((speed - 60) / (80 - 60)) * 2;
        }
      }
    } else if (type == 3) {
      if (speed <= 10) {
        exponent = 10 - ((speed - 0) / (10 - 0)) * 2;
      } else if (speed > 10 && speed <= 20) {
        exponent = 8 - ((speed - 10) / (20 - 10)) * 2;
      } else if (speed > 20 && speed <= 30) {
        exponent = 6 - ((speed - 20) / (30 - 20)) * 2;
      } else if (speed > 30 && speed <= 40) {
        exponent = 4 - ((speed - 30) / (40 - 30)) * 2;
      } else if (speed > 40) {
        exponent = 2 - ((speed - 40) / (60 - 40)) * 2;
      }
    } else if (type == 4 || type == 5) {
      if (speed <= 10) {
        exponent = 10 - ((speed - 0) / (10 - 0)) * 2;
      } else if (speed > 10 && speed <= 15) {
        exponent = 8 - ((speed - 10) / (15 - 10)) * 2;
      } else if (speed > 15 && speed <= 20) {
        exponent = 6 - ((speed - 15) / (20 - 15)) * 2;
      } else if (speed > 20 && speed <= 30) {
        exponent = 4 - ((speed - 20) / (30 - 20)) * 2;
      } else if (speed > 30) {
        if (type == 4) {
          exponent = 2 - ((speed - 30) / (50 - 30)) * 2;
        } else {
          exponent = 2 - ((speed - 30) / (40 - 30)) * 2;
        }
      }
    }
    return exponent < 0 ? 0 : exponent;
  }

  /**
   * 计算增长量比值（保留小数位数）.
   *
   * @param last 上一个值
   * @param current 当前值
   * @param digit 保留小数位数
   * @return 增长量比值
   */
  public static double calculateGrowth(double last, double current, int digit) {
    double ratio = calculateGrowth(last, current);
    return getByDigit(ratio, digit);
  }

  /**
   * 计算增长量比值（高精度）.
   *
   * @param last 上一个值
   * @param current 当前值
   * @return 增长量比值
   */
  public static double calculateGrowth(double last, double current) {
    return (last == 0 ? 0 : (current - last) / last) * 100.0;
  }

  /**
   * 保留小数位数.
   *
   * @param value 值
   * @param digit 保留小数位数
   * @return 保留小数位数
   */
  public static double getByDigit(double value, int digit) {
    return new BigDecimal(value).setScale(digit, BigDecimal.ROUND_HALF_UP).doubleValue();
  }

  /**
   * 根据开始结束日期获取统计日期范围字符串.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   */
  public static String getDateCalculated(Integer startDate, Integer endDate) {
    String startTime = startDate.toString();
    String endTime = endDate.toString();
    String startYear = startTime.substring(0, 4);
    String startMonth = startTime.substring(4, 6);
    String startDay = startTime.substring(6, 8);
    String endYear = endTime.substring(0, 4);
    String endMonth = endTime.substring(4, 6);
    String endDay = endTime.substring(6, 8);
    return String
        .format("%s年%s月%s日至%s年%s月%s日",
            startYear, startMonth, startDay, endYear, endMonth, endDay);
  }

  /**
   * 根据循环获取标题序号.
   *
   * @param titleIndex 序号
   */
  public static String getTitleIndex(int titleIndex) {
    String[] arr = new String[]{"一、", "二、", "三、", "四、", "五、", "六、", "七、", "八、",
        "九、", "十、", "十一、", "十二、", "十三、", "十四、", "十五、", "十六、", "十七、",
        "十八、", "十九、", "二十、"};
    if (titleIndex < arr.length) {
      return arr[titleIndex];
    }
    return null;
  }


  /**
   * base64转BufferedImage流.
   */
  public static BufferedImage base64ToBufferedImage(String base64) {
    BufferedImage bi = null;
    if (base64.contains(",")) {
      String[] s = base64.split(",");
      base64 = s[1];
    }
    byte[] src1 = Base64.decodeBase64(base64);
    ByteArrayInputStream inputStream = new ByteArrayInputStream(src1);
    try {
      bi = ImageIO.read(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return bi;
  }

  /**
   * 图片流转base64.
   *
   * @param bufferedImage 图片流
   * @return base64串
   */
  public static String bufferedImageToBase64(BufferedImage bufferedImage) {
    String base64 = new String();
    //io流
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    //输出流
    try {
      //写入流中
      ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
      //转换成字节
      byte[] bytes = byteArrayOutputStream.toByteArray();
      BASE64Encoder encoder = new BASE64Encoder();
      //转换成base64串
      String pngBase64 = encoder.encodeBuffer(bytes).trim();
      //删除 空格和换行
      pngBase64 = pngBase64.replaceAll("\n", "").replaceAll("\r", "");
      base64 = "data:image/jpg;base64," + pngBase64;
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (byteArrayOutputStream != null) {
        try {
          byteArrayOutputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return base64;
  }

  /**
   * 通过地图服务的url获取BufferedImage.
   *
   * @param urlPath 地图服务访问url路径
   * @return 图片流
   */
  public static BufferedImage getBufferedImageByUrl(String urlPath) {
    URL url = null;
    BufferedImage buffImage = null;
    try {
      url = new URL(urlPath);
      //打开连接
      URLConnection connection = url.openConnection();
      connection.setDoOutput(true);
      //读取连接的流，赋值给BufferedImage对象
      buffImage = ImageIO.read(connection.getInputStream());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return buffImage;
  }

  /**
   * 组合水印到image上.
   *
   * @param waterFile 源文件(图片)
   * @param x 距离右下角的X偏移量 代码里暂时写好位置，所以参数随便穿入
   * @param y 距离右下角的Y偏移量 代码里暂时写好位置，所以参数随便穿入
   * @param alpha 透明度, 选择值从0.0~1.0: 完全透明~完全不透明
   * @return BufferedImage
   * @Title: 构造图片
   * @Description: 生成水印并返回java.awt.image.BufferedImage
   */
  public static BufferedImage watermark(BufferedImage buffImg, File waterFile, int x, int y,
      float alpha) throws IOException {
    // 获取底图
    int diImgWidth = buffImg.getWidth();
    int diImgHeight = buffImg.getHeight();

    // 获取层图
    BufferedImage waterImg = ImageIO.read(waterFile);
    // 创建Graphics2D对象，用在底图对象上绘图
    Graphics2D g2d = buffImg.createGraphics();
    // 获取层图的宽度
    int waterImgWidth = (int) (waterImg.getWidth() * 0.55);
    // 获取层图的高度
    int waterImgHeight = (int) (waterImg.getHeight() * 0.55);
    /** 图片原点为左上角，往右和往下分别是增加的x和y **/
    //左下角的x值//diImgWidth-waterImgWidth //右下角
    x = diImgWidth - waterImgWidth;
    y = (diImgHeight - waterImgHeight - 10);
    // 在图形和图像中实现混合和透明效果
    // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

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
   * 获取BBox.
   *
   * @param district not null;
   */
  public static String getBbox(String district) {
    Map<String, String> map = new HashMap<>();
    map.put("1",
        "&styles=&bbox=113.98195266723633,22.492103576660156,114.11928176879883,22.59510040283203");
    map.put("2",
        "&styles=&bbox=114.06074523925781,22.519912719726562,"
            + "114.23240661621094,22.622909545898438");
    map.put("3",
        "&styles=&bbox=113.84204864501953,22.42584228515625,114.04804229736328,22.666168212890625");
    map.put("4",
        "&styles=&bbox=114.20150756835938,22.536563873291016,"
            + "114.35600280761719,22.656726837158203");
    map.put("5",
        "&styles=&bbox=113.75141143798828,22.52025604248047,113.99173736572266,22.86357879638672");
    map.put("6",
        "&styles=&bbox=114.0380859375,22.57793426513672,114.38140869140625,22.818260192871094");
    map.put("7",
        "&styles=&bbox=113.85698318481445,22.682819366455078,114.01147842407227,22.8373146057128");
    map.put("8",
        "&styles=&bbox=114.26227569580078,22.61432647705078,114.46826934814453,22.785987854003906");
    map.put("9",
        "&styles=&bbox=113.9501953125,22.569351196289062,114.12185668945312,22.775344848632812");
    map.put("10",
        "&styles=&bbox=114.32750701904297,22.43785858154297,114.6364974975586,22.678184509277344");
    return map.get(district);
  }
}

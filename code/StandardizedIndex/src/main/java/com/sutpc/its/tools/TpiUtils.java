package com.sutpc.its.tools;

import com.sutpc.its.po.RoadSectionChangePo;
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

  public static BigDecimal PreservedDecimal(String value, int digit){
    BigDecimal val = new BigDecimal(value);
    BigDecimal bigDecimal = val.setScale(digit, BigDecimal.ROUND_UP);
    return bigDecimal;
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
   * 恶化路段的列表排序降序.
   */
  public static void collectionSortDesc(List<RoadSectionChangePo> data) {
    Collections.sort(data, new Comparator<RoadSectionChangePo>() {
      @Override
      public int compare(RoadSectionChangePo o1, RoadSectionChangePo o2) {
        double r1 = o1.getRatio();
        double r2 = o2.getRatio();
        if (r2 >= r1) {
          return 1;
        } else {
          return -1;
        }
      }
    });
  }

  /**
   * 改善路段的列表排序降序.
   */
  public static void collectionSortAsc(List<RoadSectionChangePo> data) {
    Collections.sort(data, new Comparator<RoadSectionChangePo>() {
      @Override
      public int compare(RoadSectionChangePo o1, RoadSectionChangePo o2) {
        double r1 = o1.getRatio();
        double r2 = o2.getRatio();
        if (r1 >= r2) {
          return 1;
        } else {
          return -1;
        }
      }
    });
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
        "&styles=&bbox=113.996289,22.499517,114.104985,22.587634&width=768&height=622");
    map.put("2",
        "&styles=&bbox=114.065071,22.526919,114.220639,22.618263&width=768&height=450");
    map.put("3",
        "&styles=&bbox=113.866209,22.444572,114.026764,22.652567&width=592&height=768");
    map.put("4",
        "&styles=&bbox=114.203982,22.54045,114.351082,22.655171&width=768&height=598");
    map.put("5",
        "&styles=&bbox=113.757363,22.521829,113.980464,22.861723&width=504&height=768");
    map.put("6",
        "&styles=&bbox=114.049995,22.582329,114.35659,22.813697&width=768&height=579");
    map.put("7",
        "&styles=&bbox=113.858991,22.685803,114.00906,22.832865&width=768&height=752");
    map.put("8",
        "&styles=&bbox=114.267236,22.619753,114.445408,22.781574&width=768&height=697");
    map.put("9",
        "&styles=&bbox=113.966678,22.581755,114.113564,22.77184&width=593&height=768");
    map.put("10",
        "&styles=&bbox=114.336849,22.44718,114.62779,22.669641&width=768&height=587");
    return map.get(district);
  }

  /**
   * 获取BBox.
   *
   * @param district not null;
   */
  public static String getBboxMainRoad(String district) {
    Map<String, String> map = new HashMap<>();
    map.put("1",
        "&styles=&bbox=113.909454,22.514352,114.114704,22.67483&width=768&height=600");
    map.put("2",
        "&styles=&bbox=114.018941,22.527283,114.223509,22.575146&width=768&height=330");
    map.put("3",
        "&styles=&bbox=113.874382,22.476946,114.092318,22.625284&width=768&height=522");
    map.put("4",
        "&styles=&bbox=114.146943,22.552685,114.511491,22.66471&width=768&height=330");
    map.put("5",
        "&styles=&bbox=113.805814,22.541134,113.91817,22.798411&width=335&height=768");
    map.put("6",
        "&styles=&bbox=113.93336,22.560232,114.34357,22.81142&width=768&height=470");
    map.put("7",
        "&styles=&bbox=113.846914,22.569271,114.071251,22.814835&width=701&height=768");
    map.put("8",
        "&styles=&bbox=114.276802,22.621182,114.422497,22.770857&width=747&height=768");
    map.put("9",
        "&styles=&bbox=113.929906,22.558155,114.134152,22.721045&width=768&height=612");
    map.put("10",
        "&styles=&bbox=114.254735,22.462966,114.540783,22.689423&width=768&height=608");
    return map.get(district);
  }

  public static Integer getTimePrecisionValue(String timePrecision) {
    Integer timePrecisionValue = 1;
    String flag = "";
    switch (timePrecision) {
      case "five_min" :timePrecisionValue = 1;flag = "PERIOD";break;
      case "fifteen_min" :timePrecisionValue = 3;flag = "PERIOD";break;
      case "half_an_hour" :timePrecisionValue = 6;flag = "PERIOD";break;
      case "hour" :timePrecisionValue = 12;flag = "PERIOD";break;
      case "day" :timePrecisionValue = 96;flag = "FDATE";break;
      case "month" :timePrecisionValue = 96*30;flag = "MONTH";break;
      case "year" :timePrecisionValue = 96*365;flag = "YEAR";break;
      default:timePrecisionValue = 1;flag = "PERIOD";
    }
    return timePrecisionValue;
  }

  public static String getFlag(String timePrecision) {
    Integer timePrecisionValue = 1;
    String flag = "";
    switch (timePrecision) {
      case "five_min" :timePrecisionValue = 1;flag = "PERIOD";break;
      case "fifteen_min" :timePrecisionValue = 3;flag = "PERIOD";break;
      case "half_an_hour" :timePrecisionValue = 6;flag = "PERIOD";break;
      case "hour" :timePrecisionValue = 12;flag = "PERIOD";break;
      case "day" :timePrecisionValue = 96;flag = "FDATE";break;
      case "month" :timePrecisionValue = 96*30;flag = "MONTH";break;
      case "year" :timePrecisionValue = 96*365;flag = "YEAR";break;
      default:timePrecisionValue = 1;flag = "PERIOD";
    }
    return flag;
  }

}

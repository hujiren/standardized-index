package com.sutpc.its.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.sutpc.its.dao.ICommonQueryDao;
import com.sutpc.its.tools.system.JsonMapUtils;
import com.sutpc.its.tools.time.LunarUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * utils.
 */
public class Utils {

  /**
   * json格式转实体类.
   *
   * @param cls 实体类
   * @param json json字符串
   * @param <T> 实体类泛型
   * @return 实体类对象
   */
  public static <T> T jsonToEntity(Class<T> cls, Object json) {
    return JSONObject.parseObject(JSON.toJSONString(json), cls);
  }

  /**
   * jsonArray转List<Object/>类型.
   *
   * @param jsonArray json数组
   * @param cls 实体类
   * @param <T> 实体类泛型
   * @return List对象
   */
  public static <T> List<T> jsonArrayToListEntity(JSONArray jsonArray, Class<T> cls) {
    List<T> data = Lists.newArrayList();
    for (Object obj : jsonArray) {
      data.add(jsonToEntity(cls, obj));
    }
    return data;
  }

  /**
   * getTodayBeforeDate.
   */
  public static String getTodayBeforeDate(int day) {
    Calendar calendar1 = Calendar.getInstance();
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
    calendar1.add(Calendar.DATE, day);
    String str = sdf1.format(calendar1.getTime());
    return str;
  }

  /**
   * 字符串转map.
   */
  public static Map<String, Object> stringToMap(String jsonStr) {
    JSONObject json = JSONObject.parseObject(jsonStr);
    Map<String, Object> map = (Map<String, Object>) json.parse(jsonStr);
    return map;
  }

  /**
   * 获得某个月最大天数.
   *
   * @param year 年份
   * @param month 月份 (1-12)
   * @return 某个月最大天数
   */
  public static int getMaxDayByYearMonth(int year, int month) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, month - 1);
    return calendar.getActualMaximum(Calendar.DATE);
  }

  /**
   * 检查map中是否有非法字符.
   */
  public static boolean checkMapChar(Map<String, Object> map) {
    for (String key : map.keySet()) {
      String regEx = "[ `~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
      Pattern p = Pattern.compile(regEx);
      Matcher m = p.matcher(map.get(key).toString());
      if (m.find()) {
        return true;
      }
    }
    return false;
  }

  /**
   * 读取txt文件.
   */
  public static String readTxtFile(String filePath) {
    String result = "";
    try {
      String lineTxt = "";
      String encoding = "utf-8";
      File file = new File(filePath);
      if (file.isFile() && file.exists()) { //判断文件是否存在
        InputStreamReader read = new InputStreamReader(new FileInputStream(file),
            encoding);//考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        while ((lineTxt = bufferedReader.readLine()) != null) {
          result += lineTxt;
        }
        read.close();
      } else {
        System.out.println("找不到指定的文件");
        result = "nofile";
      }
    } catch (Exception e) {
      System.out.println("读取文件内容出错");
      result = "error";
      e.printStackTrace();
    }
    return result;
  }

  /**
   * getDownloadFileName.
   */
  public static String getDownloadFileName(Map<String, Object> map, String fileName,
      ICommonQueryDao commonQueryDao) {
    map.put("name", fileName);
    List<Map<String, Object>> list = commonQueryDao.getDownloadInfo(map);
    if (list != null && list.size() > 0) {
      Map<String, Object> mapSon = list.get(0);
      String name = mapSon.get("NAME").toString();
      int startIndex = name.lastIndexOf("_");
      int endIndex = name.length();
      int idStr = Integer.parseInt(name.substring(startIndex + 1, endIndex)) + 1;
      if (idStr < 10) {
        name = fileName + "0" + idStr;
      } else {
        name = fileName + idStr;
      }
      map.put("name", name);
      map.put("id", UUID.randomUUID().toString().replace("-", ""));
      commonQueryDao.setDownloadInfo(map);
      return name;
    }
    map.put("name", fileName + "01");
    map.put("id", UUID.randomUUID().toString().replace("-", ""));
    commonQueryDao.setDownloadInfo(map);
    return fileName + "01";
  }

  /**
   * encodeStr.
   */
  public static String encodeStr(String str) {
    try {
      return new String(str.getBytes("ISO-8859-1"), "UTF-8");
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 获取uuid.
   */
  public static String getUUID() {
    return UUID.randomUUID().toString().replace("-", "");
  }

  /**
   * getColorByIndex.
   */
  public static String getColorByIndex(double v) {
    if (v < 2) {
      return "#009900";
    } else if (v < 4) {
      return "#00FF00";
    } else if (v < 6) {
      return "#FFFF00";
    } else if (v < 8) {
      return "#FF9900";
    } else {
      return "#FF3300";
    }
  }

  /**
   * getRgbaColorByIndex.
   */
  public static String getRgbaColorByIndex(double v) {
    if (v < 2) {
      return "rgba(0,153,0,0.5)";
    } else if (v < 4) {
      return "rgba(0,255,0,0.5)";
    } else if (v < 6) {
      return "rgba(255,255,0,0.5)";
    } else if (v < 8) {
      return "rgba(255,153,0,0.5)";
    } else {
      return "rgba(255,51,0,0.5)";
    }
  }

  /**
   * 保留两位小数，四舍五入的一个老土的方法.
   */
  public static String formatDouble(double d) {
    DecimalFormat df = new DecimalFormat("#0.00");
    return df.format(d);
  }

  /**
   * getCurrentDateSqlString.
   */
  public static String getCurrentDateSqlString() {
    Date date = new Date();
    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
    return sf.format(date);
    //return "20160126";
  }

  /**
   * getCurrentPeriod.
   */
  public static int getCurrentPeriod() {
    Calendar calendar = Calendar.getInstance();
    int cp = calendar.getTime().getHours() * 12 + calendar.getTime().getMinutes() / 5;
    //System.out.println(cp);
    return cp - 1;
  }

  /**
   * getMonthOfLunar.
   */
  public static String getMonthOfLunar(String date) {
    SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
    Date d = null;
    try {
      d = sd.parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(d);

    LunarUtils lunar = new LunarUtils(calendar);

    return lunar.getLunarMonth(lunar.month) + "月";
  }

  /**
   * getDayOfLunar.
   */
  public static String getDayOfLunar(String date) {
    SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
    Date d = null;
    try {
      d = sd.parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(d);

    LunarUtils lunar = new LunarUtils(calendar);

    return LunarUtils.getChinaDayString(lunar.day);
  }

  /**
   * getDayOfWeek.
   */
  public static int getDayOfWeek() {
    Calendar calendar = Calendar.getInstance();
    return calendar.get(Calendar.DAY_OF_WEEK) - 1;
  }

  /**
   * getDayOfWeek.
   */
  public static int getDayOfWeek(String date) {
    SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
    Date d = null;
    try {
      d = sd.parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(d);
    int currentdayofweek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
    if (currentdayofweek == 0) {
      currentdayofweek = 7;
    }

    return currentdayofweek;//周一=1，周日=7
  }

  /**
   * getDayDesOfWeek.
   */
  public static String getDayDesOfWeek(String date) {
    int day = getDayOfWeek(date);
    String[] weekdays = new String[]{"一", "二", "三", "四", "五", "六", "日"};
    return weekdays[day - 1];
  }

  /**
   * 通过用户自定义返回格式，返回当前时间.
   */
  public static String getTimeByStr(String str) {
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat(str);
    return sdf.format(date);
  }

  /**
   * getDaysOfMonth.
   */
  public static int getDaysOfMonth(String date) {
    SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
    Date d = null;
    try {
      d = sd.parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    Calendar calendar = Calendar.getInstance();

    calendar.setTime(d);

    return calendar.getActualMaximum(Calendar.DATE);

  }

  /**
   * getLastMonthDateString.
   */
  public static String getLastMonthDateString(String d) {
    String dy = d.substring(0, 4);
    String dm = d.substring(4, 6);
    String dd = d.substring(6, 8);

    int ldm = Integer.parseInt(dm) - 1;

    if (ldm == 0) {
      return (Integer.parseInt(dy) - 1) + "12" + dd;
    } else {

      String sldm = ldm + "";
      if (ldm < 10) {
        sldm = "0" + sldm;
      }

      return dy + sldm + dd;
    }

  }

  /**
   * getLastYearDateString.
   */
  public static String getLastYearDateString(String d) {
    String dy = d.substring(0, 4);
    String dmd = d.substring(4, 8);

    int ldy = Integer.parseInt(dy) - 1;

    return ldy + dmd;
  }

  /**
   * divideObjects.
   */
  public static Float divideObjects(Object ob1, Object ob2, int s) {

    BigDecimal b = null;

    if (ob1 != null && ob2 != null) {
      if (!ob1.toString().equals("") && !ob2.toString().equals("")) {

        double dob1 = Double.parseDouble(ob1.toString());
        double dob2 = Double.parseDouble(ob2.toString());

        if (dob2 != 0) {
          b = new BigDecimal(dob1 / dob2);
          b = b.setScale(s, BigDecimal.ROUND_HALF_UP);//保留2位小数，四舍五入
        }


      }
    }
    if (b == null) {
      return null;
    }

    return b.floatValue();
  }

  /**
   * getMapKeys.
   */
  public static List<String> getMapKeys(Map<String, Object> map) {
    List<String> keys = new ArrayList<String>();
    Set<String> set = map.keySet();
    Iterator<String> iter = set.iterator();
    while (iter.hasNext()) {
      keys.add(iter.next().toString());
    }

    return keys;
  }

  /**
   * listCopy.
   */
  public static List<Map<String, Object>> listCopy(List<Map<String, Object>> list) {
    List<Map<String, Object>> nlist = new ArrayList<Map<String, Object>>();

    for (int i = 0; i < list.size(); i++) {
      Map<String, Object> tmpmap = new LinkedHashMap<String, Object>();

      Map<String, Object> rmap = list.get(i);
      List<String> keylist = getMapKeys(rmap);

      for (int j = 0; j < keylist.size(); j++) {
        tmpmap.put(keylist.get(j), rmap.get(keylist.get(j)));
      }

      nlist.add(tmpmap);
    }

    return nlist;
  }

  /**
   * downloadUrlString.
   */
  public static String downloadUrlString(String urlString) {
    try {
      URL url = new URL(urlString);
      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
      //GET Request Define:
      urlConnection.setRequestMethod("GET");
      urlConnection.connect();

      //Connection Response From Test Servlet
      //System.out.println("Connection Response From Test Servlet");
      InputStream inputStream = urlConnection.getInputStream();

      //Convert Stream to String

      InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
      StringBuilder result = new StringBuilder();
      String line = null;
      try {
        while ((line = bufferedReader.readLine()) != null) {
          result.append(line + "\n");
        }
      } catch (IOException e) {
        e.printStackTrace();
      } finally {

        try {
          inputStreamReader.close();
          inputStream.close();
          bufferedReader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      //System.out.println(result);

      return result.toString();

    } catch (IOException e) {
      e.printStackTrace();
    }

    return "";
  }

  /**
   * 向指定 URL 发送POST方法的请求.
   *
   * @param url
   *            发送请求的 URL
   * @param param
   *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
   * @return 所代表远程资源的响应结果
   */

  /**
   * 向指定 URL 发送POST方法的请求.
   *
   * @param url 发送请求的 URL
   * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
   * @return 所代表远程资源的响应结果
   */
  public static String sendPost(String url, String param) {

    PrintWriter out = null;
    BufferedReader in = null;
    String result = "";
    try {
      URL realUrl = new URL(url);
      // 打开和URL之间的连接
      URLConnection conn = realUrl.openConnection();
      // 设置通用的请求属性
      conn.setRequestProperty("accept", "*/*");
      conn.setRequestProperty("connection", "Keep-Alive");
      conn.setRequestProperty("user-agent",
          "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
      // 发送POST请求必须设置如下两行
      conn.setDoOutput(true);
      conn.setDoInput(true);
      // 获取URLConnection对象对应的输出流
      out = new PrintWriter(conn.getOutputStream());
      // 发送请求参数
      out.print(param);
      // flush输出流的缓冲
      out.flush();
      // 定义BufferedReader输入流来读取URL的响应
      in = new BufferedReader(
          new InputStreamReader(conn.getInputStream(), "UTF-8"));
      String line;
      while ((line = in.readLine()) != null) {
        result += line;
      }
    } catch (Exception e) {
      System.out.println("发送 POST 请求出现异常！" + e);
      e.printStackTrace();
    } finally {
      //使用finally块来关闭输出流、输入流
      try {
        if (out != null) {
          out.close();
        }
        if (in != null) {
          in.close();
        }
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
    return result;
  }


  /**
   * 是否为页面请求.
   */
  public static Boolean isPageRequest(String path) {

    if (path.endsWith(".html")) {
      return true;
    }

    return false;
  }

  /**
   * getWsql.
   */
  public static String getWsql(Map<String, Object> map) {
    String wsql = "";
    String[] permSects = (String[]) map.get("permSects");

    if (map.containsKey("sectField")) {
      String sectField = map.get("sectField") + "";
      String sids = sectField + "=0";
      for (int i = 0; i < permSects.length; i++) {
        sids += " or " + sectField + "=" + permSects[i];
      }
      wsql += " and (" + sids + ")";
    } else {

      PeriodUtils.time2period(map);

      //time=20180120 and period=200 and b.sect10=1
      Map<String, Object> qmap = JsonMapUtils.json2Map(map.get("queryObj").toString());

      if (qmap.containsKey("startdate") && !qmap.get("startdate").toString().equals("")) {
        wsql += " and t.time>=" + qmap.get("startdate");
      }
      if (qmap.containsKey("enddate") && !qmap.get("enddate").toString().equals("")) {
        wsql += " and t.time<=" + qmap.get("enddate");
      }
      if (qmap.containsKey("timeproperty") && qmap.get("timeproperty").toString().equals("早晚高峰")) {
        wsql += " and ((t.period >= 85 and t.period <=108) "
            + "or (t.period >= 211 and t.period <=234))";
      }
      if (qmap.containsKey("timeproperty") && qmap.get("timeproperty").toString().equals("早高峰")) {
        wsql += " and t.period >= 85 and t.period <=108";
      }
      if (qmap.containsKey("timeproperty") && qmap.get("timeproperty").toString().equals("晚高峰")) {
        wsql += " and t.period >= 211 and t.period <=234";
      }
      if (qmap.containsKey("startperiod") && !qmap.get("startperiod").toString().equals("0")) {
        wsql += " and t.period>=" + qmap.get("startperiod");
      }
      if (qmap.containsKey("endperiod") && !qmap.get("endperiod").toString().equals("0")) {
        wsql += " and t.period<=" + qmap.get("endperiod");
      }
      if (qmap.containsKey("dateproperty") && qmap.get("dateproperty").toString().equals("工作日")) {
        wsql += " and t1.workday = 1";
      }
      if (qmap.containsKey("dateproperty") && qmap.get("dateproperty").toString().equals("非工作日")) {
        wsql += " and t1.workday = 0";
      }
      if ("all".equals(qmap.get("districtId"))) {

        String sids = "b.sect10=0";
        for (int i = 0; i < permSects.length; i++) {
          sids += " or b.sect10=" + permSects[i];
        }
        wsql += " and (" + sids + ")";
      } else {
        wsql += " and b.sect10=" + qmap.get("districtId");
      }
    }
    return wsql;
  }

  /**
   * getLastDay.
   */
  public static int getLastDay(String time, int dayBefore) {
    Calendar c = Calendar.getInstance();
    Date date = null;
    try {
      date = new SimpleDateFormat("yyyyMMdd").parse(time);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    c.setTime(date);
    int day = c.get(Calendar.DATE);
    c.set(Calendar.DATE, day - dayBefore);

    String before = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
    return Integer.parseInt(before);
  }

  /**
   * 获取指定年月日期的前几个月年月日期.
   *
   * @param thisDate 指定日期 格式：201809
   * @param lastNum 几个月前的参数 1
   * @return 结果值
   */
  public static int getLastMonth(int thisDate, int lastNum) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
    Date date = null;
    try {
      date = sdf.parse(String.valueOf(thisDate));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.MONTH, -1);
    return Integer.parseInt(sdf.format(cal.getTime()));
  }

  /**
   * Clob类型 转String.
   */
  public static String clobToString(Clob clob) throws SQLException, IOException {
    String ret = "";
    Reader read = clob.getCharacterStream();
    BufferedReader br = new BufferedReader(read);
    String s = br.readLine();
    StringBuffer sb = new StringBuffer();
    while (s != null) {
      sb.append(s);
      s = br.readLine();
    }
    ret = sb.toString();
    if (br != null) {
      br.close();
    }
    if (read != null) {
      read.close();
    }
    return ret;
  }

  public static String setTpiValue(String value, int digit) {
    if (!value.contains(".")) {
      value = value + ".";
      for (int i = 0; i < digit; i++) {
        value = value + "0";
      }
    } else {
      int point = value.indexOf(".");
      while (value.length() - 1 - point < digit) {
        value = value + "0";
      }
      if (value.length() - 1 - point > digit) {
        value = value.substring(0, point + digit + 1);
      }
    }
    return value;
  }

  public static List<String> getMails(String mails) {
    return Arrays.asList(mails.split(","));
  }
}

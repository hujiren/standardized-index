package com.sutpc.its.mail;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailUtils {

  private static String host;

  private static String account;

  private static String password;

  private static String from;

  @Value("${mail.host}")
  public void setHost(String host) {
    MailUtils.host = host;
  }

  @Value("${mail.acct}")
  public void setAccount(String account) {
    MailUtils.account = account;
  }

  /**
   * 当使用pwd简称的时候，在linux环境下获取的结果是当项目部署的路径
   */
  @Value("${mail.password}")
  public void setPassword(String password) {
    MailUtils.password = password;
  }

  @Value("${mail.from}")
  public void setFrom(String from) {
    MailUtils.from = from;
  }


  public static boolean sendMailByWarn(List<String> emails, String context) {
    Object obj = GuavaManager.mailCache.getIfPresent(context);
    if (obj == null) {
      // 5分钟内没有发送过邮件
      GuavaManager.mailCache.put(context, 1);
      boolean flag = MailHelper
          .sendTextEmail(host, account, password, from, emails, "No Data Warning", context);
      return flag;
    }
    return false;
  }

  public static boolean sendMailByWarnTimeOut(List<String> emails, String context) {
    boolean flag = MailHelper
        .sendTextEmail(host, account, password, from, emails, "API request timeout", context);
    return flag;
  }

  public static boolean sendMailByWarnExceptption(List<String> emails, String context) {
    boolean flag = MailHelper
        .sendTextEmail(host, account, password, from, emails, "API request exception", context);
    return flag;
  }

}

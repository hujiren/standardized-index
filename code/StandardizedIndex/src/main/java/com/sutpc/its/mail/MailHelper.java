package com.sutpc.its.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

public class MailHelper {

  private final static Logger logger = LoggerFactory.getLogger(MailHelper.class);

  private static final int TYPE_TEXT = 1;
  private static final int TYPE_HTML = 2;

  public static boolean sendTextEmail(String host, String user, String password, String from,
      String to, String subject, String content) {
    List<String> toList = new ArrayList<String>(1);
    toList.add(to);
    return send(host, user, password, from, toList, subject, content, TYPE_TEXT);
  }

  public static boolean sendTextEmail(String host, String user, String password, String from,
      String[] to, String subject, String content) {
    return send(host, user, password, from, Arrays.asList(to), subject, content, TYPE_TEXT);
  }

  public static boolean sendTextEmail(String host, String user, String password, String from,
      List<String> toList, String subject, String content) {
    return send(host, user, password, from, toList, subject, content, TYPE_TEXT);
  }

  public static boolean sendHTMLEmail(String host, String user, String password, String from,
      String to, String subject, String content) {
    List<String> toList = new ArrayList<String>(1);
    toList.add(to);
    return send(host, user, password, from, toList, subject, content, TYPE_HTML);
  }

  public static boolean sendHTMLEmail(String host, String user, String password, String from,
      String[] to, String subject, String content) {
    return send(host, user, password, from, Arrays.asList(to), subject, content, TYPE_HTML);
  }

  public static boolean sendHTMLEmail(String host, String user, String password, String from,
      List<String> toList, String subject, String content) {
    return send(host, user, password, from, toList, subject, content, TYPE_HTML);
  }

  private static boolean send(String host, String user, String password, String from,
      List<String> toList, String subject, String content, int type) {
    Properties props = new Properties();

    props.put("mail.smtp.host", host); // ??????SMTP?????????
    props.put("mail.smtp.auth", "true"); // ??????????????????SMTP??????
    props.put("mail.smtp.ssl.enable", "true");
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.port", "465");

    boolean isOk = true;
    try {
      Session session = Session.getInstance(props, null);
      //session.setDebug(true); // ????????????????????????debug??????
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(from));// ?????????

      for (String to : toList) {// BCC??????  TO ??????
        message.addRecipient(Message.RecipientType.BCC, new InternetAddress(to));// ?????????
      }

      message.setSubject(subject);// ????????????
      if (type == TYPE_TEXT) {
        message.setText(content);
      } else if (type == TYPE_HTML) {
        message.setContent(content, "text/html; charset=utf-8");
      } else {
        message.setText(content);
      }
      message.setSentDate(new Date());
      message.saveChanges();

      Transport transport = session.getTransport("smtp");
      transport.connect(host, user, password);
      transport.sendMessage(message, message.getAllRecipients());
      transport.close();
      logger.info("??????????????????" + toList.toString());//?????????????????????info
    } catch (Exception e) {
      logger.debug("host" + host);
      logger.warn(e.getMessage(), e);
      isOk = false;
    }
    return isOk;
  }

}
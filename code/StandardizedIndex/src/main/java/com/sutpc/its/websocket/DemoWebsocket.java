package com.sutpc.its.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


@ServerEndpoint(value = "/wsHandler/demo"
)
@Component
public class DemoWebsocket {

  private Logger logger = LogManager.getLogger(DemoWebsocket.class);


  private static CopyOnWriteArraySet<DemoWebsocket> set = new CopyOnWriteArraySet<DemoWebsocket>();

  private Session session;
  //private String uuid;

  /**
   * open.
   */
  @OnOpen
  public void onOpen(
      //@PathParam("uuid") String uuid,
      Session session, EndpointConfig config) {
    this.session = session;

    try {
      // session.getBasicRemote().sendText("Connection Established");
      set.add(this);

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * When a user sends a message to the server, this method will intercept the message and allow us
   * to react to it. For now the message is read as a String.
   */
  @OnMessage
  public void onMessage(String message, Session session) {

  }

  /**
   * 处理二进制消息,参数随便搭配.
   */
  @OnMessage
  public void onMessage(Session session, byte[] message, boolean isLast) {
    //System.out.println(message.length);
  }


  static int count = 0;

  /**
   * 广播消息.
   */
  public static void sendMessageToAll(String message) {

    for (DemoWebsocket item : set) {

      try {
        item.session.getBasicRemote().sendText(message);
      } catch (IOException e) {

        e.printStackTrace();
      }

    }
  }


  /**
   * 发送消息.
   */
  public synchronized void sendMessage(String message) {
    try {
      // session.getBasicRemote() getAsyncRemote
      session.getBasicRemote().sendText(message);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * The user closes the connection.
   * <p></p>
   * Note: you can't send messages to the client from this method.
   */
  @OnClose
  public void onClose(Session session) {
    set.remove(this);

  }

  @OnError
  public void onError(Session session, Throwable error) {

    error.printStackTrace();
  }
}

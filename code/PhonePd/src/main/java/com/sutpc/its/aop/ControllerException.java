package com.sutpc.its.aop;


import com.sutpc.its.model.HttpResult;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerException {

  private Logger logger = LogManager.getLogger(ControllerException.class);

  @ResponseBody
  @ExceptionHandler(value = Exception.class)
  public HttpResult errorHandler(Exception ex) {

    logger.error(ex.getMessage() + getStackMsg(ex));

    return HttpResult.error("系统发生错误或接口使用错误");
  }

  private String getStackMsg(Exception e) {

    StringBuffer sb = new StringBuffer();
    StackTraceElement[] stackArray = e.getStackTrace();
    for (int i = 0; i < stackArray.length; i++) {
      StackTraceElement element = stackArray[i];
      sb.append(element.toString() + "\n");
    }
    return sb.toString();
  }
/*
	private String getStackMsg(Throwable e) {  
  
        StringBuffer sb = new StringBuffer();  
        StackTraceElement[] stackArray = e.getStackTrace();  
        for (int i = 0; i < stackArray.length; i++) {  
            StackTraceElement element = stackArray[i];  
            sb.append(element.toString() + "\n");  
        }  
        return sb.toString(); 
        
	}
*/
}

package com.sutpc.its.service;

import com.sutpc.its.dao.IDemoDao;
import com.sutpc.its.model.DemoModel;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class DemoService {

  @Autowired
  private IDemoDao demoDao;

  /*public List<Map<String,Object>> findObjects(){//Map<String,Object> map
    return demoDao.findObjects();
  }
  */

  /**
   * .
   */
  public List<DemoModel> findObjects(DemoModel map) {
    return demoDao.findObjects(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getBaseDistrictInfo(Map<String, Object> map) {
    return demoDao.getBaseDistrictInfo(map);
  }

  /**
   * .
   */
  public int saveObject(DemoModel dm) {

    return demoDao.saveObject(dm);
  }

  /**
   * .
   */
  public int updateObject(DemoModel dm) {

    return demoDao.updateObject(dm);
  }

  /**
   * .
   */
  @Async
  public void execAsyncFunc() {

    int s = (int) (1 + Math.random() * 10);

    try {
      Thread.sleep(s * 1000);
    } catch (InterruptedException e) {

      e.printStackTrace();
    }

    System.out.println("模拟执行了" + s + "秒");
  }


}

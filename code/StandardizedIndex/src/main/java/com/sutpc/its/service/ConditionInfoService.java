package com.sutpc.its.service;

import com.sutpc.its.dao.IConditionInfoDao;
import com.sutpc.its.dto.DelConditionInfoDto;
import com.sutpc.its.dto.QueryConditionInfoDto;
import com.sutpc.its.dto.SaveConditionInfoDto;
import com.sutpc.its.model.ConditionInfoEntity;
import com.sutpc.its.model.HttpResult;
import com.sutpc.its.po.ConditionInfoPo;
import com.sutpc.its.po.DelConditionInfoPo;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ConditionInfoService {

  @Autowired
  private IConditionInfoDao dao;

  /**
   * .
   */
  public List<ConditionInfoEntity> setConditionInfo(SaveConditionInfoDto conditionInfoDto)
      throws Exception {
    ConditionInfoPo po = new ConditionInfoPo();
    BeanUtils.copyProperties(conditionInfoDto, po);
    po.setId(UUID.randomUUID().toString().replace("-", ""));
    int status = dao.setConditionInfo(po);
    if (status == 1) {
      return dao.getConditionInfo(po);
    } else {
      throw new Exception("保存失败");
    }
  }

  /**
   * .
   */
  public List<ConditionInfoEntity> getConditionInfo(QueryConditionInfoDto queryConditionInfoDto) {
    ConditionInfoPo po = new ConditionInfoPo();
    BeanUtils.copyProperties(queryConditionInfoDto, po);
    return dao.getConditionInfo(po);
  }

  /**
   * .
   */
  public HttpResult<Object> delConditionInfo(DelConditionInfoDto dto) throws Exception {
    DelConditionInfoPo po = new DelConditionInfoPo();
    BeanUtils.copyProperties(dto, po);
    int status = dao.delConditionInfo(po);
    if (status == 1) {
      return HttpResult.ok();
    } else {
      throw new Exception("删除失败");
    }
  }
}

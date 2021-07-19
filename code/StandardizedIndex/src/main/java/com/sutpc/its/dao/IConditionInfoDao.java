package com.sutpc.its.dao;

import com.sutpc.its.model.ConditionInfoEntity;
import com.sutpc.its.po.ConditionInfoPo;
import com.sutpc.its.po.DelConditionInfoPo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 條件保存、查詢、修改等操作.
 *
 * @Author: zuotw
 * @Date: created on 15:58 2020/5/26.
 * @Description
 * @Modified By:
 */
@Mapper
@Repository
public interface IConditionInfoDao {

  /**
   * 保存条件信息.
   */
  int setConditionInfo(ConditionInfoPo po);


  List<ConditionInfoEntity> getConditionInfo(ConditionInfoPo conditionInfoPo);

  int delConditionInfo(DelConditionInfoPo po);

}

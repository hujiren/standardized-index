package com.sutpc.its.po;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 15:48 2020/8/26.
 * @Description
 * @Modified By:
 */
@Data
public class BlockInfoAllInfoPo {

  @ApiModelProperty(value = "恶化片区数")
  private int count;
  @ApiModelProperty(value = "所有片区信息")
  private List<BlockInfoPo> list;
  @ApiModelProperty(value = "十大拥堵片区")
  private List<BlockInfoPo> jamList;
}

package com.sutpc.its.dao;

import com.sutpc.its.statement.bean.ModuleTemplate;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 模块模板仓库.
 *
 * @author admin
 * @date 2020/5/20 19:08
 */
@Mapper
@Repository
public interface IModuleTemplateDao {

  /**
   * 获取模板信息.
   *
   * @param categoryId 区域id
   * @return 模板信息列表.
   */
  List<ModuleTemplate> getAll(@Param("categoryId") Integer categoryId);

  /**
   * 获取所有信息通过模板types.
   *
   * @param list types
   * @return 模板信息列表
   */
  List<ModuleTemplate> getAllByTypes(@Param("list") List<String> list);

}

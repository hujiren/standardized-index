package com.sutpc.its.service.v6.impl;

import com.sutpc.its.dao.v6.IIncidentDao;
import com.sutpc.its.service.v6.IIncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 10:19 2020/11/9.
 * @Description
 * @Modified By:
 */
@Service
public class IncidentServiceImpl implements IIncidentService {

  @Autowired
  private IIncidentDao dao;
}

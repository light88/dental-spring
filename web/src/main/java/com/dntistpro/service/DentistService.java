package com.dntistpro.service;

import com.dntistpro.web.request.legacy.DentistBean;
import com.dntistpro.persistence.entity.DentistEntity;

import java.util.List;

/**
 * Created by vrudyk on 27.05.2015.
 */
public interface DentistService extends BaseService<DentistEntity>{

  void update(DentistBean dentistBean, DentistEntity dentist);

  void save(DentistEntity dentist);

  List<DentistEntity> findAll();

  DentistEntity findOne(Long id);

}
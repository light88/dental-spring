package com.dental.persistence.repository;

import com.dental.persistence.entity.ForgotPasswordEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by vrudyk on 9/7/2016.
 */
@Repository
public interface ForgotPasswordRepository extends CrudRepository<ForgotPasswordEntity, Long> {
  ForgotPasswordEntity findByForgotPasswordKey(String forgotPasswordKey);
}

package com.dntistpro.persistence.repository;

import com.dntistpro.persistence.entity.DentistEntity;
import com.dntistpro.persistence.entity.PatientEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by admin on 27.05.2015.
 */
@Repository
public interface PatientRepository extends CrudRepository<PatientEntity, Long> {
  PatientEntity findByFirstNameAndLastName(String firstName, String lastName);
  List<PatientEntity> findByDentists(DentistEntity dentist);
  List<PatientEntity> findByDentistsOrderByCreatedOnDesc(DentistEntity dentist, Pageable page);
  List<PatientEntity> findAllByDentists(DentistEntity dentist);
  PatientEntity findOneByDentistsAndId(DentistEntity dentist, Long patientId);
  Long countByDentistsId(Long id);
}
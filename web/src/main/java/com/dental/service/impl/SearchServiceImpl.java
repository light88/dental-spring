package com.dental.service.impl;

import com.dental.helper.search.SearchStrategyFactory;
import com.dental.persistence.entity.DentistEntity;
import com.dental.persistence.entity.PatientEntity;
import com.dental.persistence.repository.SearchRepository;
import com.dental.service.AuthService;
import com.dental.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by vrudyk on 9/28/2016.
 */
@Service
public class SearchServiceImpl implements SearchService {

  @Autowired
  private SearchRepository searchRepository;

  @Autowired
  private AuthService authService;

  @Autowired
  private SearchStrategyFactory searchStrategyFactory;

  @Override
  public List<PatientEntity> findByOneName(DentistEntity dentist, String oneName) {
    return searchRepository.findByDentistAndOneName(dentist.getId(), oneName);
  }

  @Override
  public List<PatientEntity> findByFirstName(DentistEntity dentist, String firstName) {
    return searchRepository.findByDentistAndFirstNameLike(dentist.getId(), firstName);
  }

  @Override
  public List<PatientEntity> findByMiddleName(DentistEntity dentist, String middleName) {
    return searchRepository.findByDentistsAndMiddleNameLike(dentist.getId(), middleName);
  }

  @Override
  public List<PatientEntity> findByLastName(DentistEntity dentist, String lastName) {
    return searchRepository.findByDentistsAndLastNameLike(dentist.getId(), lastName);
  }

  @Override
  public List<PatientEntity> findByFirstNameOrLastName(DentistEntity dentist, String firstName, String lastName) {
    return searchRepository.findByDentistsAndFirstNameLikeOrLastNameLike(dentist.getId(), firstName, lastName);
  }

  @Override
  public List<PatientEntity> doSearch(String q) {
    return searchStrategyFactory.getSearchStrategy(q).invoke(authService.getLoggedInDentist());
  }
}
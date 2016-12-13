package com.dntistpro.web.rest;

import com.dntistpro.web.request.legacy.ToothCureRequestBean;
import com.dntistpro.init.LoggedDentist;
import com.dntistpro.persistence.entity.DentistEntity;
import com.dntistpro.persistence.entity.PatientEntity;
import com.dntistpro.persistence.entity.ToothEntity;
import com.dntistpro.persistence.entity.ToothCureEntity;
import com.dntistpro.service.AuthService;
import com.dntistpro.service.DentistService;
import com.dntistpro.service.PatientService;
import com.dntistpro.service.ToothService;
import com.dntistpro.web.dto.DTOUtils;
import com.dntistpro.web.dto.PatientDTO;
import com.dntistpro.web.dto.ToothCureDTO;
import com.dntistpro.web.dto.ToothDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * Created by light on 12/5/2015.
 */
@RestController
public class PatientRestController extends BaseRestController {

  @Autowired
  protected AuthService authService;

  @Autowired
  private DentistService dentistService;

  @Autowired
  private PatientService patientService;

  @Autowired
  private ToothService toothService;

  @RequestMapping(value = "/patients", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Set<PatientDTO>> getPatients(HttpServletRequest httpServletRequest,
                                                     @LoggedDentist DentistEntity loggedDentist, @PageableDefault(size = 20) Pageable page) {
    Set<PatientEntity> patients = patientService.findByDentist(loggedDentist, page);
    Set<PatientDTO> patientsDTO = DTOUtils.convert(patients);
    return new ResponseEntity<>(patientsDTO, HttpStatus.OK);
  }

  @RequestMapping(value = "/patients/{id}", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PatientDTO> getPatient(@PathVariable("id") Long patientId, @LoggedDentist DentistEntity loggedDentist) {
    PatientEntity patient = patientService.loadByDentistIdAndPatientId(loggedDentist.getId(), patientId);
    PatientDTO patientDTO = DTOUtils.convertDeep(patient);
    return success(patientDTO);
  }

  @RequestMapping(value = "/patients/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity putPatient(HttpServletRequest httpServletRequest, @RequestBody PatientDTO patientDTO) {

    DentistEntity loggedInDentist = authService.getLoggedInDentist();
    patientService.update(patientDTO, loggedInDentist);
    return success();
  }

  @RequestMapping(value = "/patients", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity patient(HttpServletRequest httpServletRequest, @RequestBody PatientDTO patientDTO) {

    PatientEntity patient = DTOUtils.convert(patientDTO);
    patient = patientService.add(patient);
    PatientDTO convert = DTOUtils.convert(patient);
    return success(convert);
  }

  @RequestMapping(value = "/patients/{patientId}/teeth/{toothId}/cures", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity getToothCures(@PathVariable("patientId") Long patientId, @PathVariable("toothId") Long toothId ) {
    ToothEntity tooth = toothService.load(toothId, patientId);
    ToothDTO toothDTO = DTOUtils.convertDeep(tooth);
    return success(toothDTO);
  }

  @RequestMapping(value = "/patients/{patientId}/teeth/{toothId}/cures", method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity addToothCure(HttpServletRequest httpServletRequest, @PathVariable("patientId") Long patientId,
                                     @PathVariable("toothId") Long toothId, @LoggedDentist DentistEntity loggedDentist,
                                     @RequestBody ToothCureRequestBean toothCureRequestBean)  {
    ToothCureEntity toothCure = new ToothCureEntity(toothCureRequestBean.getCure());
    toothCure = toothService.addCure(loggedDentist, patientId, toothId, toothCure);
    ToothCureDTO toothCureDTO = DTOUtils.convert(toothCure);
    return new ResponseEntity<>(toothCureDTO, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/patients/{patientId}/teeth/{toothId}/cures/{cureId}", method = RequestMethod.PUT,
    produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity toothCuresPut(HttpServletRequest httpServletRequest,
                                      @PathVariable("patientId") Long patientId,
                                      @PathVariable("toothId") Long toothId,
                                      @PathVariable("cureId") Long cureId,
                                      @LoggedDentist DentistEntity loggedDentist,
                                      @RequestBody ToothCureRequestBean toothCureRequestBean)  {
    toothService.updateCure(toothId, cureId, toothCureRequestBean.getCure());
    return success();
  }

}
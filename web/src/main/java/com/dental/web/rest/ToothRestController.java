package com.dental.web.rest;

import com.dental.bean.request.ToothCureRequestBean;
import com.dental.bean.request.ToothRequestBean;
import com.dental.bean.request.ToothStateBean;
import com.dental.exception.EntityNotFountException;
import com.dental.init.LoggedDentist;
import com.dental.persistence.entity.DentistEntity;
import com.dental.persistence.entity.PatientEntity;
import com.dental.persistence.entity.ToothCureEntity;
import com.dental.persistence.entity.ToothEntity;
import com.dental.service.AuthService;
import com.dental.service.DentistService;
import com.dental.service.PatientService;
import com.dental.service.ToothService;
import com.dental.web.dto.DTOUtils;
import com.dental.web.dto.ToothCureDTO;
import com.dental.web.dto.ToothDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by light on 4/7/2015.
 */
@RestController
public class ToothRestController extends BaseRestController {

  @Autowired
  protected AuthService authService;

  @Autowired
  private ToothService toothService;

  @Autowired
  private DentistService dentistService;

  @Autowired
  private PatientService patientService;

  @RequestMapping(value = "/tooth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ToothDTO> tooth(HttpServletRequest httpServletRequest, @LoggedDentist DentistEntity loggedDentist,
                                           ToothRequestBean toothRequestBean)  {
    ToothEntity tooth = toothService.get(toothRequestBean.getToothId(), toothRequestBean.getPatientId());
    ToothDTO toothDTO = DTOUtils.convert(tooth);
    return success(toothDTO);
  }

  @RequestMapping(value = "/tooth/cures", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ToothDTO> toothCures(HttpServletRequest httpServletRequest, @LoggedDentist DentistEntity loggedDentist,
                                             ToothRequestBean toothRequestBean)  {
    ToothEntity tooth = toothService.load(toothRequestBean.getToothId(), toothRequestBean.getPatientId());
    ToothDTO toothDTO = DTOUtils.convertDeep(tooth);
    return success(toothDTO);
  }

  @RequestMapping(value = "/tooth/cures", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ToothCureDTO> toothCuresPost(HttpServletRequest httpServletRequest, @LoggedDentist DentistEntity loggedDentist,
                                             @RequestBody ToothCureRequestBean toothCureRequestBean)  {
    ToothCureEntity toothCure = new ToothCureEntity(toothCureRequestBean.getCure());
    toothCure = toothService.addCure(toothCure, toothCureRequestBean.getToothId(), toothCureRequestBean.getPatientId());
    ToothCureDTO toothCureDTO = DTOUtils.convert(toothCure);
    return new ResponseEntity<>(toothCureDTO, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/tooth/state", method = RequestMethod.PUT,
    produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> toothStatePut(@LoggedDentist DentistEntity loggedDentist, ToothStateBean toothStateBean)  {

    PatientEntity patient = patientService.findByDentistIdAndPatientId(loggedDentist.getId(), toothStateBean.getPatientId());
    if (patient == null) {
      throw new EntityNotFountException("Patient entity not found");
    }
    toothService.updateToothState(toothStateBean.getToothId(), toothStateBean.ToothState());
    return success();
  }
}
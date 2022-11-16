package com.bitsco.vks.sothuly.api;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.response.ResponseBody;
import com.bitsco.vks.sothuly.entities.ApParam;
import com.bitsco.vks.sothuly.request.ApParamRequest;
import com.bitsco.vks.sothuly.service.ApPramService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author phucnv
 * @create 4/8/2021 10:26 AM
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/dm/ApParam")
public class ApParamController {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.CONTROLLER);
    private final ApPramService apPramService;

    @Autowired
    public ApParamController(ApPramService apPramService) {
        this.apPramService = apPramService;
    }

    @PostMapping("/getParams")
    @Operation(description = "Get list Ap param")
    public ResponseEntity<Object> getParams(@RequestBody ApParamRequest apParamRequest) throws Exception {
        try {
            List<ApParam> lstApPram = apPramService.findApParamsByParamCodeAndStatus(apParamRequest.getCode());
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, lstApPram), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /dm/ApParam ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }
}

package com.bitsco.vks.sothuly.api;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.request.PageRequest;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.response.ResponseBody;
import com.bitsco.vks.sothuly.entities.ArrestDetentionInfo;
import com.bitsco.vks.sothuly.model.ArrestDetentionInfoDTO;
import com.bitsco.vks.sothuly.entities.ArrestSettlementDecision;
import com.bitsco.vks.sothuly.entities.Arrestee;
import com.bitsco.vks.sothuly.service.ArrestDetentionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author phucnv
 * @create 4/8/2021 10:26 AM
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/arrest-detention")
public class ArrestDetentionController {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.CONTROLLER);

    @Autowired
    private ArrestDetentionService arrestDetentionService;

    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Không tìm thấy dữ liệu cần tra cứu", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @Operation(description = "Create ArrestDetention")
    @PostMapping
    public ResponseEntity<?> saveArrestDetention(@RequestBody ArrestDetentionInfo detentionInfo) throws Exception{
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, this.arrestDetentionService.createArrestDetention(detentionInfo)), HttpStatus.OK);
        }catch (CommonException e){
            return new ResponseEntity<>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        }catch (Exception e){
            LOGGER.error("Exception when create arrest-detention ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Không tìm thấy dữ liệu cần tra cứu", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping("/getLstArresteeById")
    @Operation(description = "get list arrestee by arrest detention info id")
    public ResponseEntity<?> getLstArresteeById (@RequestBody ArrestDetentionInfoDTO detentionInfoDTO) {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, this.arrestDetentionService.getArrestDetentionInfoById(detentionInfoDTO.getId(), detentionInfoDTO.getSppId())), HttpStatus.OK);
        }catch (CommonException e){
            return new ResponseEntity(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when create arrest-detention ", e);
            return new ResponseEntity(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Không tìm thấy dữ liệu cần tra cứu", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping("/saveArrestSettlementDecision")
    @Operation(description = "Create ArrestSettlementDecision")
    public ResponseEntity<?> saveArrestSettlementDecision(@RequestBody ArrestSettlementDecision arrestSettlementDecision) {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS,this.arrestDetentionService.createSettlementDecision(arrestSettlementDecision)), HttpStatus.OK);
        } catch (CommonException e){
            return new ResponseEntity(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when create arrest-detention ", e);
            return new ResponseEntity(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Không tìm thấy dữ liệu cần tra cứu", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @PutMapping
    @Operation(description = "Update ArrestDetentionInfo")
    public ResponseEntity<?> updateArrestDetentionInfo(@Validated @RequestBody ArrestDetentionInfo arrestDetentionInfo,
                                                          @RequestParam("sppId") String sppId) {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, this.arrestDetentionService.updateArrestDetentionInfo(arrestDetentionInfo, sppId)), HttpStatus.OK);
        } catch (CommonException e){
            return new ResponseEntity(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when create arrest-detention ", e);
            return new ResponseEntity(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Không tìm thấy dữ liệu cần tra cứu", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping("/saveArrestee")
    @Operation(description = "save Arrestee")
    public ResponseEntity<?> saveArrestee(@RequestBody Arrestee arrestee) {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS,this.arrestDetentionService.saveArrestee(arrestee)), HttpStatus.OK);
        } catch (CommonException e){
            return new ResponseEntity(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when create arrest-detention ", e);
            return new ResponseEntity(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Không tìm thấy dữ liệu cần tra cứu", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping("/deleteArrestDetentionInfo")
    @Operation(description = "delete ArrestDetentionInfo")
    public ResponseEntity<?> deleteArrestDetentionInfo(@RequestBody ArrestDetentionInfoDTO detentionInfoDTO, @RequestParam String sppId) {
        try {
            this.arrestDetentionService.delete(detentionInfoDTO.getId(),sppId);
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS), HttpStatus.OK);
        } catch (CommonException e){
            return new ResponseEntity(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when create arrest-detention ", e);
            return new ResponseEntity(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Không tìm thấy dữ liệu cần tra cứu", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping("/search")
    @Operation(description = "Seach Arrest Detention Info")
    public ResponseEntity<?> search(@RequestBody PageRequest pageRequest) throws Exception {
        try{
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, arrestDetentionService.search(pageRequest)), HttpStatus.OK);
        }catch (CommonException e){
            return new ResponseEntity<>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        }catch (Exception e){
            LOGGER.error("Exception when arrest-detention search ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }
}

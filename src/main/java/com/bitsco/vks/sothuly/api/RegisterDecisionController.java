package com.bitsco.vks.sothuly.api;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.request.PageRequest;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.response.ResponseBody;
import com.bitsco.vks.sothuly.entities.RegisterDecision;
import com.bitsco.vks.sothuly.entities.RegisterDecisionNum;
import com.bitsco.vks.sothuly.model.CheckRegisterDecisionRequest;
import com.bitsco.vks.sothuly.request.RegisterDecisionRequest;
import com.bitsco.vks.sothuly.service.RegisterDecisionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "registerDecision")
public class RegisterDecisionController {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.CONTROLLER);

    @Autowired
    RegisterDecisionService registerDecisionService;

    @Operation(description = "Thêm mới cấp lệnh cho vụ án/bị can/tin báo")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Không tìm thấy dữ liệu cần tra cứu", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/createRegisterDecisionAccused/")
    public ResponseEntity<?> createRegisterDecisionAccused(@RequestBody RegisterDecision registerDecision) throws Exception {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, registerDecisionService.createRegisterDecisionAccused(registerDecision)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /registerDecision/createRegisterDecisionAccused/ ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Cập nhật cấp lệnh cho vụ án/bị can/tin báo")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Không tìm thấy dữ liệu cần tra cứu", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/updateRegisterDecisionAccused/")
    public ResponseEntity<?> updateRegisterDecisionAccused(@RequestBody RegisterDecision registerDecision) throws Exception {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, registerDecisionService.updateRegisterDecisionAccused(registerDecision)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /registerDecision/updateRegisterDecisionAccused/ ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Xóa cấp lệnh cho vụ án/bị can/tin báo")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Không tìm thấy dữ liệu cần tra cứu", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/deleteRegisterDecisionAccused/")
    public ResponseEntity<?> deleteRegisterDecisionAccused(@RequestBody RegisterDecision registerDecision) throws Exception {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, registerDecisionService.deleteRegisterDecisionAccused(registerDecision)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /registerDecision/deleteRegisterDecisionAccused/ ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Chi tiết bản ghi cấp lệnh cho vụ án/bị can/tin báo")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Không tìm thấy dữ liệu cần tra cứu", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/findRegisterDecisionAccusedById/")
    public ResponseEntity<?> findRegisterDecisionAccusedById(@RequestBody RegisterDecision registerDecision) throws Exception {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, registerDecisionService.findRegisterDecisionAccusedById(registerDecision)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /registerDecision/findRegisterDecisionAccusedById/ ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Truy vấn danh sách cấp lệnh cho vụ án/bị can/tin báo - Không phân trang")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Không tìm thấy dữ liệu cần tra cứu", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/getList/")
    public ResponseEntity<?> getList(@RequestBody RegisterDecisionRequest registerDecisionRequest) throws Exception {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, registerDecisionService.getList(registerDecisionRequest)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /registerDecision/getList/ ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Truy vấn danh sách cấp lệnh cho vụ án/bị can/tin báo - Có phân trang")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Không tìm thấy dữ liệu cần tra cứu", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/getPage/")
    public ResponseEntity<?> getPage(@RequestBody PageRequest pageRequest) throws Exception {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, registerDecisionService.getPage(pageRequest)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /registerDecision/getPage/ ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Truy vấn số quyết định dự kiến của quyết định")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Số quyết định không đúng thứ tự", content = @Content),
            @ApiResponse(responseCode = "0001", description = "Số quyết định đã được cấp trong ngày", content = @Content),
            @ApiResponse(responseCode = "0002", description = "Không tìm thấy thông tin viện kiểm sát trong Cache", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Đơn vị viện kiểm sát mà tài khoản trực thuộc là đơn vị phòng nhưng không xác định được đơn vị cấp cha", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/getDecisionNum/")
    public ResponseEntity<?> getDecisionNum(@RequestBody RegisterDecisionNum registerDecisionNum) throws Exception {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, registerDecisionService.getDecisionNum(registerDecisionNum)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /registerDecision/getDecisionNum/ ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Check cấp số lệnh (Phục vụ api xóa bị can, vụ án)")
    @CrossOrigin(origins = "*")
    @PostMapping("/checkRegisterDecision/")
    public ResponseEntity<?> getDecisionNum(@RequestBody CheckRegisterDecisionRequest request) throws Exception {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, registerDecisionService.checkRegisterDecision(request.getReq(), request.getType())), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /registerDecision/checkRegisterDecision/ ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }
}

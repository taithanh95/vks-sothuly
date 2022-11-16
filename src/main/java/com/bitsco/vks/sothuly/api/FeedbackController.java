package com.bitsco.vks.sothuly.api;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.response.ResponseBody;
import com.bitsco.vks.sothuly.entities.Feedback;
import com.bitsco.vks.sothuly.model.FeedbackDTO;
import com.bitsco.vks.sothuly.service.FeedbackService;
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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@CrossOrigin
@RestController
@RequestMapping(value = "feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.CONTROLLER);

    @Operation(description = "Tạo góp ý")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Không tìm thấy dữ liệu cần tra cứu", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content),
            @ApiResponse(responseCode = "0009", description = "Không tìm thấy thông tin người thực hiện nghiệp vụ", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/createOrUpdateFeedback/")
    public ResponseEntity<?> createFeedback(@Valid @NotNull @RequestBody Feedback feedback) throws Exception {
        try {
            return new ResponseEntity<>(new com.bitsco.vks.common.response.ResponseBody(Response.SUCCESS, this.feedbackService.createOrUpdate(feedback)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new com.bitsco.vks.common.response.ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when create feedback ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping("/getListFeedBack/")
    public ResponseEntity<?> getListFeedBack (@Valid @NotNull @RequestBody FeedbackDTO feedbackDTO) throws Exception {
        try{
            return new ResponseEntity<>(new com.bitsco.vks.common.response.ResponseBody(Response.SUCCESS, this.feedbackService.getList(feedbackDTO)), HttpStatus.OK);
        }catch (CommonException e) {
            return new ResponseEntity<>(new com.bitsco.vks.common.response.ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when get list feedback ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping("/deleteFeedBack/")
    public ResponseEntity<?> deleteFeedback (@Valid @NotNull @RequestBody Feedback feedback) throws Exception{
        try{
            return new ResponseEntity<>(new com.bitsco.vks.common.response.ResponseBody(Response.SUCCESS, this.feedbackService.deleteFeedBack(feedback)), HttpStatus.OK);

        }catch (CommonException e) {
            return new ResponseEntity<>(new com.bitsco.vks.common.response.ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when delete feedback ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }
    @PostMapping("/changeFeedBackApprove/")
    public ResponseEntity<?> changeFeedBackApprove (@Valid @NotNull @RequestBody FeedbackDTO feedbackDTO) throws Exception{
        try{
            return new ResponseEntity<>(new com.bitsco.vks.common.response.ResponseBody(Response.SUCCESS, this.feedbackService.changeApprove(feedbackDTO)), HttpStatus.OK);
        }catch (CommonException e) {
            return new ResponseEntity<>(new com.bitsco.vks.common.response.ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when delete feedback ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }
    @PostMapping("/getListFeedBackByCreatedBy/")
    public ResponseEntity<?> getListFeedBackByCreatedBy (@Valid @NotNull @RequestBody FeedbackDTO feedbackDTO) throws Exception{
        try{
            return new ResponseEntity<>(new com.bitsco.vks.common.response.ResponseBody(Response.SUCCESS, this.feedbackService.getListByCreatedBy(feedbackDTO)), HttpStatus.OK);
        }catch (CommonException e) {
            return new ResponseEntity<>(new com.bitsco.vks.common.response.ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when delete feedback ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }
}

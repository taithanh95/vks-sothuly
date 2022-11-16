package com.bitsco.vks.sothuly.api;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.request.PageRequest;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.response.ResponseBody;
import com.bitsco.vks.sothuly.entities.ReviewCase;
import com.bitsco.vks.sothuly.entities.ReviewCaseAccused;
import com.bitsco.vks.sothuly.entities.ReviewCaseRequest;
import com.bitsco.vks.sothuly.request.ReviewCaseAccusedRequest;
import com.bitsco.vks.sothuly.service.ReviewCaseService;
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
@RequestMapping(value = "reviewCase")
public class ReviewCaseController {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.CONTROLLER);

    @Autowired
    ReviewCaseService reviewCaseService;

    @Operation(description = "Thêm mới 1 bản ghi xem xét lại cho vụ án")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0001", description = "Bản án đã được xem xét lại", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/createReviewCase/")
    public ResponseEntity<?> createReviewCase(@RequestBody ReviewCase reviewCase) throws Exception {
        try {
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SUCCESS, reviewCaseService.createReviewCase(reviewCase)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<ResponseBody>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /reviewCase/createReviewCase/ ", e);
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Cập nhật 1 bản ghi xem xét lại cho vụ án")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/updateReviewCase/")
    public ResponseEntity<?> updateReviewCase(@RequestBody ReviewCase reviewCase) throws Exception {
        try {
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SUCCESS, reviewCaseService.updateReviewCase(reviewCase)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<ResponseBody>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /reviewCase/updateReviewCase/ ", e);
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Thêm mới 1 bản ghi bản án xem xét lại trong 1 bản ghi xem xét lại")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Không tìm thấy dữ liệu cần tra cứu", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/createReviewCaseAccused/")
    public ResponseEntity<?> createReviewCaseAccused(@RequestBody ReviewCaseAccused reviewCaseAccused) throws Exception {
        try {
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SUCCESS, reviewCaseService.createReviewCaseAccused(reviewCaseAccused)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<ResponseBody>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /reviewCase/createReviewCaseAccused/ ", e);
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Cập nhật bản ghi bản án xem xét lại")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0002", description = "Bản ghi cần cập nhật không tồn tại", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/updateReviewCaseAccused/")
    public ResponseEntity<?> updateReviewCaseAccused(@RequestBody ReviewCaseAccused reviewCaseAccused) throws Exception {
        try {
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SUCCESS, reviewCaseService.updateReviewCaseAccused(reviewCaseAccused)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<ResponseBody>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /reviewCase/updateReviewCaseAccused/ ", e);
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Thêm mới 1 bản ghi Yêu cầu, kiến nghị, đề nghị trong 1 bản ghi xem xét lại")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Không tìm thấy dữ liệu cần tra cứu", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/createReviewCaseRequest/")
    public ResponseEntity<?> createReviewCaseRequest(@RequestBody ReviewCaseRequest reviewCaseRequest) throws Exception {
        try {
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SUCCESS, reviewCaseService.createReviewCaseRequest(reviewCaseRequest)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<ResponseBody>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /reviewCase/createReviewCaseRequest/ ", e);
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Cập nhật bản ghi Yêu cầu, kiến nghị, đề nghị")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0002", description = "Bản ghi cần cập nhật không tồn tại", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/updateReviewCaseRequest/")
    public ResponseEntity<?> updateReviewCaseRequest(@RequestBody ReviewCaseRequest reviewCaseRequest) throws Exception {
        try {
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SUCCESS, reviewCaseService.updateReviewCaseRequest(reviewCaseRequest)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<ResponseBody>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /reviewCase/updateReviewCaseRequest/ ", e);
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Truy vấn thông tin chi tiết bản ghi xem xét lại theo vụ án")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0002", description = "Bản ghi cần cập nhật không tồn tại", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/findReviewCaseByCaseCode/")
    public ResponseEntity<?> findReviewCaseByCaseCode(@RequestBody ReviewCase reviewCase) throws Exception {
        try {
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SUCCESS, reviewCaseService.findReviewCaseByCaseCode(reviewCase)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<ResponseBody>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /reviewCase/findReviewCaseByCaseCode/ ", e);
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Truy vấn danh sách bản án xem xét lại cho các bị can - Không phân trang")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Không tìm thấy dữ liệu cần tra cứu", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/getListReviewCaseAccused/")
    public ResponseEntity<?> getListReviewCaseAccused(@RequestBody ReviewCaseAccusedRequest reviewCase) throws Exception {
        try {
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SUCCESS, reviewCaseService.getListReviewCaseAccused(reviewCase)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<ResponseBody>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /reviewCase/getListReviewCaseAccused/ ", e);
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Truy vấn danh sách bản án xem xét lại cho các bị can - Có phân trang")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0007", description = "Không tìm thấy dữ liệu cần tra cứu", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/getPageReviewCaseAccused/")
    public ResponseEntity<?> getPageReviewCaseAccused(@RequestBody PageRequest pageRequest) throws Exception {
        try {
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SUCCESS, reviewCaseService.getPageReviewCaseAccused(pageRequest)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<ResponseBody>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /reviewCase/getPageReviewCaseAccused/ ", e);
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Truy vấn chi tiết bản án xem xét lại cho 1 bị can trong 1 vụ án")
    @ApiResponses({
            @ApiResponse(responseCode = "0000", description = "Thành công", content = @Content),
            @ApiResponse(responseCode = "0006", description = "Thiếu dữ liệu đầu vào bắt buộc", content = @Content),
            @ApiResponse(responseCode = "0002", description = "Không tìm thấy bản ghi xem xét lại cho vụ án", content = @Content),
            @ApiResponse(responseCode = "9999", description = "Lỗi hệ thống", content = @Content)
    })
    @CrossOrigin(origins = "*")
    @PostMapping("/detailReviewCaseByAccused/")
    public ResponseEntity<?> detailReviewCaseByAccused(@RequestBody ReviewCaseAccused reviewCaseAccused) throws Exception {
        try {
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SUCCESS, reviewCaseService.detailReviewCaseByAccused(reviewCaseAccused)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<ResponseBody>(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when /reviewCase/detailReviewCaseByAccused/ ", e);
            return new ResponseEntity<ResponseBody>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }
}

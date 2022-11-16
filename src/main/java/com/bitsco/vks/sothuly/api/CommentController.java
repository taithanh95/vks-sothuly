package com.bitsco.vks.sothuly.api;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.response.ResponseBody;
import com.bitsco.vks.sothuly.entities.Comment;
import com.bitsco.vks.sothuly.entities.Feedback;
import com.bitsco.vks.sothuly.service.CommentService;
import com.bitsco.vks.sothuly.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@CrossOrigin
@RestController
@RequestMapping(value = "comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.CONTROLLER);


    @CrossOrigin(origins = "*")
    @PostMapping("/createOrUpdateComment/")
    public ResponseEntity<?> createOrUpdateComment(@RequestBody Comment comment) throws Exception {
        try {
            return new ResponseEntity<>(new com.bitsco.vks.common.response.ResponseBody(Response.SUCCESS, this.commentService.createOrupdate(comment)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new com.bitsco.vks.common.response.ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when create comment ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/getListByFeedBack/")
    public ResponseEntity<?> getListByFeedBack (@Valid @NotNull @RequestBody Comment comment) throws Exception {
        try{
            return new ResponseEntity<>(new com.bitsco.vks.common.response.ResponseBody(Response.SUCCESS, this.commentService.getListByFeedBackId(comment)), HttpStatus.OK);
        }catch (CommonException e) {
            return new ResponseEntity<>(new com.bitsco.vks.common.response.ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when get list comment ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/deleteComment/")
    public ResponseEntity<?> deleteComment(@RequestBody Comment comment) throws Exception {
        try {
            return new ResponseEntity<>(new com.bitsco.vks.common.response.ResponseBody(Response.SUCCESS, this.commentService.deleteComment(comment)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new com.bitsco.vks.common.response.ResponseBody(e.getResponse().getResponseCode(), e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception when delete comment ", e);
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }
}

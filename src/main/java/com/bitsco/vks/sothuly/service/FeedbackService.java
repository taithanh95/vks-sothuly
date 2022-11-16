package com.bitsco.vks.sothuly.service;

import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.sothuly.entities.Feedback;
import com.bitsco.vks.sothuly.model.FeedbackDTO;

import java.util.List;

public interface FeedbackService {
    Feedback createOrUpdate(Feedback feedback) throws Exception;

    List<Feedback> getList(FeedbackDTO feedbackDTO) throws Exception;
    List<Feedback> getListByCreatedBy(FeedbackDTO feedbackDTO) throws Exception;


    Feedback deleteFeedBack(Feedback feedback) throws Exception;

    FeedbackDTO changeApprove (FeedbackDTO feedbackDTO) throws Exception;
}

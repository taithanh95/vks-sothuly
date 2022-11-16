package com.bitsco.vks.sothuly.service;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.util.ArrayListCommon;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.NumberCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.common.validate.ValidateCommon;
import com.bitsco.vks.sothuly.cache.CacheService;
import com.bitsco.vks.sothuly.entities.Feedback;
import com.bitsco.vks.sothuly.model.FeedbackDTO;
import com.bitsco.vks.sothuly.model.User;
import com.bitsco.vks.sothuly.repository.FeedbackRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    CacheService cacheService;

    @Override
    @Transactional
    public Feedback createOrUpdate(Feedback feedback) throws Exception {
        boolean notCreated = NumberCommon.isNullOrZero(feedback.getId());
        if (notCreated) {
            feedback.setApprove(Constant.APPROVE_OBJECT.WAITING);
            return feedbackRepository.save(feedback);
        } else {
            Feedback old = feedbackRepository.findById(feedback.getId()).orElse(null);
            if (old == null)
                throw new CommonException(Response.DATA_NOT_FOUND, Response.DATA_NOT_FOUND.getResponseMessage());
            old.copyForm(feedback);
            return feedbackRepository.save(old);
        }
    }

    @Override
    public List<Feedback> getList(FeedbackDTO feedbackDTO) throws Exception {
        if(!Constant.ID_VKSNDTC.equals(feedbackDTO.getSppid())){
            feedbackDTO.setApprove(Constant.APPROVE_OBJECT.ACTIVE);
        }
        List<Feedback> feedbackList = feedbackRepository.getList(
                StringCommon.isNullOrBlank(feedbackDTO.getType()) ? null : feedbackDTO.getType(),
                StringCommon.isNullOrBlank(feedbackDTO.getSubType()) ? null : feedbackDTO.getSubType(),
                StringCommon.isNullOrBlank(feedbackDTO.getContent()) ? null : StringCommon.addLikeRightAndLeft(feedbackDTO.getContent()),
                StringCommon.isNullOrBlank(feedbackDTO.getApprove()) ? null : feedbackDTO.getApprove(),
                Constant.STATUS_OBJECT.ACTIVE,
                StringCommon.isNullOrBlank(feedbackDTO.getCreatedBy()) ? null : StringCommon.addLikeRightAndLeft(feedbackDTO.getCreatedBy()),
                feedbackDTO.getFromDate(),
                feedbackDTO.getToDate()
        );
        if (ArrayListCommon.isNullOrEmpty(feedbackList)) {
            throw new CommonException(Response.DATA_NOT_FOUND);
        }
        return feedbackList;
    }

    @Override
    public List<Feedback> getListByCreatedBy(FeedbackDTO feedbackDTO) throws Exception {

        List<Feedback> feedbackList = feedbackRepository.getList(
                StringCommon.isNullOrBlank(feedbackDTO.getType()) ? null : feedbackDTO.getType(),
                StringCommon.isNullOrBlank(feedbackDTO.getSubType()) ? null : feedbackDTO.getSubType(),
                StringCommon.isNullOrBlank(feedbackDTO.getContent()) ? null : StringCommon.addLikeRightAndLeft(feedbackDTO.getContent()),
                StringCommon.isNullOrBlank(feedbackDTO.getApprove()) ? null : feedbackDTO.getApprove(),
                Constant.STATUS_OBJECT.ACTIVE,
                StringCommon.isNullOrBlank(feedbackDTO.getCreatedBy()) ? null : StringCommon.addLikeRightAndLeft(feedbackDTO.getCreatedBy()),
                feedbackDTO.getFromDate(),
                feedbackDTO.getToDate()
        );
        if(ArrayListCommon.isNullOrEmpty(feedbackList)){
            throw new CommonException(Response.DATA_NOT_FOUND,Response.DATA_NOT_FOUND.getResponseMessage());
        }
        return feedbackList;
    }


    @Override
    public Feedback deleteFeedBack(Feedback feedback) throws Exception {
        ValidateCommon.validateNullObject(feedback.getId(), "id");
        Feedback updateFeedback = feedbackRepository.findById(feedback.getId()).orElse(null);
        if (updateFeedback == null) {
            throw new CommonException(Response.DATA_INVALID, Response.DATA_INVALID.getResponseMessage());
        }
        updateFeedback.setStatus(Constant.STATUS_OBJECT.INACTIVE);
        return feedbackRepository.save(updateFeedback);
    }

    @Override
    public FeedbackDTO changeApprove(FeedbackDTO feedbackDTO) throws Exception {
        if(StringCommon.isNullOrBlank(feedbackDTO.getApprove()) ||
                StringCommon.isNullOrBlank(feedbackDTO.getContentApprove()) ||
                StringCommon.isNullOrBlank(feedbackDTO.getApproveBy()) ||
                feedbackDTO.getId() == null ||
                StringCommon.isNullOrBlank(feedbackDTO.getSppid()) ){
            throw new CommonException(Response.DATA_INVALID, Response.DATA_INVALID.getResponseMessage());
        }
        if(!Constant.ID_VKSNDTC.equals(feedbackDTO.getSppid())){
            throw new CommonException(Response.UNAUTHORIZED);
        }
        feedbackDTO.setApproveAt(new Date());
        feedbackRepository.changeApprove(feedbackDTO.getApprove(),
                feedbackDTO.getContentApprove(),
                feedbackDTO.getApproveAt(),
                feedbackDTO.getApproveBy(),
                feedbackDTO.getId()
        );
        return feedbackDTO;
    }
}

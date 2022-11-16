package com.bitsco.vks.sothuly.service;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.util.ArrayListCommon;
import com.bitsco.vks.common.util.NumberCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.common.validate.ValidateCommon;
import com.bitsco.vks.sothuly.cache.CacheService;
import com.bitsco.vks.sothuly.entities.Comment;
import com.bitsco.vks.sothuly.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    CacheService cacheService;

    @Override
    public List<Comment> getListByFeedBackId(Comment comment) {
        List<Comment> commentList = commentRepository.getList(
                comment.getFeedback().getId(),
                Constant.STATUS_OBJECT.ACTIVE
        );
        if (ArrayListCommon.isNullOrEmpty(commentList)) {
            throw new CommonException(Response.DATA_NOT_FOUND);
        }
        return commentList;
    }
    @Override
    public Comment createOrupdate(Comment comment) {
        Boolean notCreated = NumberCommon.isNullOrZero(comment.getId());
        if (notCreated) {
            comment.setStatus(Constant.STATUS_OBJECT.ACTIVE);
            return commentRepository.save(comment);
        } else {
            ValidateCommon.validateNullObject(comment.getId(), "id");
            Comment old = commentRepository.findById(comment.getId()).orElse(null);
            if (old == null)
                throw new CommonException(Response.DATA_NOT_FOUND, Response.DATA_NOT_FOUND.getResponseMessage());
            old.copyForm(comment);
            return commentRepository.save(old);
        }
    }
    @Override
    public Comment deleteComment(Comment comment) {
        ValidateCommon.validateNullObject(comment.getId(), "id");
        ValidateCommon.validateNullObject(comment.getCreatedBy(), "createdBy");

        Comment updateComment = commentRepository.findByIdAndCreatedBy(comment.getId(),comment.getCreatedBy());
        if (updateComment == null) {
            throw new CommonException(Response.DATA_NOT_FOUND);
        }
        updateComment.setStatus(Constant.STATUS_OBJECT.INACTIVE);
        updateComment.setUpdatedBy(comment.getCreatedBy());
        return commentRepository.save(updateComment);
    }
}

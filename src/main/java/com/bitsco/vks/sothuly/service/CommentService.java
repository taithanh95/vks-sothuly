package com.bitsco.vks.sothuly.service;

import com.bitsco.vks.sothuly.entities.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getListByFeedBackId(Comment comment);
    Comment createOrupdate(Comment comment);
    Comment deleteComment(Comment comment);
}

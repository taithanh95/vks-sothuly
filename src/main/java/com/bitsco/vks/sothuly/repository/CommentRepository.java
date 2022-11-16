package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.sothuly.entities.Comment;
import com.bitsco.vks.sothuly.entities.Feedback;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT c from Comment c WHERE 1 = 1"
            + " AND((:feedbackId) IS NULL OR c.feedback.id = (:feedbackId) )"
            + " AND ((:status) IS NULL OR c.status = (:status) )"
            + " ORDER BY c.createdAt ASC "

    )
    List<Comment> getList (@Param("feedback") Long feedbackId,
                           @Param("status") Integer status);
    Comment findByIdAndCreatedBy(Long Id, String createdBy);

}

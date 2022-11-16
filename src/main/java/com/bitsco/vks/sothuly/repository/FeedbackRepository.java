package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.sothuly.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query(value = "SELECT f FROM Feedback f WHERE 1 = 1 "
            + "AND(:type IS NULL OR f.type = (:type) )"
            + "AND(:subType IS NULL OR f.subType = (:subType) )"
            + "AND(:content IS NULL OR UPPER( f.content) LIKE UPPER( :content) )"
            + "AND(:approve IS NULL OR f.approve = (:approve) )"
            + "AND(:status IS NULL OR f.status = (:status) )"
            + "AND(:createdBy IS NULL OR f.createdBy LIKE (:createdBy) )"
            + "AND(:fromDate IS NULL OR :toDate IS NULL OR f.createdAt between :fromDate AND :toDate) "
            + "ORDER BY f.createdAt DESC "

    )
    List<Feedback> getList(
            @Param("type") String type,
            @Param("subType") String subType,
            @Param("content") String content,
            @Param("approve") String approve,
            @Param("status") Integer status,
            @Param("createdBy") String createdBy,
            @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate

    );

    @Transactional
    @Modifying
    @Query(value = "UPDATE Feedback f SET f.approve = :approve, f.contentApprove = :contentApprove," +
            " f.approveAt= :approveAt, f.approveBy = :approveBy WHERE f.id = :id")
    void changeApprove(@Param("approve") String approve,
                       @Param("contentApprove") String contentApprove,
                       @Param("approveAt") Date approveAt,
                       @Param("approveBy") String approveBy,
                       @Param("id") Long id);

    Feedback findByIdAndCreatedBy(Long id, String createdBy);

}

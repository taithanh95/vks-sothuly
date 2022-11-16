package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.sothuly.entities.ReviewCaseRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ReviewCaseRequestRepository extends JpaRepository<ReviewCaseRequest, Long> {
    List<ReviewCaseRequest> findByReviewCaseIdAndStatus(Long reviewCaseId, Integer status);

    List<ReviewCaseRequest> findByReviewCaseIdAndAccusedCodeAndStatus(Long reviewCaseId, String accusedCode, Integer status);
}

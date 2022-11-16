package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.sothuly.entities.ReviewCaseAccused;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ReviewCaseAccusedRepository extends JpaRepository<ReviewCaseAccused, Long> {
    List<ReviewCaseAccused> findByReviewCaseIdAndStatus(Long reviewCaseId, Integer status);
    List<ReviewCaseAccused> findByReviewCaseIdAndAccusedCodeAndStatus(Long reviewCaseId, String accusedCode, Integer status);
}

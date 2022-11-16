package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.sothuly.entities.ReviewCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ReviewCaseRepository extends JpaRepository<ReviewCase, Long> {
    ReviewCase findFirstByCaseCodeAndStatus(String caseCode, Integer status);

    Boolean existsByCaseCodeAndStatus(String caseCode, Integer status);
}

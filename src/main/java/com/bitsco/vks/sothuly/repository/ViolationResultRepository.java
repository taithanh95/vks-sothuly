package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.sothuly.entities.ViolationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ViolationResultRepository extends JpaRepository<ViolationResult, Long> {
    List<ViolationResult> findByViolationLawIdAndStatus(Long violationLawId, Integer status);
}

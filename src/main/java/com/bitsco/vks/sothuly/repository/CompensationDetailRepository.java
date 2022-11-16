package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.sothuly.entities.CompensationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CompensationDetailRepository extends JpaRepository<CompensationDetail, Long> {
    List<CompensationDetail> findByCompensationIdAndStatus(Long compensationId, Integer status);
}

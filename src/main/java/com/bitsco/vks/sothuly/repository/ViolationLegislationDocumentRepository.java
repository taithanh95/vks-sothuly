package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.sothuly.entities.ViolationLegislationDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ViolationLegislationDocumentRepository extends JpaRepository<ViolationLegislationDocument, Long> {
    List<ViolationLegislationDocument> findByViolationLawIdAndStatus(Long violationLawId, Integer status);
}

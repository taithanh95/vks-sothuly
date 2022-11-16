package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.sothuly.entities.CompensationDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CompensationDocumentRepository extends JpaRepository<CompensationDocument, Long> {
    List<CompensationDocument> findByCompensationIdAndStatus(Long compensationId, Integer status);
}

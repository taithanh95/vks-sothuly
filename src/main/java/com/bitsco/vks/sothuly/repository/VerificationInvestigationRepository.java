package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.sothuly.entities.SettlementDecision;
import com.bitsco.vks.sothuly.entities.VerificationInvestigation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface VerificationInvestigationRepository extends JpaRepository<VerificationInvestigation, Long> {
    List<VerificationInvestigation> findByDenouncementIdAndStatus(Long denouncementId, int status);
}

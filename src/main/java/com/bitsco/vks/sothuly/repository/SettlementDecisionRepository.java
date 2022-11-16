package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.sothuly.entities.SettlementDecision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface SettlementDecisionRepository extends JpaRepository<SettlementDecision, Long> {
    List<SettlementDecision> findByDenouncementIdAndStatus(Long denouncementId, int status);
}

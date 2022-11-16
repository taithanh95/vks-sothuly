package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.sothuly.entities.ArrestSettlementDecision;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Author: LamPT
 * Company: Vissoft
 * Project: vis_project
 * Package: com.bitsco.vks.sothuly.repository
 * Create_date: 4/13/2021, 9:54 AM
 * Create with: Intellij IDEA
 */
public interface ArrestSettlementDecisionRepository extends JpaRepository<ArrestSettlementDecision, Long>,ArrestSettlementDecisionRepositoryCustom {
    List<ArrestSettlementDecision> findByArresteeId(Long id) throws Exception;
}

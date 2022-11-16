package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.sothuly.entities.RegisterDecision;
import com.bitsco.vks.sothuly.entities.RegisterDecisionNum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
import java.util.List;

@RepositoryRestResource
public interface RegisterDecisionRepository extends JpaRepository<RegisterDecision, Long> {

    Boolean existsAllByCaseCodeAndSppCodeAndDecisionCodeAndIssuesDateAndTypeAndStatus(
            String caseCode,
            String sppCode,
            String decisionCode,
            Date issuesDate,
            Integer type,
            Integer status
    );

    Boolean existsAllByAccusedCodeAndSppCodeAndDecisionCodeAndIssuesDateAndTypeAndStatus(
            String accusedCode,
            String sppCode,
            String decisionCode,
            Date issuesDate,
            Integer type,
            Integer status
    );

    Boolean existsAllByDenouncementIdAndSppCodeAndDecisionCodeAndIssuesDateAndTypeAndStatus(
            Long denouncementId,
            String sppCode,
            String decisionCode,
            Date issuesDate,
            Integer type,
            Integer status
    );

    RegisterDecision findFirstByCaseCodeAndSppCodeAndDecisionCodeAndIssuesDateAndTypeAndStatus(
            String caseCode,
            String sppCode,
            String decisionCode,
            Date issuesDate,
            Integer Type,
            Integer status
    );

    RegisterDecision findFirstByAccusedCodeAndSppCodeAndDecisionCodeAndIssuesDateAndTypeAndStatus(
            String accusedCode,
            String sppCode,
            String decisionCode,
            Date issuesDate,
            Integer Type,
            Integer status
    );

    RegisterDecision findFirstByDenouncementIdAndSppCodeAndDecisionCodeAndIssuesDateAndTypeAndStatus(
            Long denouncementId,
            String sppCode,
            String decisionCode,
            Date issuesDate,
            Integer Type,
            Integer status
    );

    List<RegisterDecision> findByAccusedCodeAndStatus(String accusedCode, Integer status);

    @Query(value = "SELECT a FROM RegisterDecision a WHERE 1 = 1 "
            + " AND a.decisionCode = :decisionCode "
            + " AND a.sppCode = :sppCode "
            + " AND a.issuesDate BETWEEN :beginOfYearIssuesDate and :endOfYearIssuesDate"
            + " AND a.type = :type"
            + " AND a.status = :status "
            + " AND a.decisionNumAuto = :decisionNumAuto ")
    RegisterDecision findDecisionNum(@Param("decisionCode") String decisionCode,
                                        @Param("sppCode") String sppCode,
                                        @Param("beginOfYearIssuesDate") Date beginOfYearIssuesDate,
                                        @Param("endOfYearIssuesDate") Date endOfYearIssuesDate,
                                        @Param("type") Integer type,
                                        @Param("status") Integer status,
                                        @Param("decisionNumAuto") Integer decisionNumAuto);

    Boolean existsAllByCaseCodeAndTypeAndStatus(
            String caseCode,
            Integer type,
            Integer status
    );
    Boolean existsAllByAccusedCodeAndTypeAndStatus(
            String accusedCode,
            Integer type,
            Integer status
    );
    Boolean existsAllByDenouncementIdAndTypeAndStatus(
            Long denouncementId,
            Integer type,
            Integer status
    );
}

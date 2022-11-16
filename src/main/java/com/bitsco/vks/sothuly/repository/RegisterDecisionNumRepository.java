package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.sothuly.entities.RegisterDecisionNum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface RegisterDecisionNumRepository extends JpaRepository<RegisterDecisionNum, Long> {
    @Query(value = "SELECT MAX(a.decisionNum) FROM RegisterDecisionNum a WHERE 1 = 1 "
            + " AND a.decisionCode = :decisionCode "
            + " AND a.sppCode = :sppCode "
            + " AND a.issuesDate BETWEEN :beginOfYearIssuesDate and :endOfYearIssuesDate"
            + " AND a.type = :type"
            + " AND a.status = :status ")
    Integer getDecisionNumForAccused(@Param("decisionCode") String decisionCode,
                                     @Param("sppCode") String sppCode,
                                     @Param("beginOfYearIssuesDate") Date beginOfYearIssuesDate,
                                     @Param("endOfYearIssuesDate") Date endOfYearIssuesDate,
                                     @Param("type") Integer type,
                                     @Param("status") Integer status);

    RegisterDecisionNum findFirstBySppCodeAndDecisionCodeAndDecisionNumAndCaseCodeAndStatus(String sppCode, String decisionCode, int decisionNum, String caseCode, int status);

    RegisterDecisionNum findFirstBySppCodeAndDecisionCodeAndDecisionNumAndAccusedCodeAndStatus(String sppCode, String decisionCode, int decisionNum, String accusedCode, int status);

    RegisterDecisionNum findFirstBySppCodeAndDecisionCodeAndDecisionNumAndDenouncementIdAndStatus(String sppCode, String decisionCode, int decisionNum, long denouncementId, int status);

    @Query(value = "SELECT a FROM RegisterDecisionNum a WHERE 1 = 1 "
            + " AND a.decisionCode = :decisionCode "
            + " AND a.sppCode = :sppCode "
            + " AND a.issuesDate BETWEEN :beginOfYearIssuesDate and :endOfYearIssuesDate"
            + " AND a.type = :type"
            + " AND a.status = :status "
            + " AND a.decisionNum = :decisionNum ")
    RegisterDecisionNum findDecisionNum(@Param("decisionCode") String decisionCode,
                                     @Param("sppCode") String sppCode,
                                     @Param("beginOfYearIssuesDate") Date beginOfYearIssuesDate,
                                     @Param("endOfYearIssuesDate") Date endOfYearIssuesDate,
                                     @Param("type") Integer type,
                                     @Param("status") Integer status,
                                     @Param("decisionNum") Integer decisionNum);
}

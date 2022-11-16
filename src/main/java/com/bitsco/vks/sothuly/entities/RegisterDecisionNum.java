package com.bitsco.vks.sothuly.entities;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = SoThuLyConstant.TABLE.REGISTER_DECISION_NUM)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterDecisionNum extends BaseEntity {
    @Column(name = "N_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SoThuLyConstant.SEQUENCE.SQ_REGISTER_DECISION_NUM)
    @SequenceGenerator(name = SoThuLyConstant.SEQUENCE.SQ_REGISTER_DECISION_NUM, sequenceName = SoThuLyConstant.SEQUENCE.SQ_REGISTER_DECISION_NUM, allocationSize = 1)
    private Long id;
    //Mã quyết định
    @Column(name = "S_DECISION_CODE", length = 50)
    private String decisionCode;
    //Mã viện kiểm sát
    @Column(name = "S_SPP_CODE", length = 50)
    private String sppCode;
    //Ngày cấp số quyết định
    @Column(name = "D_ISSUES_DATE")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date issuesDate;
    //Số quyết định
    @Column(name = "N_DECISION_NUM")
    private Integer decisionNum;
    //Loại cấp số (1 = VA, 2 = BA, 3 = TBTG)
    @Column(name = "N_TYPE")
    private Integer type;
    @Column(name = "S_CASE_CODE")
    private String caseCode;
    @Column(name = "S_ACCUSED_CODE")
    private String accusedCode;
    @Column(name = "N_DENOUNCEMENT_ID")
    private Long denouncementId;

    public RegisterDecisionNum() {
    }

    public RegisterDecisionNum(RegisterDecision registerDecision) {
        this.caseCode = registerDecision.getCaseCode();
        this.accusedCode = registerDecision.getAccusedCode();
        this.denouncementId = registerDecision.getDenouncementId();
        this.decisionCode = registerDecision.getDecisionCode();
        this.sppCode = registerDecision.getSppCode();
        this.issuesDate = registerDecision.getIssuesDate();
        this.decisionNum = registerDecision.getDecisionNum() == null ? 1 : registerDecision.getDecisionNum();
        this.type = registerDecision.getType();
        this.setStatus(Constant.STATUS_OBJECT.ACTIVE);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDecisionCode() {
        return decisionCode;
    }

    public void setDecisionCode(String decisionCode) {
        this.decisionCode = decisionCode;
    }

    public String getSppCode() {
        return sppCode;
    }

    public void setSppCode(String sppCode) {
        this.sppCode = sppCode;
    }

    public Date getIssuesDate() {
        return issuesDate;
    }

    public void setIssuesDate(Date issuesDate) {
        this.issuesDate = issuesDate;
    }

    public Integer getDecisionNum() {
        return decisionNum;
    }

    public void setDecisionNum(Integer decisionNum) {
        this.decisionNum = decisionNum;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode;
    }

    public String getAccusedCode() {
        return accusedCode;
    }

    public void setAccusedCode(String accusedCode) {
        this.accusedCode = accusedCode;
    }

    public Long getDenouncementId() {
        return denouncementId;
    }

    public void setDenouncementId(Long denouncementId) {
        this.denouncementId = denouncementId;
    }
}

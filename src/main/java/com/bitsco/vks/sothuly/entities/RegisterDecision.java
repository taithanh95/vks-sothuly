package com.bitsco.vks.sothuly.entities;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import com.bitsco.vks.sothuly.model.Accused;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = SoThuLyConstant.TABLE.REGISTER_DECISION)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterDecision extends BaseEntity {
    @Column(name = "N_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SoThuLyConstant.SEQUENCE.SQ_REGISTER_DECISION)
    @SequenceGenerator(name = SoThuLyConstant.SEQUENCE.SQ_REGISTER_DECISION, sequenceName = SoThuLyConstant.SEQUENCE.SQ_REGISTER_DECISION, allocationSize = 1)
    private Long id;
    //Mã vụ án
    @Column(name = "S_CASE_CODE", length = 50)
    private String caseCode;
    //Giai đoạn
    @Column(name = "S_STAGE")
    private String stage;
    //Mã bị can
    @Column(name = "S_ACCUSED_CODE", length = 50)
    private String accusedCode;
    //Mã tin báo
    @Column(name = "N_DENOUNCEMENT_ID")
    private Long denouncementId;
    //Mã quyết định
    @Column(name = "S_DECISION_CODE", length = 50)
    private String decisionCode;
    //Ngày cấp số quyết định
    @Column(name = "D_ISSUES_DATE")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date issuesDate;
    //Số quyết định do người dùng nhập
    @Column(name = "N_DECISION_NUM")
    private Integer decisionNum;
    //Số quyết định tự tăng
    @Column(name = "N_DECISION_NUM_AUTO")
    private Integer decisionNumAuto;
    //Ngày bắt đầu hiệu lực
    @Column(name = "D_FROM_DATE")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date fromDate;
    //Ngày kết thúc hiệu lực
    @Column(name = "D_TO_DATE")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date toDate;
    //Mã viện kiểm sát
    @Column(name = "S_SPP_CODE", length = 50)
    private String sppCode;
    //Mã tòa án
    @Column(name = "S_SPC_CODE", length = 50)
    private String spcCode;
    //Loại cấp lệnh: 1 Vụ án, 2 Bị can, 3 tin báo tố giác
    @Column(name = "n_type")
    private Integer type;
    //Mã điều luật
    @Column(name = "S_LAW_CODE", length = 50)
    private String lawCode;
    //điều luật
    @Column(name = "S_LAW_ID", length = 50)
    private String lawId;

    // Lý do xóa bản ghi
    @Column(name = "s_reason", length = 500)
    private String reason;

    // Ghi chú
    @Column(name = "s_note", length = 2000)
    private String note;

    // Theo thủ tục rút gọn
    @Column(name = "s_rutgon")
    private String rutgon;

    // Đơn vị cấp số lệnh/quyết định
    @Column(name = "S_SPPID")
    private String sppid;

    @Column(name = "S_SIGNER")
    private String signer;

    @Column(name = "S_POSITION")
    private String position;

    @Transient
    private String stageName;

    @Transient
    private String caseName;

    @Transient
    private String decisionName;

    @Transient
    private String accusedName;

    @Transient
    Accused firstAccused;

    @Transient
    private String denouncementCode;

    @Transient
    private String sReporter;

    public RegisterDecision copyFrom(RegisterDecision registerDecision) {
        if (!StringCommon.isNullOrBlank(registerDecision.getSppCode())) this.setSppCode(registerDecision.getSppCode());
        if (!StringCommon.isNullOrBlank(registerDecision.getDecisionCode())) this.setDecisionCode(registerDecision.getDecisionCode().trim());
        if (!StringCommon.isNullOrBlank(registerDecision.getLawCode())) this.setLawCode(registerDecision.getLawCode().trim());
        if (registerDecision.getIssuesDate() != null) this.setIssuesDate(registerDecision.getIssuesDate());
        this.setFromDate(registerDecision.getFromDate());
        this.setToDate(registerDecision.getToDate());
        if (!StringCommon.isNullOrBlank(registerDecision.getStage())) this.setStage(registerDecision.getStage());
        if (registerDecision.getDecisionNum() != null) this.setDecisionNumAuto(registerDecision.getDecisionNum());
        if (registerDecision.getStatus() != null) this.setStatus(registerDecision.getStatus());
        if (registerDecision.getReason() != null) this.setReason(registerDecision.getReason());
        if (registerDecision.getNote() != null) this.setNote(registerDecision.getNote());
        if (registerDecision.getRutgon() != null) this.setRutgon(registerDecision.getRutgon());
        if (registerDecision.getSppid() != null) this.setSppid(registerDecision.getSppid());
        if (registerDecision.getSigner() != null) this.setSigner(registerDecision.getSigner());
        if (registerDecision.getPosition() != null) this.setPosition(registerDecision.getPosition());
        return this;
    }
}

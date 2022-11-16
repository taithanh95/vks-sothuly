package com.bitsco.vks.sothuly.entities;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Where(clause = "n_status=1")
@Table(name = SoThuLyConstant.TABLE.COMPENSATION)
public class Compensation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SoThuLyConstant.SEQUENCE.SQ_COMPENSATION)
    @SequenceGenerator(name = SoThuLyConstant.SEQUENCE.SQ_COMPENSATION, sequenceName = SoThuLyConstant.SEQUENCE.SQ_COMPENSATION,
            allocationSize = 1)
    @Column(name = "n_id", nullable = false)
    //Mã bản ghi, tự sinh tăng dần
    private Long id;

    @Column(name = "d_compensation_date", nullable = false)
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    //Ngày nhận đơn
    private Date compensationDate;

    @Column(name = "d_petition_date")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    //Ngày của đơn yêu cầu
    private Date petitionDate;

    @Column(name = "s_claimant_name", length = 100)
    //Người yêu cầu bồi thường
    private String claimantName;

    @Column(name = "d_claimant_birthday")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    //Ngày sinh của người yêu cầu bồi thường
    private Date claimantBirthday;

    @Column(name = "s_claimant_address", length = 1000)
    //Địa chỉ người yêu cầu bồi thường
    private String claimantAddress;

    @Column(name = "n_claimant_cccd")
    private String claimantCccd;

    @Column(name = "s_damages_name", length = 100)
    //Người thiệt hại
    private String damagesName;

    @Column(name = "d_damages_birthday")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    //Ngày sinh của người thiệt hại
    private Date damagesBirthday;

    @Column(name = "s_damages_address", length = 1000)
    //Địa chỉ người thiệt hại
    private String damagesAddress;

    @Column(name = "s_damages_content", length = 4000)
    //Nội dung yêu cầu bồi thường
    private String damagesContent;

    @Column(name = "n_result_code")
    //Mã kết quả thực hiện: 1-Thuộc thẩm quyền giải quyết, 2-Chuyển VKS có thẩm quyền giải quyết , 3- Chuyển cơ quan khác, 4-Trả lại đơn, 5-Xử lý khác
    private Integer resultCode;

    @Column(name = "n_result_number")
    //Số xử lý
    private Integer resultNumber;

    @Column(name = "d_result_date")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    //Ngày xử lý
    private Date resultDate;

    @Column(name = "s_result_agency", length = 10)
    //Cơ quan xử lý
    private String resultAgency;

    @Column(name = "s_result_units_id", length = 10)
    //Mã đơn vị xử lý
    private String resultUnitsId;

    @Column(name = "s_result_units_name", length = 100)
    //Tên đơn vị xử lý
    private String resultUnitsName;

    @Column(name = "s_result_handler", length = 100)
    //Người xử lý
    private String resultHandler;

    @Column(name = "s_result_position_handler", length = 100)
    //Chức vụ người xử lý
    private String resultPositionHandler;

    @Column(name = "s_note", length = 4000)
    //Ghi chú
    private String note;

    @Column(name = "d_decision_compensation_date")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    //Ngày thụ lý bản án/quyết định bồi thường
    private Date decisionCompensationDate;

    @Column(name = "n_decision_compensation_number")
    //Số bản án/quyết định bồi thường
    private Integer decisionCompensationNumber;

    @Column(name = "s_centence_compensation_sppid", length = 40)
    //Đơn vị ra bản án
    private String centenceCompensationSppid;

    @Column(name = "s_level_compensation_sppid", length = 10)
    //Cấp ra bản án
    private String levelCompensation;

    @Column(name = "d_decision_compensation_indate")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    //Ngày ra bản án/quyết định bồi thường
    private Date decisionCompensationIndate;

    @Column(name = "n_detention_in_number_of_days")
    //Số ngày giam giữ
    private Integer detentionInNumberOfDays;

    @Column(name = "d_verification_from_date")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    //Ngày bắt đầu xác mình
    private Date verificationFromDate;

    @Column(name = "d_verification_to_date")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    //Ngày kết thúc xác mình
    private Date verificationToDate;

    @Column(name = "d_negotiate_from_date")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    //Ngày bắt đầu thương lượng
    private Date negotiateFromDate;

    @Column(name = "d_negotiate_to_date")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    //Ngày kết thúc thương lượng
    private Date negotiateToDate;

    @Column(name = "n_decision_enforcement_number")
    //Số quyết định giải quyết việc bồi thường
    private Integer decisionEnforcementNumber;

    @Column(name = "d_decision_enforcement_indate")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    //Ngày ra quyết định giải quyết việc bồi thường
    private Date decisionEnforcementIndate;

    @Column(name = "s_decision_enforcement_sppid", length = 40)
    //Đơn vị ra quyết định giải quyết việc bồi thường
    private String decisionEnforcementSppid;

    @Column(name = "s_decision_enforcement_signer", length = 100)
    //Người ký ra quyết định giải quyết việc bồi thường
    private String decisionEnforcementSigner;

    @Column(name = "s_decision_enforcement_position", length = 100)
    //Chức vụ người ký ra quyết định giải quyết việc bồi thường
    private String decisionEnforcementPosition;

    @Column(name = "s_decision_enforcement_content", length = 4000)
    //Nội dung giải quyết việc bồi thường
    private String decisionEnforcementContent;

    @Column(name = "n_judgment_compensation_number")
    //Số bản án giải quyết việc bồi thường
    private Integer judgmentCompensationNumber;

    @Column(name = "d_judgment_compensation_date")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    //Ngày ra bản án giải quyết việc bồi thường
    private Date judgmentCompensationDate;

    @Column(name = "s_judgment_compensation_content", length = 4000)
    //Nội dung bản án giải quyết việc bồi thường
    private String judgmentCompensationContent;

    @Column(name = "s_spp_id", nullable = false, length = 40)
    //Đơn vị thực hiện nhập bản ghi
    private String sppId;

    @Column(name = "n_indemnify_enforcement")
    //Quyết định giải quyết việc bồi thường - có - không
    private Boolean indemnifyEnforcement;

    @Column(name = "n_indemnify_enforcement_number")
    //Số tiền quyết định giải quyết bồi thường
    private BigDecimal indemnifyEnforcementNumber;

    @Column(name = "n_indemnify_compensation")
    //Bản án giải quyết việc bồi thường - có - không
    private Boolean indemnifyCompensation;

    @Column(name = "n_indemnify_compensation_number")
    //Số tiền bản án giải quyết bồi thường
    private BigDecimal indemnifyCompensationNumber;

    @Transient
    List<CompensationDetail> compensationDetailList;

    @Transient
    List<CompensationDocument> compensationDocumentList;

    @Transient
    List<CompensationDamage> CompensationDamagesList;

    public Compensation coppyFrom(Compensation compensation) {
        this.setCompensationDate(compensation.getCompensationDate());
        this.setPetitionDate(compensation.getPetitionDate());
        this.setClaimantName(compensation.getClaimantName());
        this.setClaimantBirthday(compensation.getClaimantBirthday());
        this.setClaimantAddress(compensation.getClaimantAddress());
        this.setClaimantCccd(compensation.getClaimantCccd());
        this.setDamagesName(compensation.getDamagesName());
        this.setDamagesBirthday(compensation.getDamagesBirthday());
        this.setDamagesAddress(compensation.getDamagesAddress());
        this.setDamagesContent(compensation.getDamagesContent());
        this.setResultCode(compensation.getResultCode());
        this.setResultDate(compensation.getResultDate());
        this.setResultNumber(compensation.getResultNumber());
        this.setResultAgency(compensation.getResultAgency());
        this.setResultUnitsId(compensation.getResultUnitsId());
        this.setResultUnitsName(compensation.getResultUnitsName());
        this.setResultHandler(compensation.getResultHandler());
        this.setResultPositionHandler(compensation.getResultPositionHandler());
        this.setNote(compensation.getNote());
        this.setCentenceCompensationSppid(compensation.getCentenceCompensationSppid());
        this.setLevelCompensation(compensation.getLevelCompensation());
        this.setDecisionCompensationDate(compensation.getDecisionCompensationDate());
        this.setDecisionCompensationNumber(compensation.getDecisionCompensationNumber());
        this.setDecisionCompensationIndate(compensation.getDecisionCompensationIndate());
        this.setDetentionInNumberOfDays(compensation.getDetentionInNumberOfDays());
        this.setVerificationFromDate(compensation.getVerificationFromDate());
        this.setVerificationToDate(compensation.getVerificationToDate());
        this.setNegotiateFromDate(compensation.getNegotiateFromDate());
        this.setNegotiateToDate(compensation.getNegotiateToDate());
        this.setDecisionEnforcementNumber(compensation.getDecisionEnforcementNumber());
        this.setDecisionEnforcementIndate(compensation.getDecisionEnforcementIndate());
        this.setDecisionEnforcementSppid(compensation.getDecisionEnforcementSppid());
        this.setDecisionEnforcementSigner(compensation.getDecisionEnforcementSigner());
        this.setDecisionEnforcementPosition(compensation.getDecisionEnforcementPosition());
        this.setDecisionEnforcementContent(compensation.getDecisionEnforcementContent());
        this.setJudgmentCompensationNumber(compensation.getJudgmentCompensationNumber());
        this.setJudgmentCompensationDate(compensation.getJudgmentCompensationDate());
        this.setJudgmentCompensationContent(compensation.getJudgmentCompensationContent());
        this.setIndemnifyEnforcement(compensation.getIndemnifyEnforcement());
        this.setIndemnifyEnforcementNumber(compensation.getIndemnifyEnforcementNumber());
        this.setIndemnifyCompensation(compensation.getIndemnifyCompensation());
        this.setIndemnifyCompensationNumber(compensation.getIndemnifyCompensationNumber());
        return this;
    }
}

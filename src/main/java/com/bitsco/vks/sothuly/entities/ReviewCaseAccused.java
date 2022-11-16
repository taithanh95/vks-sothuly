package com.bitsco.vks.sothuly.entities;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.StringCommon;
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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = SoThuLyConstant.TABLE.REVIEW_CASE_ACCUSED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewCaseAccused extends BaseEntity {
    @Column(name = "N_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SoThuLyConstant.SEQUENCE.SQ_REVIEW_CASE_ACCUSED)
    @SequenceGenerator(name = SoThuLyConstant.SEQUENCE.SQ_REVIEW_CASE_ACCUSED, sequenceName = SoThuLyConstant.SEQUENCE.SQ_REVIEW_CASE_ACCUSED, allocationSize = 1)
    private Long id;
    //ID của bản ghi xem xét lại
    @Column(name = "N_REVIEW_CASE_ID")
    private Long reviewCaseId;
    //Mã bị can
    @Column(name = "S_ACCUSED_CODE", length = 50)
    private String accusedCode;
    //Giai đoạn
    @Column(name = "S_STAGE")
    private String stage;
    //Mã bản án
    @Column(name = "S_JUDGMENT_CODE", length = 50)
    private String judgmentCode;
    //Bản án số
    @Column(name = "S_JUDGMENT_NUM", length = 50)
    private String judgmentNum;
    //Ngày ra bản án
    @Column(name = "D_JUDGMENT_DATE")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date judgmentDate;
    //Bản án số
    @Column(name = "S_JUDGMENT_CONTENT", length = 3000)
    private String judgmentContent;

    public ReviewCaseAccused coppyFrom(ReviewCaseAccused reviewCaseAccused) {
        if (!StringCommon.isNullOrBlank(reviewCaseAccused.getJudgmentCode()))
            this.setJudgmentCode(reviewCaseAccused.getJudgmentCode());
        if (!StringCommon.isNullOrBlank(reviewCaseAccused.getJudgmentNum()))
            this.setJudgmentNum(reviewCaseAccused.getJudgmentNum());
        if (!StringCommon.isNullOrBlank(reviewCaseAccused.getJudgmentContent()))
            this.setJudgmentContent(reviewCaseAccused.getJudgmentContent().trim());
        if (reviewCaseAccused.getJudgmentDate() != null) this.setJudgmentDate(reviewCaseAccused.getJudgmentDate());
        if (!StringCommon.isNullOrBlank(reviewCaseAccused.getStage())) this.setStage(reviewCaseAccused.getStage());
        if (reviewCaseAccused.getStatus() != null) this.setStatus(reviewCaseAccused.getStatus());
        return this;
    }
}

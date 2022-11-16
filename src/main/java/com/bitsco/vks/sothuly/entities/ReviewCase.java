package com.bitsco.vks.sothuly.entities;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import com.bitsco.vks.sothuly.model.Case;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = SoThuLyConstant.TABLE.REVIEW_CASE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewCase extends BaseEntity {
    @Column(name = "N_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SoThuLyConstant.SEQUENCE.SQ_REVIEW_CASE)
    @SequenceGenerator(name = SoThuLyConstant.SEQUENCE.SQ_REVIEW_CASE, sequenceName = SoThuLyConstant.SEQUENCE.SQ_REVIEW_CASE, allocationSize = 1)
    private Long id;
    //Mã vụ án
    @Column(name = "S_CASE_CODE", length = 50)
    private String caseCode;
    //Số kết luận
    @Column(name = "S_CONCLUSION_NUMBER", length = 50)
    private String conclusionNumber;
    //ID bản ghi nội dung kết luận
    @Column(name = "S_CONCLUSION_ID", length = 50)
    private String conclusionId;
    //Ngày kết luận
    @Column(name = "D_CONCLUSION_DATE")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date conclusionDate;
    //Mã ghi chú
    @Column(name = "S_NOTE", length = 3000)
    private String note;

    @Transient
    List<ReviewCaseAccused> reviewCaseAccusedList;

    @Transient
    List<ReviewCaseRequest> reviewCaseRequestList;

    @Transient
    Case ccase;

    public ReviewCase coppyFrom(ReviewCase reviewCase) {
        if (!StringCommon.isNullOrBlank(reviewCase.getConclusionNumber()))
            this.setConclusionNumber(reviewCase.getConclusionNumber());
        if (!StringCommon.isNullOrBlank(reviewCase.getConclusionId()))
            this.setConclusionId(reviewCase.getConclusionId());
        if (reviewCase.getConclusionDate() != null) this.setConclusionDate(reviewCase.getConclusionDate());
        if (reviewCase.getStatus() != null) this.setStatus(reviewCase.getStatus());
        return this;
    }
}

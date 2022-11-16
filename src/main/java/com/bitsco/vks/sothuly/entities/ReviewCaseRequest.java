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
@Table(name = SoThuLyConstant.TABLE.REVIEW_CASE_REQUEST)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewCaseRequest extends BaseEntity {
    @Column(name = "N_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SoThuLyConstant.SEQUENCE.SQ_REVIEW_CASE_REQUEST)
    @SequenceGenerator(name = SoThuLyConstant.SEQUENCE.SQ_REVIEW_CASE_REQUEST, sequenceName = SoThuLyConstant.SEQUENCE.SQ_REVIEW_CASE_REQUEST, allocationSize = 1)
    private Long id;
    //ID của bản ghi xem xét lại
    @Column(name = "N_REVIEW_CASE_ID")
    private Long reviewCaseId;
    //Mã bị can
    @Column(name = "S_ACCUSED_CODE", length = 50)
    private String accusedCode;
    //Số yêu cầu
    @Column(name = "S_REQUEST_NUM", length = 50)
    private String requestNum;
    //Ngày yêu cầu
    @Column(name = "D_REQUEST_DATE")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date requestDate;
    //Cơ quan ban hành
    @Column(name = "S_REQUEST_OFFICE", length = 200)
    private String requestOffice;

    public ReviewCaseRequest coppyFrom(ReviewCaseRequest reviewCase) {
        if (!StringCommon.isNullOrBlank(reviewCase.getRequestNum()))
            this.setRequestNum(reviewCase.getRequestNum());
        if (!StringCommon.isNullOrBlank(reviewCase.getRequestOffice()))
            this.setRequestOffice(reviewCase.getRequestOffice());
        if (reviewCase.getRequestDate() != null) this.setRequestDate(reviewCase.getRequestDate());
        if (reviewCase.getStatus() != null) this.setStatus(reviewCase.getStatus());
        return this;
    }
}

package com.bitsco.vks.sothuly.entities;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Where(clause = "n_status=1")
@Table(name = SoThuLyConstant.TABLE.VIOLATION_LEGISLATION_DOCUMENT)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ViolationLegislationDocument extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SoThuLyConstant.SEQUENCE.SQ_VIOLATION_LEGISLATION_DOCUMENT)
    @SequenceGenerator(name = SoThuLyConstant.SEQUENCE.SQ_VIOLATION_LEGISLATION_DOCUMENT, sequenceName = SoThuLyConstant.SEQUENCE.SQ_VIOLATION_LEGISLATION_DOCUMENT,
            allocationSize = 1)
    @Column(name = "n_id", nullable = false)
    private Long  id;

    @Column(name = "n_violation_law_id", nullable = false)
    //ID bản violation_law
    private Long  violationLawId;

    @Column(name = "n_document_code")
    //Văn bản ban hành: 1-Kháng nghị, 2-Kiến nghị, 3-Thông báo rút kinh nghiệm, 4-Yêu cầu, 99-Khác
    private Integer documentCode;

    @Column(name = "s_document_number")
    //Số văn bản
    private String documentNumber;

    @Column(name = "d_document_date")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    //Ngày ra văn bản ban hành
    private Date documentDate;

    @Column(name = "s_content")
    //Nội dung
    private String content;
}

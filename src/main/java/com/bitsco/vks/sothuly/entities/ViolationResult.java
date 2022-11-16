package com.bitsco.vks.sothuly.entities;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "n_status=1")
@Entity
@Table(name = SoThuLyConstant.TABLE.VIOLATION_RESULT)
public class ViolationResult extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = SoThuLyConstant.SEQUENCE.SQ_VIOLATION_RESULT)
    @SequenceGenerator(name = SoThuLyConstant.SEQUENCE.SQ_VIOLATION_RESULT,
            sequenceName = SoThuLyConstant.SEQUENCE.SQ_VIOLATION_RESULT,
            allocationSize = 1)
    @Column(name = "n_id", nullable = false)
    private Long  id;

    @Column(name = "n_violation_law_id", nullable = false)
    // Mã vi phạm
    private Long violationLawId;

    @Column(name = "s_result_number", length = 15)
    // Số kết quả
    private String resultNumber;

    @Column(name = "d_result_date")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    // Ngày ra kết quả
    private Date resultDate;

    @Column(name = "s_result_content", columnDefinition = "NVARCHAR2 (2000)")
    // Nội dung kết quả
    private String resultContent;

    @Column(name = "s_note", columnDefinition = "NVARCHAR2 (500)")
    // Ghi chú
    private String note;

    @Column(name = "s_chap_nhan", columnDefinition = "CHAR")
    private String chapNhan;

    @Column(name = "s_chap_nhan_mot_phan", columnDefinition = "CHAR")
    private String chapNhanMotPhan;

    @Column(name = "s_khong_chap_nhan", columnDefinition = "CHAR")
    private String khongChapNhan;

    @Column(name = "s_khong_chap_nhan_mot_phan", columnDefinition = "CHAR")
    private String khongChapNhanMotPhan;
}

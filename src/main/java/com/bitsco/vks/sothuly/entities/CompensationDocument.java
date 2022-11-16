package com.bitsco.vks.sothuly.entities;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = SoThuLyConstant.TABLE.COMPENSATION_DOCUMENT)
public class CompensationDocument extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SoThuLyConstant.SEQUENCE.SQ_COMPENSATION_DOCUMENT)
    @SequenceGenerator(name = SoThuLyConstant.SEQUENCE.SQ_COMPENSATION_DOCUMENT, sequenceName = SoThuLyConstant.SEQUENCE.SQ_COMPENSATION_DOCUMENT,
            allocationSize = 1)
    @Column(name = "n_id", nullable = false)
    //Mã bản ghi, tự sinh tăng dần
    private Long id;

    @Column(name = "n_compensation_id", nullable = false)
    //ID của bản ghi bồi thường
    private Long compensationId;

    @Column(name = "s_document_name", length = 1000)
    //Tên tài liệu
    private String documentName;

    @Column(name = "d_deadlines", nullable = false)
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    //Thời hạn bổ sung
    private Date deadlines;

    @Column(name = "n_finish")
    //Hoàn thành BSHSTL
    private Boolean finish;

    @Column(name = "s_note", length = 4000)
    //Ghi chú
    private String note;
}

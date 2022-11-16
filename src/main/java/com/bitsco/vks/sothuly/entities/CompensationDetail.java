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
@Table(name = SoThuLyConstant.TABLE.COMPENSATION_DETAIL)
public class CompensationDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SoThuLyConstant.SEQUENCE.SQ_COMPENSATION_DETAIL)
    @SequenceGenerator(name = SoThuLyConstant.SEQUENCE.SQ_COMPENSATION_DETAIL, sequenceName = SoThuLyConstant.SEQUENCE.SQ_COMPENSATION_DETAIL,
            allocationSize = 1)
    @Column(name = "n_id", nullable = false)
    //Mã bản ghi, tự sinh tăng dần
    private Long id;

    @Column(name = "n_compensation_id", nullable = false)
    //ID của bản ghi bồi thường
    private Long compensationId;

    @Column(name = "n_documentary_number")
    //Số công văn đề nghị cấp kinh phí
    private String documentaryNumber;

    @Column(name = "d_documentary_date")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    //Ngày công văn đề nghị cấp kinh phí
    private Date documentaryDate;

    @Column(name = "n_finance_number")
    //Số bộ tài chính cấp kinh phí
    private Integer financeNumber;

    @Column(name = "d_finance_date")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    //Ngày bộ tài chính cấp kinh phí
    private Date financeDate;

    @Column(name = "d_compensation_enforce_date")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    //Ngày chi trả tiền bồi thường
    private Date compensationEnforceDate;

    @Column(name = "d_restore_honor_date")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    //Ngày phục hồi danh dự
    private Date restoreHonorDate;

    @Column(name = "n_compensation_amount_temp")
    //Số tiền tạm bồi thường
    private Long compensationAmountTemp;

    @Column(name = "n_compensation_amount")
    //Số tiền phải bồi hoàn
    private Long compensationAmount;

    @Column(name = "s_note", length = 4000)
    //Ghi chú
    private String note;

    @Column(name = "s_sppid", length = 4000)
    //Đơn vị giải quyết bồi thường
    private String sppid;
}

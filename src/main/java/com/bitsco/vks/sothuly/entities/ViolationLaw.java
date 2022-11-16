package com.bitsco.vks.sothuly.entities;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Where(clause = "n_status=1")
@Table(name = SoThuLyConstant.TABLE.VIOLATION_LAW)
public class ViolationLaw extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SoThuLyConstant.SEQUENCE.SQ_VIOLATION_LAW)
    @SequenceGenerator(name = SoThuLyConstant.SEQUENCE.SQ_VIOLATION_LAW, sequenceName = SoThuLyConstant.SEQUENCE.SQ_VIOLATION_LAW,
            allocationSize = 1)
    @Column(name = "n_id", nullable = false)
    //Mã vi phạm, tự sinh tăng dần
    private Long id;

    @Column(name = "d_violation_date")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    //Ngày vi phạm
    private Date violationDate;

    @Column(name = "s_violated_agency")
    //Cơ quan vi phạm
    private String violatedAgency;

    @Column(name = "s_violated_units_id")
    //Mã đơn vị vi phạm
    private String violatedUnitsId;

    @Column(name = "s_violated_units_name")
    //Tên đơn vị vi phạm
    private String violatedUnitsName;

    @Column(name = "n_result_code")
    private Integer resultCode;

    @Column(name = "s_spp_id", nullable = false)
    //Đơn vị thực hiện nhập bản ghi
    private String sppId;

    @Transient
    List<ViolationLegislationDocument> violationLegislationDocumentList;

    @Transient
    List<ViolationResult> violationResultLists;

    public ViolationLaw copyFrom(ViolationLaw violationLaw) {
        this.setSppId(StringCommon.isNullOrBlank(violationLaw.getSppId()) ? this.getSppId() : violationLaw.getSppId());
        this.setViolationDate(violationLaw.getViolationDate() == null ? this.getViolationDate() : violationLaw.getViolationDate());
        return this;
    }
}

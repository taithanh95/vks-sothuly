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
@Table(name = SoThuLyConstant.TABLE.COMPENSATION_DAMAGE)
public class CompensationDamage extends BaseEntity{

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

    @Column(name = "s_damages_cccd")
    private String damagesCccd;
}

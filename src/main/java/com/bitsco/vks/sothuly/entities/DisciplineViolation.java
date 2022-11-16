package com.bitsco.vks.sothuly.entities;

import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author phucnv
 * @create 4/8/2021 11:44 AM
 */
@Entity
@Where(clause = "n_status = 1")
@Table(name = SoThuLyConstant.TABLE.ARREST_DETENTION_DISCIPLINE_VIOLATION)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DisciplineViolation extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "discipline_violation_id_gen")
    @SequenceGenerator(name = "discipline_violation_id_gen", sequenceName = SoThuLyConstant.SEQUENCE.SQ_ARREST_DETENTION_DISCIPLINE_VIOLATION,
             allocationSize = 1)
    @Column(name = "N_DISCIPLINE_VIOLATION_ID")
    private Long id;

    @Column(name = "D_VIOLATION_DATE")
    private Date violationDate;

    @Column(name = "N_ARRESTEE_ID")
    private Long arresteeId;

    @Column(name = "S_PUNISHMENT_TYPE")
    private String punishmentType;

    @Column(name = "S_VIOLATION_CONTENT")
    private String violationContent;

}

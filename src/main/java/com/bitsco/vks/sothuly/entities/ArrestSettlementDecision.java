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
 * @create 4/8/2021 11:37 AM
 */
@Entity
@Where(clause = "n_status = 1")
@Table(name = SoThuLyConstant.TABLE.ARREST_DETENTION_SETTLEMENT_DECISION)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ArrestSettlementDecision extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "settlement_decision_id_gen")
    @SequenceGenerator(name = "settlement_decision_id_gen", sequenceName = SoThuLyConstant.SEQUENCE.SQ_ARREST_DETENTION_SETTLEMENT_DECISION,
            allocationSize = 1)
    @Column(name = "N_SETTLEMENT_DECISION_ID")
    private Long id;

    @Column(name = "N_ARRESTEE_ID")
    private Long arresteeId;

    @Column(name = "S_DECISION_MAKING_AGENCY")
    private String decisionMakingAgency;

    @Column(name = "S_DECISION_MAKING_UNIT_ID")
    private String decisionMakingUnitId;

    @Column(name = "S_DECISION_MAKING_UNIT_NAME")
    private String decisionMakingUnitName;

    @Column(name = "S_DECISION_NUMBER")
    private String decisionNumber;

    @Column(name = "S_DECISION_ID")
    private String decisionId;

    @Column(name = "S_DECISION_NAME")
    private String decisionName;

    @Column(name = "D_DECISION_DATE")
    private Date decisionDate;

    @Column(name = "S_REASON")
    private String reason;

    @Column(name = "D_EFFECT_START_DATE")
    private Date effectStartDate;

    @Column(name = "D_EFFECT_END_DATE")
    private Date effectEndDate;

    @Column(name = "S_SIGNER")
    private String signer;

    @Column(name = "S_SIGNER_POSITION")
    private String singerPosition;

    @Column(name = "S_NOTE")
    private String note;

    @Column(name = "N_EXECUTE_ORDER")
    private Long executeOrder;

    @Column(name = "S_TYPE_DATE")
    private String typeDate;

    @Column(name = "N_DAY_MONTH_YEAR")
    private Long dayMonthYear;
}

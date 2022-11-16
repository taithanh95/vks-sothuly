package com.bitsco.vks.sothuly.entities;

import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import com.bitsco.vks.sothuly.model.Law;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @Author phucnv
 * @create 4/8/2021 10:39 AM
 */
@Entity
@Where(clause = "n_status = 1")
@Table(name = SoThuLyConstant.TABLE.ARREST_DETENTION_INFO)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ArrestDetentionInfo extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arrest_detention_info_id_gen")
    @SequenceGenerator(name = "arrest_detention_info_id_gen", sequenceName = SoThuLyConstant.SEQUENCE.SQ_ARREST_DETENTION_INFO,
            allocationSize = 1)
    @Column(name = "N_ARREST_DETENTION_INFO_ID")
    private Long id;

    @Column(name = "N_SHARE_INFO_LEVEL")
    private Integer shareInfoLevel;

    @Column(name = "N_ARREST_DETENTION_INFO_CODE")
    private Long code;

    @Column(name = "S_ARRESTING_UNIT_ID")
    private String arrestingUnitId;

    @Column(name = "S_ARRESTING_UNIT_NAME")
    private String arrestingUnitName;

    @Column(name = "D_PROCURACY_TAKEN_OVER_DATE")
    private Date procuracyTakenOverDate;

    @Column(name = "S_TAKEN_OVER_PROCURATOR_ID")
    private String takenOverProsecutorId;

    @Column(name = "S_TAKEN_OVER_PROCURATOR_NAME")
    private String takenOverProcuratorName;

    @Column(name = "S_PROCURATOR_ASSIGNMENT_DECISION_NUMBER")
    private String procuratorAssignmentDecisionNumber;

    @Column(name = "D_PROCURATOR_ASSIGNMENT_DATE")
    private Date procuratorAssignmentDate;

    @Column(name = "S_ARREST_CONTENT")
    private String arrestContent;

    @Column(name = "S_ARREST_ENACTMENT_ID")
    private String arrestEnactmentId;

    @Column(name = "S_ARREST_ENACTMENT_NAME")
    private String arrestEnactmentName;

    @Column(name = "S_LAW_CLAUSE_ID")
    private String lawClauseId;

    @Column(name = "S_LAW_CLAUSE_NAME")
    private String lawClauseName;

    @Column(name = "S_LAW_POINT_ID")
    private String lawPointId;

    @Column(name = "S_LAW_POINT_NAME")
    private String lawPointName;

    @Column(name = "SPP_ID")
    private String sppId;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "N_ARREST_DETENTION_INFO_ID", referencedColumnName = "N_ARREST_DETENTION_INFO_ID")
    private List<Arrestee> arrestees;

    @Transient
    private Law law;
}

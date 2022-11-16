package com.bitsco.vks.sothuly.entities;

import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @Author phucnv
 * @create 4/8/2021 11:29 AM
 */
@Entity
@Where(clause = "n_status = 1")
@Table(name = SoThuLyConstant.TABLE.ARREST_DETENTION_ARRESTEE)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Arrestee extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arrestee_id_gen")
    @SequenceGenerator(name = "arrestee_id_gen", sequenceName = SoThuLyConstant.SEQUENCE.SQ_ARREST_DETENTION_ARRESTEE,
             allocationSize = 1)
    @Column(name = "N_ARRESTEE_ID")
    private Long id;

    @Column(name = "N_ARREST_DETENTION_INFO_ID")
    private Long arrestDetentionInfoId;

    @Column(name = "S_ARREST_TYPE")
    private String arrestType;

    @Column(name = "S_CASE_ID")
    private String caseId;

    @Column(name = "S_CASE_NAME")
    private String caseName;

    @Column(name = "S_DEFENDANT_ID")
    private String defendantId;

    @Column(name = "S_DEFENDANT_NAME")
    private String defendantName;

    @Column(name = "D_ARREST_DATE")
    private Date arrestDate;

    @Column(name = "D_PROCURACY_HANDLING_DATE")
    private Date procuracyHandlingDate;

    @Column(name = "S_ARREST_REASON")
    private String arrestReason;

    @Column(name = "S_ARREST_VIOLATION")
    private String arrestViolation;

    @Column(name = "S_FULL_NAME")
    private String fullName;

    @Column(name = "D_DATE_OF_BIRTH")
    private Date dateOfBirth;

    @Column(name = "N_YEAR_OF_BIRTH")
    private Integer yearOfBirth;

    @Column(name = "S_JOB")
    private String job;

    @Column(name = "S_WORKPLACE")
    private String workplace;

    @Column(name = "S_ADDRESS")
    private String address;

    @Column(name = "N_IS_DEAD")
    private Boolean isDead;

    @Column(name = "D_DEATH_DATE")
    private Date deathDate;

    @Column(name = "N_CAUSE_OF_DEATH_ID")
    private String causeOfDeathId;

    @Column(name = "S_CAUSE_OF_DEATH_NAME")
    private String causeOfDeathName;

    @Column(name = "N_ESCAPED")
    private Boolean escaped;

    @Column(name = "D_ESCAPING_DATE")
    private Date escapingDate;

    @Column(name = "S_REASON_FOR_ESCAPING")
    private String reasonForEscaping;

    @Column(name = "D_RECAPTURE_DATE")
    private Date recaptureDate;

    @Column(name = "N_MOVE_TO_ANOTHER_PLACE")
    private Boolean moveToAnotherPlace;

    @Column(name = "D_MOVE_TO_ANOTHER_PLACE_DATE")
    private Date moveToAnotherPlaceDate;

    @Column(name = "N_ARRIVE_FROM_ANOTHER_PLACE")
    private Boolean arriveFromAnotherPlace;

    @Column(name = "D_ARRIVE_FROM_ANOTHER_PLACE_DATE")
    private Date arriveFromAnotherPlaceDate;

    @Column(name = "S_REASON")
    private String reason;

    @Column(name = "N_ARRESTEE_YEAR")
    private Integer arresteeYear;

    @Column(name = "N_ARRESTEE_MONTH")
    private Integer arresteeMonth;

    @Column(name = "N_ARRESTEE_DAY")
    private Integer arresteeDay;

    @Column(name = "S_ARRIVE_FROM_ANOTHER_PLACE")
    private String noiChuyenDi;

    @Column(name = "S_MOVE_TO_ANOTHER_PLACE")
    private String noiChuyenDen;

    @Column(name = "S_DETENTION_PLACE")
    private String detentionPlace;

    @Column(name = "N_CCCD")
    private String cccd;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "N_ARRESTEE_ID", referencedColumnName = "N_ARRESTEE_ID")
    private List<LawOffense> lawOffenses;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "N_ARRESTEE_ID", referencedColumnName = "N_ARRESTEE_ID")
    private List<DisciplineViolation> disciplineViolations;


    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "N_ARRESTEE_ID", referencedColumnName = "N_ARRESTEE_ID")
    private List<ArrestSettlementDecision> settlementDecisions;

}

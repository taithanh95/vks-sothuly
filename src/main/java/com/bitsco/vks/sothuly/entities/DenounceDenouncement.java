package com.bitsco.vks.sothuly.entities;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import com.bitsco.vks.sothuly.model.Case;
import com.bitsco.vks.sothuly.model.Law;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: anhnb
 * Date: 5/5/2021
 * Time: 8:31 AM
 */

@Getter
@Setter
@Entity
@Table(name = SoThuLyConstant.TABLE.DENOUNCE_DENOUNCEMENT)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DenounceDenouncement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SoThuLyConstant.SEQUENCE.DENOUNCEMENT_ID_SEQ)
    @SequenceGenerator(name = SoThuLyConstant.SEQUENCE.DENOUNCEMENT_ID_SEQ, sequenceName = SoThuLyConstant.SEQUENCE.DENOUNCEMENT_ID_SEQ, allocationSize = 1)
    @Column(name = "DENOUNCEMENT_ID")
    private Long id;

    @Column(name = "DENOUNCEMENT_CODE")
    private String denouncementCode;

    @Column(name = "TAKEN_OVER_AGENCY_CODE", length = 50)
    private String takenOverAgencyCode;

    @Column(name = "TAKEN_OVER_DATE")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date takenOverDate;

    @Column(name = "TAKEN_RESULT_DATE")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date takenResultDate;

    @Column(name = "SETTLEMENT_TERM")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date settlementTerm;

    @Column(name = "CRIME_REPORT_SOURCE", length = 50)
    private String crimeReportSource;

    @Column(name = "COMPLICATED_CIRCUMSTANCE")
    private Boolean complicatedCircumstance;

    @Column(name = "TAKEN_OVER_OFFICER", length = 200)
    private String takenOverOfficer;

    @Column(name = "OFFICER_POSITION", length = 100)
    private String officerPosition;

    @Column(name = "IA_HANDLING_UNIT", length = 100)
    private String iaHandlingUnit;

    @Column(name = "IA_HANDLING_OFFICER", length = 100)
    private String iaHandlingOfficer;

    @Column(name = "IA_ASSIGNMENT_DECISION_NUMBER", length = 100)
    private String iaAssignmentDecisionNumber;

    @Column(name = "IA_ASSIGNMENT_DATE")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date iaAssignmentDate;

    @Column(name = "P_HANDLING_NUMBER", length = 100)
    private String phandlingNumber;

    @Column(name = "P_HANDLING_DATE")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date phandlingDate;

    @Column(name = "P_HANDLING_PROSECUTOR", length = 200)
    private String phandlingProsecutor;

    @Column(name = "P_ASSIGNMENT_DECISION_NUMBER", length = 100)
    private String passignmentDecisionNumber;

    @Column(name = "P_ASSIGNMENT_DATE")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date passignmentDate;

    @Column(name = "R_REPORTER", length = 200)
    private String rreporter;

    @Column(name = "R_DATE_OF_BIRTH")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date rdateOfBirth;

    @Column(name = "R_YEAR_OF_BIRTH")
    private Integer ryearOfBirth;

    @Column(name = "R_ADDRESS", length = 500)
    private String raddress;

    @Column(name = "R_PHONE_NUMBER", length = 20)
    private String rphoneNumber;

    @Column(name = "R_DELATION", length = 3000)
    private String rdelation;

    @Column(name = "R_NOTE", length = 1000)
    private String rnote;

    @Column(name = "R_CCCD")
    private String rcccd;

    @Column(name = "IPN_SETTLEMENT_AGENCY", length = 100)
    private String ipnSettlementAgency;

    @Column(name = "IPN_SETTLEMENT_UNIT", length = 100)
    private String ipnSettlementUnit;

    @Column(name = "IPN_CLASSIFIED_NEWS", length = 100)
    private String ipnClassifiedNews;

    @Column(name = "IPN_ENACTMENT", length = 1000)
    private String ipnEnactment;

    @Column(name = "IPN_LAW_CLAUSE", length = 100)
    private String ipnLawClause;

    @Column(name = "IPN_LAW_POINT", length = 100)
    private String ipnLawPoint;

    @Column(name = "FN_CODE", length = 50)
    private String fnCode;

    @Column(name = "FN_DATE")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date fnDate;

    @Column(name = "FN_TAKEN_OVER_AGENCY", length = 200)
    private String fnTakenOverAgency;

    @Column(name = "FN_TAKEN_OVER_UNIT", length = 20)
    private String fnTakenOverUnit;

    @Column(name = "CREATE_USER", length = 50)
    private String createUser;

    @Column(name = "CREATE_DATE")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE_TIME, timezone = "Asia/Ho_Chi_Minh")
    private Date createDate;

    @Column(name = "UPDATE_USER", length = 50)
    private String updateUser;

    @Column(name = "UPDATE_DATE")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE_TIME, timezone = "Asia/Ho_Chi_Minh")
    private Date updateDate;

    @Column(name = "STATUS")
    private Boolean status;

    @Column(name = "SPPID", length = 50)
    private String sppId;

    @Column(name = "SHARE_INFO_LEVEL")
    private Integer shareInfoLevel;

    @Column(name = "FN_NOTE", length = 3000)
    private String fnNote;

    @Column(name = "IPN_ENACTMENT_ID", length = 50)
    private String ipnEnactmentId;

    @Column(name = "SETTLEMENT_STATUS")
    private Integer settlementStatus;

    @Column(name = "IA_HANDLING_UNIT_ID", length = 20)
    private String iaHandlingUnitId;

    @Column(name = "IPN_SETTLEMENT_UNIT_ID", length = 20)
    private String ipnSettlementUnitId;

    @Column(name = "P_HANDLING_PROSECUTOR_ID", length = 200)
    private String phandlingProsecutorId;

    @Column(name = "CORRUPTION_CRIME")
    private Boolean corruptionCrime;

    @Column(name = "ECONOMIC_CRIME")
    private Boolean economicCrime;

    @Column(name = "OTHER_CRIME")
    private Boolean otherCrime;

    @Column(name = "s_denouncement_agency")
    //Cơ quan chuyển hồ sơ đến
    private String denouncementAgency;

    @Column(name = "s_denouncement_units_id")
    //Mã đơn vị chuyển hồ sơ đến
    private String denouncementUnitsId;

    @Column(name = "s_violated_units_name")
    //Tên đơn vị chuyển hồ sơ đến
    private String denouncementUnitsName;

    @Column(name = "DENOUNCEMENT_CODE_NEW")
    //Mã tin báo theo mã định danh
    private String denouncementCodeNew;

    @Column(name = "CASECODE")
    //Mã vụ án khi vụ án chọn tin báo liên quan
    private String casecode;

    @Column(name = "CASENAME")
    //Tên vụ án khi vụ án chọn tin báo liên quan
    private String casename;

    @Transient
    private List<Law> law;

    @Transient
    private List<DenounceDenouncedPerson> denounceDenouncedPersonList;

    @Transient
    private List<InvestigationActivity> investigationActivityList;

    @Transient
    private List<SettlementDecision> settlementDecisionList;

    @Transient
    private List<VerificationInvestigation> verificationInvestigationList;

    public DenounceDenouncement coppyFrom(DenounceDenouncement denouncement) {
        this.setDenouncementCode( denouncement.getDenouncementCode() );
        this.setTakenOverAgencyCode( denouncement.getTakenOverAgencyCode() );
        this.setTakenOverDate( denouncement.getTakenOverDate() );
        this.setTakenResultDate( denouncement.getTakenResultDate() );
        this.setSettlementTerm( denouncement.getSettlementTerm() );
        this.setCrimeReportSource( denouncement.getCrimeReportSource() );
        this.setComplicatedCircumstance( denouncement.getComplicatedCircumstance() );
        this.setTakenOverOfficer( denouncement.getTakenOverOfficer() );
        this.setOfficerPosition( denouncement.getOfficerPosition() );
        this.setIaHandlingUnitId( denouncement.getIaHandlingUnitId() );
        this.setIaHandlingUnit( denouncement.getIaHandlingUnit() );
        this.setIaHandlingOfficer( denouncement.getIaHandlingOfficer() );
        this.setIaAssignmentDecisionNumber( denouncement.getIaAssignmentDecisionNumber() );
        this.setIaAssignmentDate( denouncement.getIaAssignmentDate() );
        this.setPhandlingNumber( denouncement.getPhandlingNumber() );
        this.setPhandlingDate( denouncement.getPhandlingDate() );
        this.setPhandlingProsecutor( denouncement.getPhandlingProsecutor() );
        this.setPhandlingProsecutorId( denouncement.getPhandlingProsecutorId() );
        this.setPassignmentDecisionNumber( denouncement.getPassignmentDecisionNumber() );
        this.setPassignmentDate( denouncement.getPassignmentDate() );
        this.setRreporter( denouncement.getRreporter() );
        this.setRdateOfBirth( denouncement.getRdateOfBirth() );
        this.setRyearOfBirth( denouncement.getRyearOfBirth() );
        this.setRaddress( denouncement.getRaddress() );
        this.setRphoneNumber( denouncement.getRphoneNumber() );
        this.setRdelation( denouncement.getRdelation() );
        this.setRnote( denouncement.getRnote() );
        this.setRcccd( denouncement.getRcccd() );
        this.setIpnSettlementAgency( denouncement.getIpnSettlementAgency() );
        this.setIpnSettlementUnit( denouncement.getIpnSettlementUnit() );
        this.setIpnSettlementUnitId( denouncement.getIpnSettlementUnitId() );
        this.setIpnClassifiedNews( denouncement.getIpnClassifiedNews() );
        this.setIpnEnactment( denouncement.getIpnEnactment() );
        this.setIpnEnactmentId( denouncement.getIpnEnactmentId() );
        this.setIpnLawClause( denouncement.getIpnLawClause() );
        this.setIpnLawPoint( denouncement.getIpnLawPoint() );
        this.setFnCode( denouncement.getFnCode() );
        this.setFnDate( denouncement.getFnDate() );
        this.setFnNote( denouncement.getFnNote() );
        this.setFnTakenOverAgency( denouncement.getFnTakenOverAgency() );
        this.setFnTakenOverUnit( denouncement.getFnTakenOverUnit() );
        this.setCreateUser( denouncement.getCreateUser() );
        this.setCreateDate( denouncement.getCreateDate() );
        this.setUpdateUser( denouncement.getUpdateUser() );
        this.setUpdateDate( denouncement.getUpdateDate() );
        this.setStatus( denouncement.getStatus() );
        this.setSettlementStatus( denouncement.getSettlementStatus() );
        this.setSppId( denouncement.getSppId() );
        this.setShareInfoLevel( denouncement.getShareInfoLevel() );
        this.setCorruptionCrime( denouncement.getCorruptionCrime() );
        this.setEconomicCrime( denouncement.getEconomicCrime() );
        this.setOtherCrime( denouncement.getOtherCrime() );
        this.setSettlementDecisionList(denouncement.getSettlementDecisionList());
        this.setDenounceDenouncedPersonList(denouncement.getDenounceDenouncedPersonList());
        this.setInvestigationActivityList(denouncement.getInvestigationActivityList());
        this.setVerificationInvestigationList(denouncement.getVerificationInvestigationList());
        this.setDenouncementAgency(denouncement.getDenouncementAgency());
        this.setDenouncementUnitsId(denouncement.getDenouncementUnitsId());
        this.setDenouncementUnitsName(denouncement.getDenouncementUnitsName());
        this.setCasecode(denouncement.getCasecode());
        this.setCasename(denouncement.getCasename());
        return this;
    }
}

package com.bitsco.vks.sothuly.entities;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = SoThuLyConstant.TABLE.DENOUNCE_SETTLEMENT_DECISION)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SettlementDecision {
    @Id
    @Column(name = "settlement_decision_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "settlement_decision_id_gen")
    @SequenceGenerator(name = "settlement_decision_id_gen", sequenceName = "settlement_decision_id_seq", schema = "spp_report", allocationSize = 1)
    private Long id;

    @Column(name = "decision_number")
    private String decisionNumber;

    @Column(name = "decision_id")
    private String decisionId;

    @Column(name = "decision_name")
    private String decisionName;

    @Column(name = "description")
    private String description;

    @Column(name = "decision_date")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date decisionDate;

    @Column(name = "decision_making_agency")
    private String decisionMakingAgency;

    @Column(name = "decision_making_unit_id")
    private String decisionMakingUnitId;

    @Column(name = "decision_making_unit")
    private String decisionMakingUnit;

    @Column(name = "effect_start_date")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date effectStartDate;

    @Column(name = "effect_end_date")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date effectEndDate;

    @Column(name = "signer")
    private String signer;

    @Column(name = "position")
    private String position;

    @Column(name = "execute_order")
    private Long executeOrder;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "create_date")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE_TIME, timezone = "Asia/Ho_Chi_Minh")
    private Date createDate;

    @Column(name = "update_user")
    private String updateUser;

    @Column(name = "update_date")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE_TIME, timezone = "Asia/Ho_Chi_Minh")
    private Date updateDate;

    @Column(name = "status")
    private Integer status;

    @Column(name = "denouncement_id")
    private Long denouncementId;

    @Column(name = "request_Vks")
    private Boolean requestVks;

    public void updateRecordInfo(String username) {
        if (this.getId() != null) {
            this.setUpdateUser(username);
            this.setUpdateDate(new Date());
        } else {
            this.setCreateUser(username);
            this.setCreateDate(new Date());
        }
    }
}

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
@Table(name = SoThuLyConstant.TABLE.DENOUNCE_INVESTIGATION_ACTIVITY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvestigationActivity {
    @Id
    @Column(name = "investigation_activity_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "investigation_activity_id_gen")
    @SequenceGenerator(name = "investigation_activity_id_gen", sequenceName = "investigation_activity_id_seq", schema = "spp_report", allocationSize = 1)
    private Long id;

    @Column(name = "investigation_activity_type")
    private String investigationActivityType;

    @Column(name = "procuracy_participated")
    private Boolean procuracyParticipated;

    @Column(name = "execution_date")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date executionDate;

    @Column(name = "investigator")
    private String investigator;

    @Column(name = "participated_procurator")
    private String participatedProcurator;

    @Column(name = "participated_procurator_id")
    private String participatedProcuratorId;

    @Column(name = "reason_for_not_participating")
    private String reasonForNotParticipating;

    @Column(name = "assessment")
    private String assessment;

    @Column(name = "result")
    private String result;

    @Column(name = "process_type")
    private String processType;

    @Column(name = "note")
    private String note;

    @Column(name = "create_user")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE_TIME, timezone = "Asia/Ho_Chi_Minh")
    private String createUser;

    @Column(name = "create_date")
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

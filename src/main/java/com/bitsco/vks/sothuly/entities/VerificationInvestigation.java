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
@Table(name = SoThuLyConstant.TABLE.DENOUNCE_VERIFICATION_INVESTIGATION)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerificationInvestigation {
    @Id
    @Column(name = "verification_investigation_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "verification_investigation_id_gen")
    @SequenceGenerator(name = "verification_investigation_id_gen", sequenceName = "verification_investigation_id_seq", schema = "spp_report", allocationSize = 1)
    private Long id;

    @Column(name = "verification_investigation_code")
    private String verificationInvestigationCode;

    @Column(name = "verification_date")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date verificationDate;

    @Column(name = "procurators_request")
    private String procuratorsRequest;

    @Column(name = "procurators_request_id")
    private String procuratorsRequestId;

    @Column(name = "content_request")
    private String contentRequest;

    @Column(name = "result")
    private String result;

    @Column(name = "note")
    private String note;

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

    @Column(name = "type")
    private Long type;

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

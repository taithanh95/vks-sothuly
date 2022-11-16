package com.bitsco.vks.sothuly.entities;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: anhnb
 * Date: 5/5/2021
 * Time: 10:26 AM
 */
@Getter
@Setter
@Entity
@Table(name = SoThuLyConstant.TABLE.DENOUNCE_DENOUNCED_PERSON)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DenounceDenouncedPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SoThuLyConstant.SEQUENCE.DENOUNCED_PERSON_ID_SEQ)
    @SequenceGenerator(name = SoThuLyConstant.SEQUENCE.DENOUNCED_PERSON_ID_SEQ, sequenceName = SoThuLyConstant.SEQUENCE.DENOUNCED_PERSON_ID_SEQ, allocationSize = 1)
    @Column(name = "DENOUNCED_PERSON_ID")
    private Long id;

    @JoinColumn(name = "DENOUNCEMENT_ID")
    private Long denouncementId;

    @Column(name = "FULL_NAME", length = 200)
    private String fullName;

    @Column(name = "DATE_OF_BIRTH")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date dateOfBirth;

    @Column(name = "YEAR_OF_BIRTH")
    private Integer yearOfBirth;

    @Column(name = "JOB", length = 500)
    private String job;

    @Column(name = "WORKPLACE", length = 1000)
    private String workplace;

    @Column(name = "ADDRESS", length = 1000)
    private String address;

    @Column(name = "CREATE_USER", length = 50)
    private String createUser;

    @Column(name = "CREATE_DATE")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date createDate;

    @Column(name = "UPDATE_USER", length = 50)
    private String updateUser;

    @Column(name = "UPDATE_DATE")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date updateDate;

    @Column(name = "STATUS")
    private Boolean status;

    @Column(name = "CCCD")
    private String cccd;

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

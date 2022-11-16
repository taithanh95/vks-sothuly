package com.bitsco.vks.sothuly.model;

import com.bitsco.vks.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Accused{
    private String accuCode;
    private String caseCode;
    private String fullName;
    private String otherName;
    private String aliasName;
    private String address;
    private String locaId;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date birthDay;
    private String sex;
    private String identify;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date identifyD;
    private String identifyW;
    private String inParty;
    private String natiId;
    private String religion;
    private String heroin;
    private String repeater;
    private Integer conviction;
    private Integer offence;
    private String counId;
    private String occuId;
    private String levelId;
    private String officeId;
    private String partyId;
    private String crimWhere;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date crimDate;
    private String crimTime;
    private String wander;
    private Integer age;
    private String lawCode;
    private String g3Pnt;
    private String g4Pnt;
    private String beginSetnum;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE_TIME, timezone = "Asia/Ho_Chi_Minh")
    private Date beginIndate;
    private String beginSpp;
    private String beginSpc;
    private String beginPol;
    private String unoccupation;
    private String firstAcc;
    private String occuTeler;
    private String occuDishonest;
    private String occuReeducate;
    private String occuSoldier;
    private String occuStudent;
    private String occuOfficer;
    private String sppId;
    private String status;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date statusDate;
    private String lawId;
    private String addrName;
    private String locaName;
    private String bDay;
    private String bMonth;
    private String bYear;
    private String beginOffice;
    private String beginOfficeId;
    private Integer sync;
    private String legalper;
    private String baoChua;
    private String bcvnd;
    private String tgvpl;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE_TIME, timezone = "Asia/Ho_Chi_Minh")
    private Date lastTime;
    private Integer corruption;
    private Integer drug;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE_TIME, timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE_TIME, timezone = "Asia/Ho_Chi_Minh")
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;

    public Accused(String accuCode) {
        this.accuCode = accuCode;
    }
}

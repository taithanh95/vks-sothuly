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
public class Case {
    private String caseCode;
    private String caseName;
    private String sppId;
    private String address;
    private String caseType;
    private String remark;
    private String spcCaseCode;
    private String crimWhere;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date crimDate;
    private String crimTime;
    private String oriSppId;
    private String beginSetnum;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date beginIndate;
    private String beginSpp;
    private String beginSpc;
    private String beginPol;
    private String lawCode;
    private String firstAcc;
    private String alias;
    private String lawId;
    private String status;
    private String statusDate;
    private String crimDate1;
    private String crimWhere1;
    private String autoLaw;
    private String beginOffice;
    private String beginOfficeId;
    private Integer sync;
    private String caseIsnew;
    private String ghiHinh;
    private String dienThoai;
    private Integer knht;
    private Integer nhanDang;
    private Integer khamXet;
    private Integer kntt;
    private Integer nbgn;
    private Integer tndt;
    private Integer doiChat;
    private String ycKhoiTo;
    private Integer ttHoiCung;
    private Integer ttLkNbdDs;
    private Integer tgHoiCung;
    private Integer ttLkBbTg;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date lastTime;
    private Integer corruption;
    private Integer ttLkNlc;
    private Integer ttLkNbh;
    private Integer tgLk;
    private Integer ttLkNbbNtgNlcNbh;
    private Integer khamNghiemHienTruongKo;
    private Integer khamNghiemTuThiKo;
    private Integer doiChatKo;
    private Integer nhanDangKo;
    private Integer nhanBietGiongNoiKo;
    private Integer thucNghiemDieuTraKo;
    private Integer khamXetKo;
    private Integer drug;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE_TIME, timezone = "Asia/Ho_Chi_Minh")
    private Date fromDate;
    private Integer setTime;
    private Integer eSetTime;
    private String signName;
    private String signOffice;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date finishDate;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE_TIME, timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE_TIME, timezone = "Asia/Ho_Chi_Minh")
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;

    public Case(String caseCode) {
        this.caseCode = caseCode;
    }
}

package com.bitsco.vks.sothuly.request;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.ArrayListCommon;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: anhnb
 * Date: 5/4/2021
 * Time: 5:01 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DenouncedDenouncementRequest {
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date fromDate;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date toDate;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date phandlingFromDate;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date phandlingToDate;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date verificationFromDate;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date verificationToDate;
    private String phandlingNumber;
    private String phandlingProsecutorId;
    private String username;
    private String denouncementCode;
    private String crimeReportSource;
    private String reporter;
    private String takenOverOfficer;
    private String accusedName;
    private List<String> decisionIdList;
    private String sppId;
    private List<String> crimeReportSourceList;
    private List<String> listTakenOverAgency;
    private String ipnEnactmentId;
    private List<Integer> statusList;
    private String iaAssignmentDecisionNumber;
    private String iaHandlingOfficer;
    private String passignmentDecisionNumber;
    private String ipnSettlementAgency;
    private String ipnClassifiedNews;
    private Boolean corruptionCrime;
    private Boolean economicCrime;
    private Boolean otherCrime;
    private String verificationInvestigationCode;
    private String type;
    private String investigationActivityType;

//    public String getIpnEnactmentId() {
//        if (ArrayListCommon.isNullOrEmpty(ipnEnactmentIdList))
//            return null;
//        else {
//            String rs = ipnEnactmentIdList.get(0);
//            if (ipnEnactmentIdList.size() > 1)
//                for (int i = 1; i < ipnEnactmentIdList.size(); i++) {
//                    rs += ";" + ipnEnactmentIdList.get(i);
//                }
//            return rs;
//        }
//    }

    public String getDecisionId() {
        if (ArrayListCommon.isNullOrEmpty(decisionIdList))
            return null;
        else {
            String rs = decisionIdList.get(0);
            if (decisionIdList.size() > 1)
                for (int i = 1; i < decisionIdList.size(); i++) {
                    rs += ";" + decisionIdList.get(i);
                }
            return rs;
        }
    }

    public String getCrimeReportSourceList() {
        if (ArrayListCommon.isNullOrEmpty(crimeReportSourceList))
            return null;
        else {
            String rs = crimeReportSourceList.get(0);
            if (crimeReportSourceList.size() > 1)
                for (int i = 1; i < crimeReportSourceList.size(); i++) {
                    rs += ";" + crimeReportSourceList.get(i);
                }
            return rs;
        }
    }

    public String getListTakenOverAgency() {
        if (ArrayListCommon.isNullOrEmpty(listTakenOverAgency))
            return null;
        else {
            String rs = listTakenOverAgency.get(0);
            if (listTakenOverAgency.size() > 1)
                for (int i = 1; i < listTakenOverAgency.size(); i++) {
                    rs += ";" + listTakenOverAgency.get(i);
                }
            return rs;
        }
    }

    public String getStatusList() {
        if (ArrayListCommon.isNullOrEmpty(statusList))
            return null;
        else {
            String rs = statusList.get(0) + "";
            if (statusList.size() > 1)
                for (int i = 1; i < statusList.size(); i++) {
                    rs += ";" + statusList.get(i);
                }
            return rs;
        }
    }
}

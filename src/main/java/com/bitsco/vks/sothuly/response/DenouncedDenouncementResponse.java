package com.bitsco.vks.sothuly.response;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.sothuly.model.Law;
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
 * Time: 5:05 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DenouncedDenouncementResponse {
    private Integer stt;
    private Long id;
    private String denouncementCode;
    private String crimeReportSource;
    private String rReporter;
    private String rDelation;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date takenOverDate;
    private String fullName;
    private String nameAccused;
    private String decisionName;
    private Integer settlementStatus;
    private Integer shareInfoLevel;
    private String createUser;
    private String sppId;
    private String ipnEnactmentId;
    private List<Law> law;
    private String casecode;
    private String casename;
}

package com.bitsco.vks.sothuly.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArrestSettlementDecisionDTO {

    private Long id;

    private Long arresteeId;

    private String fullName;

    private String decisionMakingAgency;

    private String decisionMakingUnitId;

    private String decisionMakingUnitName;

    private String decisionNumber;

    private String decisionId;

    private String decisionName;

    private Date decisionDate;

    private String reason;

    private Date effectStartDate;

    private Date effectEndDate;

    private String signer;

    private String singerPosition;

    private String note;

    private String createUser;

    private Date createDate;

    private String updateUser;

    private Date updateDate;

    private Integer status;

    private Long executeOrder;
}

package com.bitsco.vks.sothuly.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArrestDetentionInfoDTO {

    private Long id;

    private Integer shareInfoLevel;

    private Integer code;

    private String arrestingUnitId;

    private String arrestingUnitName;

    private Date procuracyTakenOverDate;

    private String takenOverProsecutorId;

    private String takenOverProcuratorName;

    private String procuratorAssignmentDecisionNumber;

    private Date procuratorAssignmentDate;

    private String arrestContent;

    private String arrestEnactmentId;

    private String arrestEnactmentName;

    private String lawClauseId;

    private String lawClauseName;

    private String lawPointId;

    private String lawPointName;

    private String createUser;

    private Date createDate;

    private String updateUser;

    private Date updateDate;

    private Integer status;

    private String sppId;

    private List<@Valid ArresteeDTO> arrestees;

    private String arresteeName;

    private Long rownum;

    private Date fromDate;

    private Date toDate;

    private Long rowcount;

    private Integer rnum;

    private Integer pageSize;

    private Integer rowIndex;

    private Integer stt;

}

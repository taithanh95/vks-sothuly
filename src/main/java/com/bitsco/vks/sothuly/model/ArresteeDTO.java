package com.bitsco.vks.sothuly.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArresteeDTO {

    private Long id;

    private Long arrestDetentionInfoId;

    private String arrestType;

    private String caseId;

    private String caseName;

    private String defendantId;

    private String defendantName;

    private Date arrestDate;

    private Date procuracyHandlingDate;

    private String arrestReason;

    private String arrestViolation;

    private String fullName;

    private Date dateOfBirth;

    private Integer yearOfBirth;

    private String job;

    private String workplace;

    private String address;

    private Boolean isDead;

    private Date deathDate;

    private String causeOfDeathId;

    private String causeOfDeathName;

    private Boolean escaped;

    private Date escapingDate;

    private String reasonForEscaping;

    private Date recaptureDate;

    private Boolean moveToAnotherPlace;

    private Date moveToAnotherPlaceDate;

    private Boolean arriveFromAnotherPlace;

    private Date arriveFromAnotherPlaceDate;

    private String reason;

    private String createUser;

    private Date createDate;

    private String updateUser;

    private Date updateDate;

    private Integer status;

    private List<LawOffenseDTO> lawOffenses;

    private List<DisciplineViolationDTO> disciplineViolations;

    private List<ArrestSettlementDecisionDTO> arrestSettlementDecisions;

}

package com.bitsco.vks.sothuly.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchArresteeDTO {
    private Long id;
    private String defendantName;
    private Date arrestDate;
    private Date procuracyHandlingDate;
    private String arrestReason;
    private String arrestViolation;
    private String fullName;
    private Date dateOfBirth;
    private Integer yearOfBirth;
    private String job;
    private String workPlace;
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
    private String detentionPlace;
    private String arriveFromAnotherPlaceName;
    private String moveToAnotherPlaceName;
    private String arrestingUnitName;
}

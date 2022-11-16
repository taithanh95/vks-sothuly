package com.bitsco.vks.sothuly.request;

import com.bitsco.vks.common.request.BaseRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterDecisionRequest extends BaseRequest {
    private String sppCode;
    private String caseCode;
    private String caseName;
    private String accusedCode;
    private String accusedName;
    private String stage;
    private String decisionCode;
    private Integer type;
    private Integer denouncementId;
    private Integer denouncementType;
    private String reporterName;
    private String rutgon;
    private String sppid;
}

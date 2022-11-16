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
public class CompensationRequest  extends BaseRequest {
    private Long id;
    private String claimantName;
    private String damagesName;
    private Integer documentCode;
    private Integer resultCode;
    private String sppid;
}

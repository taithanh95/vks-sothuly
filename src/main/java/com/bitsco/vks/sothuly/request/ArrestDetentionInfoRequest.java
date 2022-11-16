package com.bitsco.vks.sothuly.request;

import com.bitsco.vks.common.request.BaseRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author phucnv
 * @create 4/13/2021 11:39 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArrestDetentionInfoRequest extends BaseRequest {
    private String arresteeName;
    private String sppId;
    private String codeDetention;
    private String decisionNumber;
}

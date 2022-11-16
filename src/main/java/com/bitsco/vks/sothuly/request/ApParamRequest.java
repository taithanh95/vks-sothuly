package com.bitsco.vks.sothuly.request;

import com.bitsco.vks.common.request.BaseRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApParamRequest extends BaseRequest {
    private String code;
}

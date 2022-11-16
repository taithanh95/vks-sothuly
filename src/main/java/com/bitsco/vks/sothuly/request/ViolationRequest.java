package com.bitsco.vks.sothuly.request;

import com.bitsco.vks.common.request.BaseRequest;
import com.bitsco.vks.common.util.ArrayListCommon;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ViolationRequest extends BaseRequest {
    private Long id;
    private String violatedAgency;
    private String violatedUnitsId;
    private Integer documentCode;
    private List<String> resultCodeList;
    private String sppid;

    public boolean checkChapNhan(String resultCode) {
        return resultCodeList.contains(resultCode);
    }
}

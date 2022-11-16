package com.bitsco.vks.sothuly.response;

import com.bitsco.vks.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ViolationResponse {
    private Long id;
    private String violatedUnitsId;
    private String violatedUnitsName;
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE, timezone = "Asia/Ho_Chi_Minh")
    private Date violationDate;
    private String documentName;
    private String resultName;
}

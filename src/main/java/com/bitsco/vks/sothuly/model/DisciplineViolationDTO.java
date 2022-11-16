package com.bitsco.vks.sothuly.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisciplineViolationDTO {

    private Long id;

    private Date violationDate;

    private Long arresteeId;

    private String punishmentType;

    private String violationContent;

    private String createUser;

    private Date createDate;

    private String updateUser;

    private Date updateDate;

    private Integer status;
}

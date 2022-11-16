package com.bitsco.vks.sothuly.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Tai Thanh <taithanh95.dev@gmail.com>
 * Date: 19/08/2022
 * Time: 11:55 AM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckRegisterDecisionRequest {
    private String req;
    private Integer type;
}

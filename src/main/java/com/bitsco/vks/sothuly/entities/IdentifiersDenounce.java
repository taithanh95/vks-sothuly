package com.bitsco.vks.sothuly.entities;

import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Tai Thanh <taithanh95.dev@gmail.com>
 * Date: 23/09/2022
 * Time: 9:02 AM
 */
@Getter
@Setter
@Entity
@Table(name = SoThuLyConstant.TABLE.IDENTIFIERS_DENOUNCE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdentifiersDenounce extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SoThuLyConstant.SEQUENCE.IDENTIFIERS_DENOUNCE_SEQ)
    @SequenceGenerator(name = SoThuLyConstant.SEQUENCE.IDENTIFIERS_DENOUNCE_SEQ, sequenceName = SoThuLyConstant.SEQUENCE.IDENTIFIERS_DENOUNCE_SEQ, allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "STT")
    private Long stt;

    @Column(name = "SPPNAME")
    private String sppName;

    @Column(name = "SPPID", length = 8)
    private String sppId;

    @Column(name = "IDENTIFIERS_CODE")
    private String identifiersCode;
}

package com.bitsco.vks.sothuly.entities;

import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * @Author phucnv
 * @create 4/8/2021 11:40 AM
 */
@Entity
@Where(clause = "n_status = 1")
@Table(name = SoThuLyConstant.TABLE.ARREST_DETENTION_LAW_OFFENSE)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class LawOffense extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "law_offense_id_gen")
    @SequenceGenerator(name = "law_offense_id_gen", sequenceName = SoThuLyConstant.SEQUENCE.SQ_ARREST_DETENTION_LAW_OFFENSE,
             allocationSize = 1)
    @Column(name = "N_LAW_OFFENSE_ID")
    private Long id;

    @Column(name = "S_LAW_ID")
    private String lawId;

    @Column(name = "N_ARRESTEE_ID")
    private Long arresteeId;

    @Column(name = "S_LAW_NAME")
    private String lawName;

    @Column(name = "S_ENACTMENT_ID")
    private String enactmentId;

    @Column(name = "S_ENACTMENT_NAME")
    private String enactmentName;

    @Column(name = "S_POINT")
    private String point;

    @Column(name = "S_ITEM")
    private String item;

}

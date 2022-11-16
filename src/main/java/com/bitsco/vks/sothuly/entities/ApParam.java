package com.bitsco.vks.sothuly.entities;

import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Where(clause = "status=1")
@Table(name = SoThuLyConstant.TABLE.AP_PARAM)
public class ApParam  {

    @Id
    @Column(name = "ap_param_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ap_param_gen")
    @SequenceGenerator(name = "ap_param_gen", sequenceName = "ap_param_seq",  allocationSize = 1)
    private Long id;

    @Column(name = "param_code")
    private String paramCode;

    @Column(name = "param_name")
    private String paramName;

    @Column(name = "param_value")
    private String paramValue;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_user")
    private String updateUser;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "status")
    private Integer status;

}

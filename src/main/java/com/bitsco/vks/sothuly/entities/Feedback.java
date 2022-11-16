package com.bitsco.vks.sothuly.entities;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Truong Nguyen
 * Date: 26-Sep-18
 * Time: 4:22 PM
 * To change this template use File | Settings | File Templates.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = SoThuLyConstant.TABLE.FEEDBACK)
public class Feedback extends BaseEntity {
    @Column(name = "N_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SoThuLyConstant.SEQUENCE.SQ_FEEDBACK)
    @SequenceGenerator(name = SoThuLyConstant.SEQUENCE.SQ_FEEDBACK, sequenceName = SoThuLyConstant.SEQUENCE.SQ_FEEDBACK, allocationSize = 1)
    private Long id;
    @Column(name = "s_type")
    private String type;
    @Column(name = "s_sub_type")
    private String subType;
    @Column(name = "c_content", columnDefinition = "CLOB")
    private String content;
    @Column(name = "s_approve")
    private String approve;
    @Column(name = "s_email")
    private String email;
    @Column(name = "s_phone_number")
    private String phoneNumber;
    @Column(name = "c_content_approve", columnDefinition = "CLOB")
    private String contentApprove;
    @Column(name = "d_approve_at")
    @JsonFormat(pattern = Constant.DATE.FORMAT.DATE_TIME, timezone = "Asia/Ho_Chi_Minh")
    private Date approveAt;
    @Column(name = "s_approve_by", length = 50)
    private String approveBy;
    @JsonManagedReference
    @OneToMany(mappedBy = "feedback", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;
    public void copyForm(Feedback feedback){
        if(!StringCommon.isNullOrBlank(feedback.getType()))
            this.setType(feedback.getType());
        if(!StringCommon.isNullOrBlank(feedback.getContent()))
            this.setContent(feedback.getContent());
        if(!StringCommon.isNullOrBlank(feedback.getPhoneNumber()))
            this.setPhoneNumber(feedback.getPhoneNumber());
        if(!StringCommon.isNullOrBlank(feedback.getEmail()))
            this.setEmail(feedback.getEmail());
        if(!StringCommon.isNullOrBlank(feedback.getSubType()))
            this.setSubType(feedback.getSubType());
    }
}

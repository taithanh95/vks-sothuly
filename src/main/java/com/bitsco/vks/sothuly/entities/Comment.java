package com.bitsco.vks.sothuly.entities;


import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SoThuLyConstant.TABLE.COMMENT)
public class Comment extends BaseEntity {
    @Column(name = "N_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SoThuLyConstant.SEQUENCE.SQ_COMMENT)
    @SequenceGenerator(name = SoThuLyConstant.SEQUENCE.SQ_COMMENT, sequenceName = SoThuLyConstant.SEQUENCE.SQ_COMMENT, allocationSize = 1)
    private Long id;
    @Column(name = "s_content", columnDefinition = "CLOB")
    private String content;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="n_id_feedback")
    private Feedback feedback;

    public void copyForm(Comment comment) {
        if (!StringCommon.isNullOrBlank(comment.getContent())) this.setContent(comment.getContent());
    }
}

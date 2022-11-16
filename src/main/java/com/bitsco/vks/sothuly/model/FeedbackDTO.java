package com.bitsco.vks.sothuly.model;

import com.bitsco.vks.sothuly.entities.Feedback;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO extends Feedback {
    Date fromDate;
    Date toDate;
    String sppid;
    String username;
    String sppName;
}

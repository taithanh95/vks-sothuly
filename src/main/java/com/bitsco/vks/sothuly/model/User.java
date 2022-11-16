package com.bitsco.vks.sothuly.model;

import com.bitsco.vks.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class User {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.DATE.FORMAT.DATE_TIME, timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.DATE.FORMAT.DATE_TIME, timezone = "Asia/Ho_Chi_Minh")
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String groupUserId;
    private Integer type;
    private Integer status;
    private Integer emailVerified;
    private String imageUrl;

    @JsonIgnore
    private String passwordDecode;
}

package com.example.music.model.request;

import lombok.Data;

import java.util.Date;

@Data
public class ConsumerRequest {
    private Integer id;

    private String username;

    private String oldPassword; // The user's old password will be used, so mapping is sufficient

    private String password;

    private Byte sex;

    private String phoneNum;

    private String email;

    private Date birth;

    private String introduction;

    private String location;

    private String avator;

    private Date createTime;
}

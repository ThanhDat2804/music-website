package com.example.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.music.common.R;
import com.example.music.model.domain.Admin;
import com.example.music.model.request.AdminRequest;
import jakarta.servlet.http.HttpSession;


public interface AdminService extends IService<Admin> {

    R verityPasswd(AdminRequest adminRequest, HttpSession session);
}

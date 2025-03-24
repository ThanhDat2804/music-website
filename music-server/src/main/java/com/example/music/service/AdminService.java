package com.example.music.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.music.common.Response;
import com.example.music.model.domain.Admin;
import com.example.music.model.request.AdminRequest;
import jakarta.servlet.http.HttpSession;

public interface AdminService extends IService<Admin> {

    Response verityPasswd(AdminRequest adminRequest, HttpSession session);
}

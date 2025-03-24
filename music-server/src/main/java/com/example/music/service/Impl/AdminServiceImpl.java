package com.example.music.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.music.common.Response;
import com.example.music.mapper.AdminMapper;
import com.example.music.model.domain.Admin;
import com.example.music.model.request.AdminRequest;
import com.example.music.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Response verityPasswd(AdminRequest adminRequest, HttpSession session) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", adminRequest.getUsername());
        queryWrapper.eq("password", adminRequest.getPassword());

        if (adminMapper.selectCount(queryWrapper) > 0) {
            session.setAttribute("name", adminRequest.getUsername());
            return Response.success("Login successful");
        } else {
            return Response.error("Incorrect username or password");
        }
    }
}
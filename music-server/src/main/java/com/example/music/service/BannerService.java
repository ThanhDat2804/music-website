package com.example.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.music.model.domain.Banner;

import java.util.List;


public interface BannerService extends IService<Banner> {

    List<Banner> getAllBanner();

}

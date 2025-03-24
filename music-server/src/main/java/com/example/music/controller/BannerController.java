package com.example.music.controller;

import com.example.music.common.R;
import com.example.music.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping("/getAllBanner")
    public R getAllBanner() {
        return R.success("Successfully retrieved banners", bannerService.getAllBanner());
    }
}

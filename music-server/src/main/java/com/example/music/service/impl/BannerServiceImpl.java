package com.example.music.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.music.mapper.BannerMapper;
import com.example.music.model.domain.Banner;
import com.example.music.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner>
        implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Cacheable(value = "banner", key = "'list'")  // Stored in cache; Redis uses key-value storage
    @Override
    public List<Banner> getAllBanner() {
        System.out.println("Cache not used");
        return bannerMapper.selectList(null);
    }
}

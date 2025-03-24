package com.example.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.music.common.R;
import com.example.music.mapper.CollectMapper;
import com.example.music.model.domain.Collect;
import com.example.music.model.request.CollectRequest;
import com.example.music.service.CollectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {

    @Autowired
    private CollectMapper collectMapper;

    @Override
    public R addCollection(CollectRequest addCollectRequest) {
        // The author uses 'type' to determine whether the collection is a song or a playlist
        Collect collect = new Collect();
        BeanUtils.copyProperties(addCollectRequest, collect);
        if (collectMapper.insert(collect) > 0) {
            return R.success("Collection added successfully", true);
        } else {
            return R.error("Failed to add collection");
        }
    }

    @Override
    public R existSongId(CollectRequest isCollectRequest) {
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", isCollectRequest.getUserId());
        queryWrapper.eq("song_id", isCollectRequest.getSongId());
        if (collectMapper.selectCount(queryWrapper) > 0) {
            return R.success("Already collected", true);
        } else {
            return R.success("Not collected", false);
        }
    }

    @Override
    public R deleteCollect(Integer userId, Integer songId) {
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("song_id", songId);
        if (collectMapper.delete(queryWrapper) > 0) {
            return R.success("Collection removed", false);
        } else {
            return R.error("Failed to remove collection");
        }
    }

    @Override
    public R collectionOfUser(Integer userId) {
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return R.success("User's collection", collectMapper.selectList(queryWrapper));
    }
}

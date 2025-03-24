package com.example.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.music.common.R;
import com.example.music.mapper.ListSongMapper;
import com.example.music.model.domain.ListSong;
import com.example.music.model.request.ListSongRequest;
import com.example.music.service.ListSongService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListSongServiceImpl extends ServiceImpl<ListSongMapper, ListSong> implements ListSongService {

    @Autowired
    private ListSongMapper listSongMapper;

    @Override
    public List<ListSong> allListSong() {
        return listSongMapper.selectList(null);
    }

    @Override
    public R updateListSongMsg(ListSongRequest updateListSongRequest) {
        ListSong listSong = new ListSong();
        BeanUtils.copyProperties(updateListSongRequest, listSong);
        return listSongMapper.updateById(listSong) > 0 ? R.success("Update successful") : R.error("Update failed");
    }

    @Override
    public R deleteListSong(Integer songId) {
        QueryWrapper<ListSong> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("song_id", songId);
        return listSongMapper.delete(queryWrapper) > 0 ? R.success("Delete successful") : R.error("Delete failed");
    }

    @Override
    public R addListSong(ListSongRequest addListSongRequest) {
        ListSong listSong = new ListSong();
        BeanUtils.copyProperties(addListSongRequest, listSong);
        return listSongMapper.insert(listSong) > 0 ? R.success("Add successful") : R.error("Add failed");
    }

    @Override
    public R listSongOfSongId(Integer songListId) {
        QueryWrapper<ListSong> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("song_list_id", songListId);
        return R.success("Query successful", listSongMapper.selectList(queryWrapper));
    }
}

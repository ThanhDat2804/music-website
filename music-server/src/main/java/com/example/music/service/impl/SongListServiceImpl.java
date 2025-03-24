package com.example.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.music.common.R;
import com.example.music.controller.MinioUploadController;
import com.example.music.mapper.SongListMapper;
import com.example.music.model.domain.SongList;
import com.example.music.model.request.SongListRequest;
import com.example.music.service.SongListService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class SongListServiceImpl extends ServiceImpl<SongListMapper, SongList> implements SongListService {

    @Autowired
    private SongListMapper songListMapper;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Override
    public R updateSongListMsg(SongListRequest updateSongListRequest) {
        SongList songList = new SongList();
        BeanUtils.copyProperties(updateSongListRequest, songList);
        return songListMapper.updateById(songList) > 0 ? R.success("Update successful") : R.error("Update failed");
    }

    @Override
    public R deleteSongList(Integer id) {
        return songListMapper.deleteById(id) > 0 ? R.success("Delete successful") : R.error("Delete failed");
    }

    @Override
    public R allSongList() {
        return R.success(null, songListMapper.selectList(null));
    }

    @Override
    public List<SongList> findAllSong() {
        return songListMapper.selectList(null);
    }

    @Override
    public R likeTitle(String title) {
        QueryWrapper<SongList> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title", title);
        return R.success(null, songListMapper.selectList(queryWrapper));
    }

    @Override
    public R likeStyle(String style) {
        QueryWrapper<SongList> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("style", style);
        return R.success(null, songListMapper.selectList(queryWrapper));
    }

    @Override
    public R addSongList(SongListRequest addSongListRequest) {
        SongList songList = new SongList();
        BeanUtils.copyProperties(addSongListRequest, songList);
        songList.setPic("/img/songListPic/default.jpg"); // Default image
        return songListMapper.insert(songList) > 0 ? R.success("Add successful") : R.error("Add failed");
    }

    @Override
    public R updateSongListImg(MultipartFile avatarFile, int id) {
        String fileName = avatarFile.getOriginalFilename();
        String imgPath = "/" + bucketName + "/songlist/" + fileName;
        MinioUploadController.uploadSonglistImgFile(avatarFile);

        SongList songList = new SongList();
        songList.setId(id);
        songList.setPic(imgPath);

        return songListMapper.updateById(songList) > 0 ? R.success("Upload successful", imgPath) : R.error("Upload failed");
    }
}

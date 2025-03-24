package com.example.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.music.common.R;
import com.example.music.controller.MinioUploadController;
import com.example.music.mapper.SongMapper;
import com.example.music.model.domain.Song;
import com.example.music.model.request.SongRequest;
import com.example.music.service.SongService;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.errors.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {

    @Autowired
    private SongMapper songMapper;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Autowired
    private MinioClient minioClient;

    @Override
    public R allSong() {
        return R.success(null, songMapper.selectList(null));
    }

    @Override
    public R addSong(SongRequest addSongRequest, MultipartFile lrcFile, MultipartFile mpFile) {
        Song song = new Song();
        BeanUtils.copyProperties(addSongRequest, song);
        String defaultPic = "/img/songPic/tubiao.jpg";
        String fileName = mpFile.getOriginalFilename();
        String uploadStatus = MinioUploadController.uploadFile(mpFile);
        String filePath = "/" + bucketName + "/" + fileName;

        song.setCreateTime(new Date());
        song.setUpdateTime(new Date());
        song.setPic(defaultPic);
        song.setUrl(filePath);

        if (lrcFile != null && song.getLyric().equals("[00:00:00] No lyrics available")) {
            try {
                String content = new String(lrcFile.getBytes(), "GB2312");
                song.setLyric(content);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if ("File uploaded successfully!".equals(uploadStatus) && songMapper.insert(song) > 0) {
            return R.success("Upload successful", filePath);
        } else {
            return R.error("Upload failed");
        }
    }

    @Override
    public R updateSongMsg(SongRequest updateSongRequest) {
        Song song = new Song();
        BeanUtils.copyProperties(updateSongRequest, song);
        return songMapper.updateById(song) > 0 ? R.success("Update successful") : R.error("Update failed");
    }

    @Override
    public R updateSongUrl(MultipartFile urlFile, int id) {
        Song song = songMapper.selectById(id);
        String oldPath = song.getUrl();
        String oldFileName = oldPath.substring(oldPath.lastIndexOf("/") + 1);

        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(oldFileName)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String newFileName = urlFile.getOriginalFilename();
        String uploadStatus = MinioUploadController.uploadFile(urlFile);
        String newPath = "/" + bucketName + "/" + newFileName;

        song.setId(id);
        song.setUrl(newPath);
        song.setName(newFileName);

        if ("File uploaded successfully!".equals(uploadStatus) && songMapper.updateById(song) > 0) {
            return R.success("Update successful", newPath);
        } else {
            return R.error("Update failed");
        }
    }

    @Override
    public R updateSongPic(MultipartFile urlFile, int id) {
        String fileName = urlFile.getOriginalFilename();
        String filePath = "/user01/singer/song/" + fileName;
        MinioUploadController.uploadSongImgFile(urlFile);

        Song song = new Song();
        song.setId(id);
        song.setPic(filePath);

        return songMapper.updateById(song) > 0 ? R.success("Upload successful", filePath) : R.error("Upload failed");
    }

    @Override
    public R deleteSong(Integer id) {
        Song song = songMapper.selectById(id);
        String filePath = song.getUrl();
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);

        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return songMapper.deleteById(id) > 0 ? R.success("Delete successful") : R.error("Delete failed");
    }

    @Override
    public R songOfSingerId(Integer singerId) {
        QueryWrapper<Song> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("singer_id", singerId);
        return R.success(null, songMapper.selectList(queryWrapper));
    }

    @Override
    public R songOfId(Integer id) {
        QueryWrapper<Song> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        return R.success(null, songMapper.selectList(queryWrapper));
    }

    @Override
    public R songOfSingerName(String name) {
        QueryWrapper<Song> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        List<Song> songs = songMapper.selectList(queryWrapper);
        return songs.isEmpty() ? R.error("No matching songs found") : R.success(null, songs);
    }

    @Override
    public R updateSongLrc(MultipartFile lrcFile, int id) {
        Song song = songMapper.selectById(id);
        if (lrcFile != null && !song.getLyric().equals("[00:00:00] No lyrics available")) {
            try {
                String content = new String(lrcFile.getBytes(), "GB2312");
                song.setLyric(content);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return songMapper.updateById(song) > 0 ? R.success("Update successful") : R.error("Update failed");
    }
}

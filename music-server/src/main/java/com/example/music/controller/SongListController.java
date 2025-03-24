package com.example.music.controller;

import com.example.music.common.R;
import com.example.music.model.request.SongListRequest;
import com.example.music.service.SongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/songList")
public class SongListController {

    @Autowired
    private SongListService songListService;

    // Add a new playlist
    @PostMapping("/add")
    public R addSongList(@RequestBody SongListRequest addSongListRequest) {
        return songListService.addSongList(addSongListRequest);
    }

    // Delete a playlist
    @DeleteMapping("/delete")
    public R deleteSongList(@RequestParam int id) {
        return songListService.deleteSongList(id);
    }

    // Retrieve all playlists
    @GetMapping
    public R allSongList() {
        return songListService.allSongList();
    }

    // Search playlists by title
    @GetMapping("/likeTitle/detail")
    public R songListOfLikeTitle(@RequestParam String title) {
        return songListService.likeTitle('%' + title + '%');
    }

    // Retrieve playlists by genre/style
    @GetMapping("/style/detail")
    public R songListOfStyle(@RequestParam String style) {
        return songListService.likeStyle('%' + style + '%');
    }

    // Update playlist information
    @PutMapping("/update")
    public R updateSongListMsg(@RequestBody SongListRequest updateSongListRequest) {
        return songListService.updateSongListMsg(updateSongListRequest);
    }

    // Update playlist image
    @PostMapping("/img/update")
    public R updateSongListPic(@RequestParam("file") MultipartFile avatarFile, @RequestParam("id") int id) {
        return songListService.updateSongListImg(avatarFile, id);
    }
}

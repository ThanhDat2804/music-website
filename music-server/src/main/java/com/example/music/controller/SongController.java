package com.example.music.controller;

import com.example.music.common.R;
import com.example.music.model.request.SongRequest;
import com.example.music.service.SongService;
import jakarta.servlet.MultipartConfigElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SongController {

    @Autowired
    private SongService songService;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // Maximum file size: 20MB (supports B, KB, MB, GB, TB)
        factory.setMaxFileSize(DataSize.of(20, DataUnit.MEGABYTES));
        // Maximum total upload size: 20MB
        factory.setMaxRequestSize(DataSize.of(20, DataUnit.MEGABYTES));
        return factory.createMultipartConfig();
    }

    // Add a new song
    @PostMapping("/song/add")
    public R addSong(SongRequest addSongRequest, @RequestParam("lrcfile") MultipartFile lrcFile, @RequestParam("file") MultipartFile mpFile) {
        return songService.addSong(addSongRequest, lrcFile, mpFile);
    }

    // Delete a song
    @DeleteMapping("/song/delete")
    public R deleteSong(@RequestParam int id) {
        return songService.deleteSong(id);
    }

    // Retrieve all songs
    @GetMapping("/song")
    public R allSong() {
        return songService.allSong();
    }

    // Retrieve a song by its ID
    @GetMapping("/song/detail")
    public R songOfId(@RequestParam int id) {
        return songService.songOfId(id);
    }

    // Retrieve songs by a singer's ID
    @GetMapping("/song/singer/detail")
    public R songOfSingerId(@RequestParam int singerId) {
        return songService.songOfSingerId(singerId);
    }

    // Retrieve songs by a singer's name
    @GetMapping("/song/singerName/detail")
    public R songOfSingerName(@RequestParam String name) {
        return songService.songOfSingerName('%' + name + '%');
    }

    // Update song information
    @PostMapping("/song/update")
    public R updateSongMsg(@RequestBody SongRequest updateSongRequest) {
        return songService.updateSongMsg(updateSongRequest);
    }

    // Update song cover image
    @PostMapping("/song/img/update")
    public R updateSongPic(@RequestParam("file") MultipartFile urlFile, @RequestParam("id") int id) {
        return songService.updateSongPic(urlFile, id);
    }

    // Update song file
    @PostMapping("/song/url/update")
    public R updateSongUrl(@RequestParam("file") MultipartFile urlFile, @RequestParam("id") int id) {
        return songService.updateSongUrl(urlFile, id);
    }

    // Update song lyrics
    @PostMapping("/song/lrc/update")
    public R updateSongLrc(@RequestParam("file") MultipartFile lrcFile, @RequestParam("id") int id) {
        return songService.updateSongLrc(lrcFile, id);
    }
}

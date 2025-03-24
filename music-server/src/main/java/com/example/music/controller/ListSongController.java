package com.example.music.controller;

import com.alibaba.excel.EasyExcel;
import com.example.music.common.R;
import com.example.music.model.domain.SongList;
import com.example.music.model.request.ListSongRequest;
import com.example.music.service.ListSongService;
import com.example.music.service.SongListService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/listSong")
public class ListSongController {

    @Autowired
    private ListSongService listSongService;

    @Autowired
    private SongListService songListService;

    // Add a song to a playlist
    @PostMapping("/add")
    public R addListSong(@RequestBody ListSongRequest addListSongRequest) {
        return listSongService.addListSong(addListSongRequest);
    }

    // Remove a song from a playlist
    @DeleteMapping("/delete")
    public R deleteListSong(@RequestParam int songId) {
        return listSongService.deleteListSong(songId);
    }

    // Retrieve all songs from a playlist by playlist ID
    @GetMapping("/detail")
    public R listSongOfSongId(@RequestParam int songListId) {
        return listSongService.listSongOfSongId(songListId);
    }

    // Update song information within a playlist
    @PutMapping("/update")
    public R updateListSongMsg(@RequestBody ListSongRequest updateListSongRequest) {
        return listSongService.updateListSongMsg(updateListSongRequest);
    }

    // Export playlist to Excel
    @GetMapping("/export")
    public ResponseEntity<Resource> exportExcel(HttpServletRequest request) {
        String fileName = "SongList_" + System.currentTimeMillis() + ".xlsx";
        File file = new File(fileName);

        try {
            EasyExcel.write(fileName, SongList.class)
                    .sheet("Playlist")
                    .doWrite(fetchSongListData());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(Files.size(file.toPath()))
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        } finally {
            file.delete(); // Delete temporary file after response
        }
    }

    // Fetch all songs in a playlist for export
    private List<SongList> fetchSongListData() {
        return songListService.findAllSong();
    }
}

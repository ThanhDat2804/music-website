package com.example.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.music.common.R;
import com.example.music.model.domain.ListSong;
import com.example.music.model.request.ListSongRequest;

import java.util.List;

public interface ListSongService extends IService<ListSong> {

    // Add a song to a playlist
    R addListSong(ListSongRequest addListSongRequest);

    // Update song information within a playlist
    R updateListSongMsg(ListSongRequest updateListSongRequest);

    // Delete a song from a playlist
    R deleteListSong(Integer songId);

    // Retrieve all songs in all playlists
    List<ListSong> allListSong();

    // Retrieve all songs in a specific playlist
    R listSongOfSongId(Integer songListId);
}

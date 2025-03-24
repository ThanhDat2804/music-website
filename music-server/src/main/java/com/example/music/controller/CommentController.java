package com.example.music.controller;

import com.example.music.common.R;
import com.example.music.model.request.CommentRequest;
import com.example.music.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    // Submit a comment
    @PostMapping("/comment/add")
    public R addComment(@RequestBody CommentRequest addCommentRequest) {
        return commentService.addComment(addCommentRequest);
    }

    // Delete a comment
    @GetMapping("/comment/delete")
    public R deleteComment(@RequestParam Integer id) {
        return commentService.deleteComment(id);
    }

    // Get comments for a specific song ID
    @GetMapping("/comment/song/detail")
    public R commentOfSongId(@RequestParam Integer songId) {
        return commentService.commentOfSongId(songId);
    }

    // Get comments for a specific playlist ID
    @GetMapping("/comment/songList/detail")
    public R commentOfSongListId(@RequestParam Integer songListId) {
        return commentService.commentOfSongListId(songListId);
    }

    // Like a comment
    @PostMapping("/comment/like")
    public R commentOfLike(@RequestBody CommentRequest upCommentRequest) {
        return commentService.updateCommentMsg(upCommentRequest);
    }
}

package com.example.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.music.common.R;
import com.example.music.mapper.CommentMapper;
import com.example.music.model.domain.Comment;
import com.example.music.model.request.CommentRequest;
import com.example.music.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public R addComment(CommentRequest addCommentRequest) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(addCommentRequest, comment);
        comment.setType(addCommentRequest.getNowType());
        return commentMapper.insert(comment) > 0 ? R.success("Comment added successfully") : R.error("Failed to add comment");
    }

    @Override
    public R updateCommentMsg(CommentRequest addCommentRequest) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(addCommentRequest, comment);
        return commentMapper.updateById(comment) > 0 ? R.success("Like added successfully") : R.error("Failed to add like");
    }

    // Delete a comment
    @Override
    public R deleteComment(Integer id) {
        return commentMapper.deleteById(id) > 0 ? R.success("Comment deleted successfully") : R.error("Failed to delete comment");
    }

    @Override
    public R commentOfSongId(Integer songId) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("song_id", songId);
        return R.success(null, commentMapper.selectList(queryWrapper));
    }

    @Override
    public R commentOfSongListId(Integer songListId) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("song_list_id", songListId);
        return R.success(null, commentMapper.selectList(queryWrapper));
    }
}

package com.example.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.music.model.domain.RankList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RankListMapper extends BaseMapper<RankList> {

    /**
     * Get the total score for a song list
     * @param songListId The ID of the song list
     * @return The total score
     */
    int selectScoreSum(Long songListId);

    /**
     * Get the rating given by a specific user
     * @param consumerId The ID of the user
     * @param songListId The ID of the song list
     * @return The rating given by the user
     */
    Integer selectUserRank(@Param("consumer_id") Long consumerId, @Param("song_list_id") Long songListId);
}

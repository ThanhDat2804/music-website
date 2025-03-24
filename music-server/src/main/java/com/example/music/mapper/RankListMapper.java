package com.example.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.music.model.domain.RankList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RankListMapper extends BaseMapper<RankList> {

    /**
     * Query total score
     * @param songListId
     * @return
     */
    int selectScoreSum(Long songListId);

    /**
     * Query specific user's rating
     * @param consumerId
     * @param songListId
     * @return
     */
    Integer selectUserRank(@Param("consumer_id") Long consumerId, @Param("song_list_id") Long songListId);
}

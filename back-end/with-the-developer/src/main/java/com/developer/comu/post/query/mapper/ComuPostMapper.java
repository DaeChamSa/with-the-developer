package com.developer.comu.post.query.mapper;

import com.developer.comu.post.query.dto.ComuPostResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ComuPostMapper {

    // 커뮤니티 게시글 전체 조회
    List<ComuPostResponseDTO> selectAllComuPost(int offset);

    // 커뮤니티 게시글 특정 코드로 조회
    ComuPostResponseDTO selectComuPostByCode(@Param("comuPostCode")Long comuPostCode);
}

package com.developer.bookmark.query.mapper;

import com.developer.bookmark.query.dto.BookmarkListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookmarkMapper {

    List<BookmarkListDTO> selectBookmarkByUserCode(@Param("userCode") Long userCode);
}

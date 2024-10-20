package com.developer.postservice.bookmark.query.mapper;

import com.developer.postservice.bookmark.query.dto.BookmarkListDTO;
import com.developer.postservice.bookmark.query.dto.BookmarkListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookmarkMapper {

    List<BookmarkListDTO> selectBookmarkByUserCode(@Param("userCode") Long userCode);
}

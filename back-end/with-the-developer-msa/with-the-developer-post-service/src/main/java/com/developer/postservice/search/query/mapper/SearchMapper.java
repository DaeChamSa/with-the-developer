package com.developer.postservice.search.query.mapper;

import com.developer.postservice.search.query.dto.SearchGoodsDTO;
import com.developer.postservice.search.query.dto.SearchResultDTO;
import com.developer.postservice.search.query.dto.SearchGoodsDTO;
import com.developer.postservice.search.query.dto.SearchIntegratedResultDTO;
import com.developer.postservice.search.query.dto.SearchResultDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface SearchMapper {
    List<SearchResultDTO> searchRecruit(int offset, @Param("keyword") String keyword);

    List<SearchResultDTO> searchComuPost(int offset, @Param("keyword") String keyword);

    List<SearchResultDTO> searchProjPost(int offset, @Param("keyword") String keyword);

    List<SearchResultDTO> searchTeamPost(int offset, @Param("keyword") String keyword);

    List<SearchIntegratedResultDTO> searchIntegrated(int offset, @Param("keyword") String keyword);

    List<SearchGoodsDTO> searchGoods(int offset, @Param("keyword") String keyword);
}
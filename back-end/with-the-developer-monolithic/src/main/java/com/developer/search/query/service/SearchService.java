package com.developer.search.query.service;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.search.query.dto.SearchGoodsDTO;
import com.developer.search.query.dto.SearchIntegratedResultDTO;
import com.developer.search.query.dto.SearchResultDTO;
import com.developer.search.query.mapper.SearchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchMapper searchMapper;

    public List<SearchResultDTO> search(String domain, String keyword, Integer page) {
        if (page == null || page <= 0) {
            throw new CustomException(ErrorCode.INVALID_VALUE);
        }

        if (keyword.length() < 2) {
            throw new CustomException(ErrorCode.INVALID_KEYWORD);
        }

        int offset = (page - 1) * 10;

        List<SearchResultDTO> results;
        switch (domain) {
            case "recruit":
                results = searchMapper.searchRecruit(offset, keyword);
                break;
            case "comu":
                results = searchMapper.searchComuPost(offset, keyword);
                break;
            case "team":
                results = searchMapper.searchTeamPost(offset, keyword);
                break;
            case "proj":
                results = searchMapper.searchProjPost(offset, keyword);
                break;
            default:
                throw new CustomException(ErrorCode.INVALID_VALUE);
        }

        return handleSearchResults(results);
    }

    public List<SearchIntegratedResultDTO> searchIntegrated(String keyword, Integer page) {
        if (page == null || page <= 0) {
            throw new CustomException(ErrorCode.INVALID_VALUE);
        }

        int offset = (page - 1) * 10;

        List<SearchIntegratedResultDTO> results = searchMapper.searchIntegrated(offset, keyword);

        return handleSearchResults(results);
    }

    private <T> List<T> handleSearchResults(List<T> results) {
        if (results == null || results.isEmpty()) {
            return Collections.emptyList();
        }

        results.forEach(result -> {
            if (result instanceof SearchResultDTO) {
                ((SearchResultDTO) result).setContent(omitContent(((SearchResultDTO) result).getContent()));
            } else if (result instanceof SearchIntegratedResultDTO) {
                ((SearchIntegratedResultDTO) result).setContent(omitContent(((SearchIntegratedResultDTO) result).getContent()));
            }
        });

        return results;
    }

    // 내용이 50자 이상인 경우 50자만 보여주는 메서드
    private String omitContent(String content) {
        if (content == null) {
            return "";
        }

        return content.length() > 50 ? content.substring(0, 50) : content;
    }

    public List<SearchGoodsDTO> searchGoods(String keyword, Integer page) {
        if (page == null || page <= 0) {
            throw new CustomException(ErrorCode.INVALID_VALUE);
        }

        int offset = (page - 1) * 10;

        List<SearchGoodsDTO> results = searchMapper.searchGoods(offset, keyword);

        return handleSearchResults(results);
    }
}

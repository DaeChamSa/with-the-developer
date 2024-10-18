package com.developer.image.command.repository;

import com.developer.image.command.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    // 각 게시글 종류와 게시글 코드로 이미지 목록 조회
    List<Image> findByTeamPostCode(Long teamPostCode);
    List<Image> findByProjPostCode(Long projPostCode);
    List<Image> findByComuCode(Long comuCode);
    List<Image> findByGoodsCode(Long goodsCode);
    List<Image> findByRecruitCode(Long recruitCode);

}

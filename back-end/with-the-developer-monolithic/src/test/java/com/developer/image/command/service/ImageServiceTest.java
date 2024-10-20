package com.developer.image.command.service;

import com.developer.common.module.PostAndImageService;
import com.developer.comu.post.command.dto.ComuPostCreateDTO;
import com.developer.comu.post.command.dto.ComuPostUpdateDTO;
import com.developer.goods.command.application.dto.GoodsCreateDTO;
import com.developer.goods.command.application.dto.GoodsUpdateDTO;
import com.developer.image.command.entity.Image;
import com.developer.image.command.repository.ImageRepository;
import com.developer.project.post.command.application.dto.ProjPostRequestDTO;
import com.developer.recruit.command.dto.RecruitApplyDTO;
import com.developer.team.post.command.dto.TeamPostDeleteDTO;
import com.developer.team.post.command.dto.TeamPostRegistDTO;
import com.developer.team.post.command.dto.TeamPostUpdateDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ImageServiceTest {

    @Autowired
    private PostAndImageService postAndImageService;

    @Autowired
    private ImageRepository imageRepository;

    private static MockMultipartFile[] images = new MockMultipartFile[3];
    private static MockMultipartFile[] updateImages = new MockMultipartFile[3];

    private static final Long userCode = 1L;

    private static final String userId = "testId";

    private static Long teamPostCode;
    private static Long recruitPostCode;
    private static Long projPostCode;
    private static Long goodsPostCode;
    private static Long comuPostCode;

    @BeforeAll
    static void setUpBefore() throws Exception {

        for (int i = 0; i < 3; i++) {
            images[i] = new MockMultipartFile(
                    "test", // 파일의 파라미터 이름
                    "testImage1.png", // 실제 파일 이름
                    "image/png", // 파일의 확장자 타입
                    new FileInputStream(new File("src/test/resources/static/images/testImage1.png"))
            ); // 실제 파일
        }
        for (int i = 0; i < 3; i++) {
            updateImages[i] = new MockMultipartFile(
                    "test", // 파일의 파라미터 이름
                    "testImage2.png", // 실제 파일 이름
                    "image/png", // 파일의 확장자 타입
                    new FileInputStream(new File("src/test/resources/static/images/testImage2.png"))
            ); // 실제 파일
        }
    }


    @Test
    @Order(1)
    @DisplayName("팀 모집 게시글에 이미지를 업로드할 수 있다.")
    void teamImageUploadTest() throws ParseException, IOException {

        // given
        // 테스트를 위한 게시글 등록
        TeamPostRegistDTO registDTO = TeamPostRegistDTO.builder()
                .teamPostTitle("testTitle")
                .teamContent("testContent")
                .teamPostDeadLine("2024-10-04 00:00:00")
                .jobTagNames(List.of(new String[]{"백엔드"}))
                .userCode(userCode)
                .build();

        teamPostCode = postAndImageService.teamPostRegist(registDTO, images);

        List<Image> postImages = imageRepository.findByTeamPostCode(teamPostCode);
        assertEquals(3, postImages.size());
        for(int i = 0; i < postImages.size(); i++){
            assertEquals(images[i].getOriginalFilename(), postImages.get(i).getOriginFileName());
        }
    }

    @Test
    @Order(2)
    @DisplayName("프로젝트 자랑 게시글에 이미지를 등록할 수 있다.")
    void projImageUploadTest() throws IOException {
        // given
        // 테스트를 위한 게시글 등록
        ProjPostRequestDTO projPostRequestDTO = new ProjPostRequestDTO();
        projPostRequestDTO.setProjPostTitle("testTitle");
        projPostRequestDTO.setProjPostContent("testContent");
        projPostRequestDTO.setProjUrl("projUrl");
        projPostRequestDTO.setProjTagContent(List.of(new String[]{"안드로이드", "스프링부트"}));

        // when
        projPostCode = postAndImageService.projPostRegist(projPostRequestDTO, userCode, images);

        //then
        List<Image> postImages = imageRepository.findByProjPostCode(projPostCode);
        assertEquals(3, postImages.size());
        for(int i = 0; i < postImages.size(); i++){
            assertEquals(images[i].getOriginalFilename(), postImages.get(i).getOriginFileName());
        }
    }

    @Test
    @Order(3)
    @DisplayName("커뮤니티 게시글에 이미지를 등록할 수 있다.")
    void comuImageUploadTest() throws ParseException, IOException {

        // given
        // 테스트를 위한 게시글 등록
        ComuPostCreateDTO comuPostCreateDTO = new ComuPostCreateDTO();
        comuPostCreateDTO.setComuSubject("testSubject");
        comuPostCreateDTO.setComuContent("testContent");

        // when
        comuPostCode = postAndImageService.comuPostRegist(comuPostCreateDTO, userId, images);

        // then
        List<Image> postImages = imageRepository.findByComuCode(comuPostCode);
        assertEquals(3, postImages.size());
        for(int i = 0; i < postImages.size(); i++){
            assertEquals(images[i].getOriginalFilename(), postImages.get(i).getOriginFileName());
        }
    }

    @Test
    @Order(4)
    @DisplayName("채용공고 게시글에 이미지를 등록할 수 있다.")
    void recruitImageUploadTest() throws ParseException, IOException {

        //given
        // 테스트를 위한 게시글 등록
        RecruitApplyDTO recruitApplyDTO = new RecruitApplyDTO();
        recruitApplyDTO.setRecruitTitle("testTitle");
        recruitApplyDTO.setRecruitContent("testContent");
        recruitApplyDTO.setRecruitUrl("recruitUrl");
        recruitApplyDTO.setRecruitStart(LocalDateTime.parse("2024-09-30T09:00:00"));
        recruitApplyDTO.setRecruitEnd(LocalDateTime.parse("2024-10-01T09:00:00"));
        recruitApplyDTO.setJobTagNames(List.of(new String[]{"백엔드"}));

        // when
        recruitPostCode = postAndImageService.recruitPostRegist(recruitApplyDTO, userCode, images);

        // then
        List<Image> postImages = imageRepository.findByRecruitCode(recruitPostCode);
        assertEquals(3, postImages.size());
        for(int i = 0; i < postImages.size(); i++){
            assertEquals(images[i].getOriginalFilename(), postImages.get(i).getOriginFileName());
        }
    }

    @Test
    @Order(5)
    @DisplayName("굿즈 게시글에 이미지를 등록할 수 있다.")
    void goodsImageUploadTest() throws ParseException, IOException {

        // given
        // 테스트를 위한 게시글 등록
        GoodsCreateDTO goodsCreateDTO = new GoodsCreateDTO();
        goodsCreateDTO.setGoodsName("상품1");
        goodsCreateDTO.setGoodsContent("상품 1 설명");
        goodsCreateDTO.setGoodsStatus("판매중");
        goodsCreateDTO.setGoodsPrice(1000);

        // when
        goodsPostCode = postAndImageService.goodsRegist(goodsCreateDTO, images);
        // then
        List<Image> postImages = imageRepository.findByGoodsCode(goodsPostCode);
        assertEquals(3, postImages.size());
        for(int i = 0; i < postImages.size(); i++){
            assertEquals(images[i].getOriginalFilename(), postImages.get(i).getOriginFileName());
        }
    }

    @Test
    @Order(6)
    @DisplayName("팀 모집 게시글의 이미지를 수정할 수 있다.")
    void teamImageUpdateTest() throws ParseException, IOException {

        // given
        TeamPostUpdateDTO teamPostUpdateDTO = TeamPostUpdateDTO.builder()
                .teamPostCode(teamPostCode)
                .teamPostTitle("updateTitle")
                .teamContent("updateContent")
                .teamPostDeadLine("2024-10-05 00:00:00")
                .jobTagNames(List.of(new String[]{"프론트엔드","백엔드"}))
                .userCode(userCode)
                .build();
        // when
        postAndImageService.teamPostUpdate(teamPostUpdateDTO, updateImages);

        // then
        List<Image> postImages = imageRepository.findByTeamPostCode(teamPostCode);
        assertEquals(3, postImages.size());
        for(int i = 0; i < postImages.size(); i++){
            assertEquals(updateImages[i].getOriginalFilename(), postImages.get(i).getOriginFileName());
        }
    }

    @Test
    @Order(7)
    @DisplayName("프로젝트 자랑 게시글의 이미지를 수정할 수 있다.")
    void projImageUpdateTest() throws IOException {

        // given
        ProjPostRequestDTO projPostUpdateDTO = new ProjPostRequestDTO();
        projPostUpdateDTO.setProjPostTitle("updateTitle");
        projPostUpdateDTO.setProjPostContent("updateContent");
        projPostUpdateDTO.setProjUrl("updateUrl");
        projPostUpdateDTO.setProjTagContent(List.of(new String[]{"스프링부트"}));

        postAndImageService.projPostUpdate(projPostCode, userCode, projPostUpdateDTO, updateImages);

        // then
        List<Image> postImages = imageRepository.findByProjPostCode(projPostCode);
        assertEquals(3, postImages.size());
        for(int i = 0; i < postImages.size(); i++){
            assertEquals(updateImages[i].getOriginalFilename(), postImages.get(i).getOriginFileName());
        }
    }

    @Test
    @Order(8)
    @DisplayName("커뮤니티 게시글의 이미지를 수정할 수 있다.")
    void comuImageUpdateTest() throws ParseException, IOException {

        // given
        ComuPostUpdateDTO comuPostUpdateDTO = new ComuPostUpdateDTO();
        comuPostUpdateDTO.setComuCode(comuPostCode);
        comuPostUpdateDTO.setComuSubject("updateSubject");
        comuPostUpdateDTO.setComuContent("updateContent");

        postAndImageService.comuPostUpdate(comuPostUpdateDTO, userId, updateImages);

        // then
        List<Image> postImages = imageRepository.findByComuCode(comuPostCode);
        assertEquals(3, postImages.size());
        for(int i = 0; i < postImages.size(); i++){
            assertEquals(updateImages[i].getOriginalFilename(), postImages.get(i).getOriginFileName());
        }
    }

    @Test
    @Order(10)
    @DisplayName("굿즈 게시글의 이미지를 수정할 수 있다.")
    void goodsImageUpdateTest() throws ParseException, IOException {

        // given
        GoodsUpdateDTO goodsUpdateDTO = new GoodsUpdateDTO();
        goodsUpdateDTO.setGoodsCode(goodsPostCode);
        goodsUpdateDTO.setGoodsName("updateTitle");
        goodsUpdateDTO.setGoodsContent("updateContent");
        goodsUpdateDTO.setGoodsName("updateName");
        goodsUpdateDTO.setGoodsStatus("판매종료");
        goodsUpdateDTO.setGoodsPrice(1000);

        // when
        postAndImageService.goodsUpdate(goodsUpdateDTO, updateImages);

        // then
        List<Image> postImages = imageRepository.findByGoodsCode(goodsPostCode);
        assertEquals(3, postImages.size());
        for(int i = 0; i < postImages.size(); i++){
            assertEquals(updateImages[i].getOriginalFilename(), postImages.get(i).getOriginFileName());
        }
    }

    @Test
    @Order(11)
    @DisplayName("팀 모집 게시글의 이미지를 삭제할 수 있다.")
    void teamImageDeleteTest() throws ParseException, IOException {

        // given

        // when
        postAndImageService.teamPostDelete(new TeamPostDeleteDTO(teamPostCode, userCode));

        // then
        List<Image> postImages = imageRepository.findByTeamPostCode(teamPostCode);
        assertEquals(0, postImages.size());
    }

    @Test
    @Order(12)
    @DisplayName("프로젝트 자랑 게시글의 이미지를 삭제할 수 있다.")
    void projImageDeleteTest() throws ParseException, IOException {

        // given

        // when
        postAndImageService.projPostDelete(userCode, projPostCode);

        // then
        List<Image> postImages = imageRepository.findByProjPostCode(projPostCode);
        assertEquals(0, postImages.size());
    }

    @Test
    @Order(13)
    @DisplayName("커뮤니티 게시글의 이미지를 삭제할 수 있다.")
    void comuImageDeleteTest() throws ParseException, IOException {

        // given

        // when
        postAndImageService.comuPostDelete(comuPostCode, userId);

        // than
        List<Image> postImages = imageRepository.findByComuCode(comuPostCode);
        assertEquals(0, postImages.size());
    }

    @Test
    @Order(14)
    @DisplayName("채용공고 게시글의 이미지를 삭제할 수 있다.")
    void recruitImageDeleteTest() throws Exception {

        // given

        // when
        postAndImageService.recruitPostDelete(recruitPostCode, userCode);

        // then
        List<Image> postImages = imageRepository.findByRecruitCode(recruitPostCode);
        assertEquals(0, postImages.size());
    }

    @Test
    @Order(15)
    @DisplayName("굿즈 게시글의 이미지를 삭제할 수 있다.")
    void goodsImageDeleteTest() throws Exception {

        // given

        // when
        postAndImageService.goodsDelete(goodsPostCode);

        // then
        List<Image> postImages = imageRepository.findByGoodsCode(goodsPostCode);
        assertEquals(0, postImages.size());
    }

}
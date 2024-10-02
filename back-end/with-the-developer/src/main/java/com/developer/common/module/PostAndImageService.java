package com.developer.common.module;

import com.developer.comu.post.command.dto.ComuPostCreateDTO;
import com.developer.comu.post.command.dto.ComuPostUpdateDTO;
import com.developer.comu.post.command.service.ComuPostService;
import com.developer.goods.command.application.dto.GoodsCreateDTO;
import com.developer.goods.command.application.dto.GoodsUpdateDTO;
import com.developer.goods.command.application.service.GoodsService;
import com.developer.image.command.entity.ImageType;
import com.developer.image.command.service.ImageService;
import com.developer.project.post.command.application.dto.ProjPostRequestDTO;
import com.developer.project.post.command.application.service.ProjPostCommandService;
import com.developer.recruit.command.dto.RecruitApplyDTO;
import com.developer.recruit.command.service.RecruitCommandService;
import com.developer.team.post.command.dto.TeamPostDeleteDTO;
import com.developer.team.post.command.dto.TeamPostRegistDTO;
import com.developer.team.post.command.dto.TeamPostUpdateDTO;
import com.developer.team.post.command.service.TeamPostCommandService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostAndImageService {

    private final ImageService imageService;
    private final TeamPostCommandService teamPostCommandService;
    private final ComuPostService comuPostService;
    private final ProjPostCommandService projPostCommandService;
    private final RecruitCommandService recruitCommandService;
    private final GoodsService goodsService;

    @Transactional
    public Long teamPostRegist(TeamPostRegistDTO teamPostDTO, MultipartFile[] images) throws ParseException, IOException {
        // 서비스 메소드 호출
        Long createdCode = teamPostCommandService.registTeamPost(teamPostDTO);

        // 이미지도 같이 등록 할 경우 ImageService 호출
        if(images!=null && !images[0].isEmpty()) {
            imageService.upload(images, ImageType.TEAMPOST, createdCode);
        }

        return createdCode;
    }

    @Transactional
    public void teamPostUpdate(TeamPostUpdateDTO teamPostUpdateDTO, MultipartFile[] images) throws ParseException, IOException {

        teamPostCommandService.updateTeamPost(teamPostUpdateDTO);

        imageService.updateImage(images, ImageType.TEAMPOST,teamPostUpdateDTO.getTeamPostCode());

    }

    @Transactional
    public void teamPostDelete(TeamPostDeleteDTO teamPostDTO) throws ParseException {

        imageService.deleteImage(ImageType.TEAMPOST, teamPostDTO.getTeamPostCode());

        teamPostCommandService.deleteTeamPost(teamPostDTO);
    }

    @Transactional
    public Long comuPostRegist(ComuPostCreateDTO comuPostDTO, String userId, MultipartFile[] images) throws ParseException, IOException {

        Long createdCode = comuPostService.createComuPost(comuPostDTO, userId);

        // 이미지도 같이 등록 할 경우 ImageService 호출
        if(images!=null && !images[0].isEmpty()) {
            imageService.upload(images, ImageType.COMU, createdCode);
        }

        return createdCode;
    }

    @Transactional
    public void comuPostUpdate(ComuPostUpdateDTO comuPostDTO, String userId, MultipartFile[] images) throws ParseException, IOException {

        imageService.updateImage(images,ImageType.COMU, comuPostDTO.getComuCode());

        comuPostService.updateComuPost(comuPostDTO, userId);
    }

    @Transactional
    public void comuPostDelete(Long comuPostCode, String userId){

        imageService.deleteImage(ImageType.COMU, comuPostCode);

        comuPostService.deleteComuPost(comuPostCode,userId);
    }

    @Transactional
    public Long projPostRegist(ProjPostRequestDTO projPostDTO, Long loginUserCode, MultipartFile[] images) throws IOException {

        Long createdCode = projPostCommandService.createProjPost(loginUserCode, projPostDTO);

        if(images!=null && !images[0].isEmpty()) {
            imageService.upload(images, ImageType.PROJPOST, createdCode);
        }

        return createdCode;
    }

    @Transactional
    public void projPostUpdate(Long projPostCode, Long loginUserCode, ProjPostRequestDTO projPostRequestDTO, MultipartFile[] images) throws IOException {

        // 이미지 업데이트 호출
        imageService.updateImage(images,ImageType.PROJPOST,projPostCode);

        projPostCommandService.updateProjPost(projPostCode, loginUserCode, projPostRequestDTO);
    }

    @Transactional
    public void projPostDelete(Long loginUserCode, Long projPostCode){

        imageService.deleteImage(ImageType.PROJPOST, projPostCode);

        projPostCommandService.deleteProjPost(loginUserCode, projPostCode);
    }

    @Transactional
    public Long recruitPostRegist(RecruitApplyDTO newApplyRecruitDTO, Long loginUserCode, MultipartFile[] images) throws IOException {

        Long createdCode = recruitCommandService.applyRecruit(newApplyRecruitDTO, loginUserCode);

        if(images!=null && !images[0].isEmpty()) {
            imageService.upload(images, ImageType.RECRUIT, createdCode);
        }

        return createdCode;
    }

    @Transactional
    public void recruitPostDelete(Long recruitCode, Long loginUserCode) throws Exception {

        imageService.deleteImage(ImageType.RECRUIT, recruitCode);

        recruitCommandService.deleteRecruit(recruitCode, loginUserCode);

    }

    @Transactional
    public Long goodsRegist(GoodsCreateDTO goodsCreateDTO, MultipartFile[] images) throws IOException{

        Long createCode =goodsService.createGoods(goodsCreateDTO);

        if(images!=null && !images[0].isEmpty()) {
            imageService.upload(images, ImageType.GOODS, createCode);
        }
        return createCode;
    }

    @Transactional
    public void goodsUpdate(GoodsUpdateDTO goodsUpdateDTO, MultipartFile[] images) throws ParseException, IOException {
       goodsService.updateGoods(goodsUpdateDTO);
       imageService.updateImage(images,ImageType.GOODS,goodsUpdateDTO.getGoodsCode());
    }

    @Transactional
    public void goodsDelete(Long goodsCode) throws Exception{
        imageService.deleteImage(ImageType.GOODS, goodsCode);
        goodsService.deleteGoods(goodsCode);
    }

    }

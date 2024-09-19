package com.developer.comu.command.controller;

import com.developer.comu.command.dto.ComuPostDTO;
import com.developer.comu.command.service.ComuPostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("")
public class ComuPostController {

    private final ComuPostService comuPostService;

    public ComuPostController(ComuPostService comuPostService) {
        this.comuPostService = comuPostService;
    }

    @GetMapping("")
    public void createComuPost() {}

    @PostMapping("")
    public String createComuPost(ComuPostDTO comuPostDTO) {
        comuPostService.createComuPost(comuPostDTO);
        return "";
    }
}

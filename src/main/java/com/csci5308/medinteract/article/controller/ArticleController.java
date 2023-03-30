package com.csci5308.medinteract.article.controller;

import com.csci5308.medinteract.article.model.ArticleModel;
import com.csci5308.medinteract.article.service.ArticleService;
import com.csci5308.medinteract.utilities.LocalDateTimeDeserializer;
import com.csci5308.medinteract.utilities.Response;
import com.google.gson.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

import static com.csci5308.medinteract.utilities.FileUploader.saveFile;

@RestController
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleServiceImpl;

    @Autowired
    public ArticleController(ArticleService articleServiceImpl){
        this.articleServiceImpl = articleServiceImpl;
    }

    @PostMapping(path = "/addArticle", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity addArticle(@RequestParam MultiValueMap<String, String> formData, @RequestParam(value = "content") String content, @RequestParam(value = "coverImage") MultipartFile multipartFile) throws IOException {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer()).create();
//        Gson gson = new Gson();
        ArticleModel articleModel = gson.fromJson(formData.getFirst("objectData"), ArticleModel.class);
        String fileName = RandomStringUtils.randomAlphanumeric(10) + "." + multipartFile.getContentType().split("/")[1];
//        StringUtils.cleanPath(multipartFile.getOriginalFilename())
        String uploadDir = "user-photos/blog/";
        articleModel.setCoverImage(uploadDir + fileName);
        articleModel.setContent(content.getBytes());
        articleModel = articleServiceImpl.saveArticle(articleModel);
        saveFile(uploadDir, fileName, multipartFile);
        Response res = new Response(articleModel, false, "Article added successfully!");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}

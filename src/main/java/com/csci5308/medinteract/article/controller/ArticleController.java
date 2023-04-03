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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.csci5308.medinteract.utilities.FileUploader.saveFile;

@RestController
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleServiceImpl;

    @Autowired
    public ArticleController(ArticleService articleServiceImpl) {
        this.articleServiceImpl = articleServiceImpl;
    }

    @PostMapping(path = "/addArticle", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity addArticle(@RequestParam MultiValueMap<String, String> formData, @RequestParam(value = "content") String content, @RequestParam(value = "coverImage") MultipartFile multipartFile) {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer()).create();
        ArticleModel articleModel = gson.fromJson(formData.getFirst("objectData"), ArticleModel.class);
        String fileName = RandomStringUtils.randomAlphanumeric(10) + ".jpeg";
//        StringUtils.cleanPath(multipartFile.getOriginalFilename())
        String uploadDir = "user-photos/blog/";
        articleModel.setCoverImage(uploadDir + fileName);
        articleModel.setContent(content.getBytes());
        articleModel.setActive(true);
        try {
            saveFile(uploadDir, fileName, multipartFile);
        } catch (IOException e) {
            Response res = new Response("", true, "Error while saving article");
            return new ResponseEntity(res, HttpStatus.OK);
        }
        articleModel = articleServiceImpl.saveArticle(articleModel);
        Response res = new Response(articleModel, false, "Article added successfully!");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }

    @GetMapping("/fetchAllArticles")
    public ResponseEntity fetchAllArticles() {
        List<Map<String, Object>> articleModelList = articleServiceImpl.fetchAllArticles();
        Response res = new Response(articleModelList, false, "Articles fetched successfully");
        return new ResponseEntity(res.getResponse(), HttpStatus.OK);
    }

    @GetMapping("/fetchDoctorArticle/{doctorId}")
    public ResponseEntity fetchAllDoctorArticles(@PathVariable("doctorId") Long id) {
        List<Map<String, Object>> articleModelList = articleServiceImpl.fetchAllDoctorArticles(id);
        Response res = new Response(articleModelList, false, "Articles fetched successfully");
        return new ResponseEntity(res.getResponse(), HttpStatus.OK);
    }

    @GetMapping("/getArticle/{articleId}")
    public ResponseEntity fetchArticle(@PathVariable("articleId") Long id) {
        Optional<ArticleModel> articleModel = articleServiceImpl.fetchArticle(id);
        if (articleModel.isEmpty() || !articleModel.get().getActive()) {
            Response res = new Response("", true, "Error while fetching article!");
            return new ResponseEntity(res.getResponse(), HttpStatus.OK);
        }
        Response res = new Response(articleModel, false, "Article fetched successfully");
        return new ResponseEntity(res.getResponse(), HttpStatus.OK);
    }

    @GetMapping("/deleteArticle/{articleId}")
    public ResponseEntity deleteArticle(@PathVariable("articleId") Long id) {
        Optional<ArticleModel> articleModel = articleServiceImpl.fetchArticle(id);
        if (articleModel.isEmpty() || !articleModel.get().getActive()) {
            Response res = new Response("", true, "Error while deleting article!");
            return new ResponseEntity(res.getResponse(), HttpStatus.OK);
        }
        articleModel.get().setActive(false);
        articleServiceImpl.deleteArticle(id);

        Response res = new Response(articleModel.get(), false, "Article deleted successfully");
        return new ResponseEntity(res.getResponse(), HttpStatus.OK);
    }
}

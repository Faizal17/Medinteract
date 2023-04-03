package com.csci5308.medinteract.article.service;

import com.csci5308.medinteract.article.model.ArticleModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ArticleService {
    ArticleModel saveArticle(ArticleModel articleModel);

    List<Map<String, Object>> fetchAllArticles();
    List<Map<String, Object>> fetchAllDoctorArticles(Long id);

    Optional<ArticleModel> fetchArticle(Long id);
    void deleteArticle(Long id);
}

package com.csci5308.medinteract.article.service;

import com.csci5308.medinteract.article.model.ArticleModel;
import com.csci5308.medinteract.article.repository.ArticleRepository;
import com.csci5308.medinteract.province.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService{
    ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }

    @Override
    public ArticleModel saveArticle(ArticleModel articleModel) {
        return articleRepository.save(articleModel);
    }
}

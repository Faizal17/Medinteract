package com.csci5308.medinteract.article.repository;

import com.csci5308.medinteract.article.model.ArticleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleModel, Long> {
}

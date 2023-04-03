package com.csci5308.medinteract.article.repository;

import com.csci5308.medinteract.article.model.ArticleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleModel, Long> {
//    @Query("SELECT a.id, a.isActive, a.doctorId, a.title, a.coverImage, a.createdDate FROM ArticleModel a WHERE a.isActive = true")
    @Query("SELECT a.id, a.createdDate, a.doctorId, a.title, a.coverImage, d.doctorName FROM ArticleModel a JOIN DoctorModel d ON a.doctorId = d.id WHERE a.isActive = true ORDER BY a.createdDate DESC")
    List<Object> findAllArticles();

    @Query("SELECT a.id, a.createdDate, a.doctorId, a.title, a.coverImage, d.doctorName FROM ArticleModel a JOIN DoctorModel d ON a.doctorId = d.id WHERE a.isActive = true AND a.doctorId = ?1 ORDER BY a.createdDate DESC")
    List<Object> findAllDoctorArticles(Long id);

    @Modifying
    @Query("UPDATE ArticleModel SET isActive = false WHERE id = ?1")
    void deleteById(Long id);
}

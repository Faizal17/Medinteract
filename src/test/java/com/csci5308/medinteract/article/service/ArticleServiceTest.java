package com.csci5308.medinteract.article.service;

import com.csci5308.medinteract.article.model.ArticleModel;
import com.csci5308.medinteract.article.repository.ArticleRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = {ArticleService.class})
public class ArticleServiceTest {


    @MockBean
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleService articleService;

    @Test
    public void testSaveArticle() {
        ArticleModel articleModel = new ArticleModel();
        articleModel.setDoctorId(1L);
        articleModel.setTitle("Test Article");
        articleModel.setCreatedDate(LocalDateTime.now());
        articleModel.setActive(true);

        Mockito.when(articleRepository.save(articleModel)).thenReturn(articleModel);

        ArticleModel savedArticle = articleService.saveArticle(articleModel);

        Assert.assertEquals(articleModel, savedArticle);
        verify(articleRepository, times(1)).save(articleModel);
    }

    @Test
    public void testFetchAllArticles() {
        // mock data for articleRepository.findAllArticles() method
        List<Object> articles = new ArrayList<>();
        LocalDateTime time = LocalDateTime.now();
        articles.add(new Object[]{1L, time, 2L, "Title", "CoverImage", "DoctorName", "ProfilePicture"});

        Mockito.when(articleRepository.findAllArticles()).thenReturn(articles);

        List<Map<String, Object>> expectedArticleList = new ArrayList<>();
        Map<String, Object> expectedData = new HashMap<>();
        expectedData.put("id", 1L);
        expectedData.put("createdDate", time);
        expectedData.put("doctorId", 2L);
        expectedData.put("title", "Title");
        expectedData.put("coverImage", "CoverImage");
        expectedData.put("doctorName", "DoctorName");
        expectedData.put("profilePicture", "ProfilePicture");
        expectedArticleList.add(expectedData);

        List<Map<String, Object>> articleList = articleService.fetchAllArticles();
        assertEquals(expectedArticleList, articleList);
    }

    @Test
    void testFetchAllDoctorArticles() {
        // Setup
        Long doctorId = 1L;
        Object[] article1 = {1L, LocalDateTime.of(2022, 1, 1, 12, 0), doctorId, "Article 1", "cover1.jpg", "Doctor 1", "profile1.jpg"};
        Object[] article2 = {2L, LocalDateTime.of(2022, 1, 2, 12, 0), doctorId, "Article 2", "cover2.jpg", "Doctor 1", "profile1.jpg"};
        List<Object> articles = Arrays.asList(article1, article2);
        Mockito.when(articleRepository.findAllDoctorArticles(doctorId)).thenReturn(articles);

        // Execution
        List<Map<String, Object>> result = articleService.fetchAllDoctorArticles(doctorId);

        // Verification
        assertNotNull(result);
        assertEquals(2, result.size());
        Map<String, Object> article1Data = result.get(0);
        assertEquals(1L, article1Data.get("id"));
        assertEquals(LocalDateTime.of(2022, 1, 1, 12, 0), article1Data.get("createdDate"));
        assertEquals(doctorId, article1Data.get("doctorId"));
        assertEquals("Article 1", article1Data.get("title"));
        assertEquals("cover1.jpg", article1Data.get("coverImage"));
        assertEquals("Doctor 1", article1Data.get("doctorName"));
        assertEquals("profile1.jpg", article1Data.get("profilePicture"));
        Map<String, Object> article2Data = result.get(1);
        assertEquals(2L, article2Data.get("id"));
        assertEquals(LocalDateTime.of(2022, 1, 2, 12, 0), article2Data.get("createdDate"));
        assertEquals(doctorId, article2Data.get("doctorId"));
        assertEquals("Article 2", article2Data.get("title"));
        assertEquals("cover2.jpg", article2Data.get("coverImage"));
        assertEquals("Doctor 1", article2Data.get("doctorName"));
        assertEquals("profile1.jpg", article2Data.get("profilePicture"));
    }

    @Test
    void testFetchAllDoctorArticlesWithNoArticles() {
        // Setup
        Long doctorId = 1L;
        List<Object> articles = Collections.emptyList();
        Mockito.when(articleRepository.findAllDoctorArticles(doctorId)).thenReturn(articles);

        // Execution
        List<Map<String, Object>> result = articleService.fetchAllDoctorArticles(doctorId);

        // Verification
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFetchArticleDetails() {
        Long id = 1L;
        Object[] article = new Object[] {id, LocalDateTime.now(), 2L, "Test Title", "Test Image", "Test Doctor", new byte[] {}, "Test Profile Picture"};
        List<Object> articles = new ArrayList<>();
        articles.add(article);
        Mockito.when(articleRepository.fetchById(id)).thenReturn(articles);

        List<Map<String, Object>> articleDetails = articleService.fetchArticleDetails(id);

        assertNotNull(articleDetails);
        assertEquals(1, articleDetails.size());
        assertEquals(id, articleDetails.get(0).get("id"));
        assertEquals(article[1], articleDetails.get(0).get("createdDate"));
        assertEquals(article[2], articleDetails.get(0).get("doctorId"));
        assertEquals(article[3], articleDetails.get(0).get("title"));
        assertEquals(article[4], articleDetails.get(0).get("coverImage"));
        assertEquals(article[5], articleDetails.get(0).get("doctorName"));
        assertEquals(article[6], articleDetails.get(0).get("content"));
        assertEquals(article[7], articleDetails.get(0).get("profilePicture"));
    }

    @Test
    void testFetchLatestArticles() {
        Long id = 1L;
        Object[] article1 = {1L, LocalDateTime.now(), 1L, "Title 1", "Image 1", "Doctor 1", "Profile 1"};
        Object[] article2 = {2L, LocalDateTime.now(), 2L, "Title 2", "Image 2", "Doctor 2", "Profile 2"};
        List<Object> articles = Arrays.asList(article1, article2);
        Mockito.when(articleRepository.fetchLatestArticles(id)).thenReturn(articles);

        List<Map<String, Object>> result = articleService.fetchLatestArticles(id);

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).get("id"));
        assertEquals("Title 1", result.get(0).get("title"));
        assertEquals("Doctor 1", result.get(0).get("doctorName"));
        assertEquals("Profile 1", result.get(0).get("profilePicture"));
        assertEquals(2L, result.get(1).get("id"));
        assertEquals("Title 2", result.get(1).get("title"));
        assertEquals("Doctor 2", result.get(1).get("doctorName"));
        assertEquals("Profile 2", result.get(1).get("profilePicture"));
    }

    @Test
    void testFetchArticleModel() {
        ArticleModel article = new ArticleModel();
        article.setId(1L);
        article.setTitle("Test Article");
        Optional<ArticleModel> optionalArticle = Optional.of(article);

        Mockito.when(articleRepository.findById(1L)).thenReturn(optionalArticle);

        Optional<ArticleModel> result = articleService.fetchArticleModel(1L);

        assertEquals(optionalArticle, result);
    }

    @Test
    void testDeleteArticle() {
        Long id = 123L;
        doNothing().when(articleRepository).deleteById(id);
        articleService.deleteArticle(id);
        verify(articleRepository, times(1)).deleteById(id);
    }
}



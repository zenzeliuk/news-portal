package com.mainacademy.newsportal.service;

import com.mainacademy.newsportal.common.Language;
import com.mainacademy.newsportal.common.NewsCategory;
import com.mainacademy.newsportal.model.NewsContent;
import com.mainacademy.newsportal.model.NewsResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class NewsContentServiceTest {

    @Autowired
    NewsContentService newsContentService;

    @Test
    void save() {
        NewsContent newsContent = NewsContent.builder()
                .category(NewsCategory.TOP)
                .language(Language.EN)
                .content("test")
                .author("test")
                .description("test")
                .newsUrl("test-1")
                .imageUrl("test")
                .publishedTime(LocalDateTime.now())
                .resource(NewsResource.builder()
                        .apiId("id-1")
                        .name("test")
                        .resourceUrl("test")
                        .language(Language.EN)
                        .category(NewsCategory.SCIENCE)
                        .build())
                .build();
        newsContentService.save(newsContent);

        assertEquals(newsContent, newsContentService.findById(1));
    }

}
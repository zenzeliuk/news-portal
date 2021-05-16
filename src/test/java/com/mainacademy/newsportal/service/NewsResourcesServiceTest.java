package com.mainacademy.newsportal.service;

import com.mainacademy.newsportal.common.Language;
import com.mainacademy.newsportal.common.NewsCategory;
import com.mainacademy.newsportal.model.NewsResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class NewsResourcesServiceTest {

    @Autowired
    NewsResourcesService newsResourcesService;

    @Test
    void saveAll() {
        List<NewsResource> newsResourceList = new ArrayList<>();
        newsResourceList.add(NewsResource.builder()
                .name("name-test-3")
                .apiId("id-3")
                .category(NewsCategory.ENTERTAINMENT)
                .language(Language.EN)
                .resourceUrl("url-test-3")
                .build());
        newsResourceList.add(NewsResource.builder()
                .name("name-test-2")
                .apiId("id-2")
                .category(NewsCategory.HEALTH)
                .language(Language.RU)
                .resourceUrl("url-test-2")
                .build());

        newsResourcesService.saveAll(newsResourceList);

        List<NewsResource> newsResourceListInDB = newsResourcesService.findAll();
        assertTrue(newsResourceListInDB.containsAll(newsResourceList));

    }

    @Test
    void saveAndFindAll() {
        NewsResource newsResource = NewsResource.builder()
                .name("name-test")
                .apiId("id-1")
                .category(NewsCategory.BUSINESS)
                .language(Language.EN)
                .resourceUrl("url-test")
                .build();
        newsResourcesService.save(newsResource);

        List<NewsResource> newsResourceList = newsResourcesService.findAll();
        assertTrue(newsResourceList.contains(newsResource));


    }
}
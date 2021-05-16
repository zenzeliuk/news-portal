package com.mainacademy.newsportal.service;

import com.mainacademy.newsportal.api.client.NewsapiClient;
import com.mainacademy.newsportal.api.client.dto.NewsResponseDTO;
import com.mainacademy.newsportal.api.client.dto.ResourcesResponseDTO;
import com.mainacademy.newsportal.common.Language;
import com.mainacademy.newsportal.common.NewsCategory;
import com.mainacademy.newsportal.dao.NewsResourceRepository;
import com.mainacademy.newsportal.model.NewsContent;
import com.mainacademy.newsportal.model.NewsResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class DataExtractionServiceTest {

    @Autowired
    DataExtractionService dataExtractionService;

    @MockBean
    private NewsapiClient newsapiClient;
    @MockBean
    private NewsResourceRepository newsResourceRepository;

    @Test
    void extractNews() {
        //mock response top news RU and EN
        List<NewsResponseDTO.Article> topArticlesRU = new ArrayList<>();
        topArticlesRU.add(NewsResponseDTO.Article.builder()
                .source(NewsResponseDTO.Source.builder()
                        .id("res-1")
                        .name("name-test")
                        .build())
                .content("content-test")
                .author("author-test")
                .description("description-test")
                .publishedAt(LocalDateTime.now())
                .title("title-test")
                .url("url-test")
                .urlToImage("url-to-image-test")
                .build());
        NewsResponseDTO newsResponseDTORU = NewsResponseDTO.builder()
                .status("OK")
                .articles(topArticlesRU)
                .totalResults(33)
                .build();

        List<NewsResponseDTO.Article> topArticlesEN = new ArrayList<>();
        topArticlesEN.add(NewsResponseDTO.Article.builder()
                .source(NewsResponseDTO.Source.builder()
                        .id("res-2")
                        .name("name-test-2")
                        .build())
                .content("content-test-2")
                .author("author-test-2")
                .description("description-test-2")
                .publishedAt(LocalDateTime.now())
                .title("title-test-2")
                .url("url-test-2")
                .urlToImage("url-to-image-test-2")
                .build());
        NewsResponseDTO newsResponseDTOEN = NewsResponseDTO.builder()
                .status("OK")
                .articles(topArticlesEN)
                .totalResults(22)
                .build();
        //-------------------------------------------------------------------------
        //mock response other news
        List<NewsResponseDTO.Article> otherArticles = new ArrayList<>();
        otherArticles.add(NewsResponseDTO.Article.builder()
                .source(NewsResponseDTO.Source.builder()
                        .id("res-2")
                        .name("name-test-2")
                        .build())
                .content("other-2")
                .author("other-2")
                .description("other-2")
                .publishedAt(LocalDateTime.now())
                .title("other-2")
                .url("other-2")
                .urlToImage("other")
                .build());
        NewsResponseDTO otherNewsResponseDTO = NewsResponseDTO.builder()
                .status("OK")
                .articles(otherArticles)
                .totalResults(2)
                .build();
        //-------------------------------------------------------------------------

        List<NewsResource> newsResources = new ArrayList<>();
        newsResources.add(NewsResource.builder()
                .id(1)
                .apiId("res-1")
                .name("name-test")
                .resourceUrl("resource-url-test")
                .language(Language.RU)
                .category(NewsCategory.BUSINESS)
                .build());
        newsResources.add(NewsResource.builder()
                .id(2)
                .apiId("res-2")
                .name("name-test-2")
                .resourceUrl("resource-url-test-2")
                .language(Language.EN)
                .category(NewsCategory.HEALTH)
                .build());

        when(newsResourceRepository.findAll()).thenReturn(newsResources);

        when(newsapiClient.getTopNews("ru")).thenReturn(newsResponseDTORU);
        when(newsapiClient.getTopNews("en")).thenReturn(newsResponseDTOEN);
        when(newsapiClient.getOtherNews("res-1")).thenReturn(otherNewsResponseDTO);
        when(newsapiClient.getOtherNews("res-2")).thenReturn(otherNewsResponseDTO);

        List<NewsContent> result = dataExtractionService.extractNews();

        Integer sumTopNews = 0;
        for (NewsContent c : result) {
            if (c.getCategory().name().equals(NewsCategory.TOP.toString()))
                sumTopNews++;
        }
        assertEquals(result.size(), 4);
        assertEquals(sumTopNews, 2);


    }

    @Test
    void extractResources() {
        ResourcesResponseDTO resourcesRU;
        ResourcesResponseDTO resourcesEN;

        List<ResourcesResponseDTO.Resource> resourceListRU = new ArrayList<>();
        resourceListRU.add(ResourcesResponseDTO.Resource.builder()
                .id("id-1")
                .name("test-name")
                .category("general")
                .country("ru")
                .language("ru")
                .url("test-url")
                .build());
        resourcesRU = ResourcesResponseDTO.builder()
                .status("OK")
                .sources(resourceListRU)
                .build();

        List<ResourcesResponseDTO.Resource> resourceListEN = new ArrayList<>();
        resourceListEN.add(ResourcesResponseDTO.Resource.builder()
                .id("id-2")
                .name("test-name-2")
                .category("technology")
                .country("us")
                .language("en")
                .url("test-url-2")
                .build());
        resourceListEN.add(ResourcesResponseDTO.Resource.builder()
                .id("id-3")
                .name("test-name-3")
                .category("business")
                .country("au")
                .language("en")
                .url("test-url-3")
                .build());
        resourcesEN = ResourcesResponseDTO.builder()
                .status("OK")
                .sources(resourceListEN)
                .build();
        when(newsapiClient.getAcceptableResources("en")).thenReturn(resourcesEN);
        when(newsapiClient.getAcceptableResources("ru")).thenReturn(resourcesRU);
        List<NewsResource> newsResource = dataExtractionService.extractResources();

        assertNotNull(newsResource);
        assertEquals(newsResource.size(), 3);
    }

}
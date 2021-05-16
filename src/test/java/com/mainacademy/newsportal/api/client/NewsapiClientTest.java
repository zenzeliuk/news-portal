package com.mainacademy.newsportal.api.client;

import com.mainacademy.newsportal.api.client.dto.NewsResponseDTO;
import com.mainacademy.newsportal.api.client.dto.ResourcesResponseDTO;
import com.mainacademy.newsportal.dao.NewsResourceRepository;
import com.mainacademy.newsportal.model.NewsContent;
import com.mainacademy.newsportal.model.NewsResource;
import com.mainacademy.newsportal.service.DataExtractionService;
import com.mainacademy.newsportal.service.NewsContentService;
import com.mainacademy.newsportal.service.NewsResourcesService;
import com.mainacademy.newsportal.service.impl.DataExtractionServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class NewsapiClientTest {

    @Autowired
    NewsapiClient newsapiClient;

    @Test
    void getAcceptableResources() {
        ResourcesResponseDTO result = newsapiClient.getAcceptableResources("en");
        Set<String> categories = result.getSources()
                .stream()
                .map(ResourcesResponseDTO.Resource::getCategory)
                .collect(Collectors.toSet());
        System.out.println("The total number of 'resources' available for your request language EN : " + result.getSources().size());
        categories.forEach(System.out::println);

        assertNotNull(result);
        assertEquals("ok", result.getStatus());
    }

    @Test
    void getTopNews() {
        NewsResponseDTO result = newsapiClient.getTopNews("en");
        System.out.println("The total size of top news available for your request language EN: " + result.getTotalResults());

        assertTrue(nonNull(result.getTotalResults()));
        assertEquals("ok", result.getStatus());
    }

    @Test
    void getOtherNews() {
        ResourcesResponseDTO.Resource source = newsapiClient.getAcceptableResources("en").getSources().get(2);
        NewsResponseDTO result = newsapiClient.getOtherNews(source.getId());
        System.out.println("The total number of results available for your request : " + result.getTotalResults());

        assertTrue(nonNull(result.getTotalResults()));
        assertEquals("ok", result.getStatus());
    }

}
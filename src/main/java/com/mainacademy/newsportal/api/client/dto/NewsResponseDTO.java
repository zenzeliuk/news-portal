package com.mainacademy.newsportal.api.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponseDTO {

    private String status;
    private Integer totalResults;

    private List<Article> articles;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Article {

        private Source source;
        private String author;
        private String title;
        private String description;
        private String url;
        private String urlToImage;
        private String content;
        private LocalDateTime publishedAt;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Source {

        private String id;
        private String name;

    }

}

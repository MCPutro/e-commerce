package com.ecommerce.auth.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedResponse<T> {

    private List<T> data;

    private Meta meta;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Meta {
        private int page;
        @JsonProperty("perPage")
        private int perPage;
        @JsonProperty("totalItems")
        private int totalItems;
        @JsonProperty("totalPages")
        private int totalPages;
    }

    public static <T> PaginatedResponse<T> of(List<T> data, int page, int perPage, long totalItems) {
        int totalPages = (int) Math.ceil((double) totalItems / perPage);
        return PaginatedResponse.<T>builder()
                .data(data)
                .meta(Meta.builder()
                        .page(page)
                        .perPage(perPage)
                        .totalItems((int) totalItems)
                        .totalPages(totalPages)
                        .build())
                .build();
    }
}

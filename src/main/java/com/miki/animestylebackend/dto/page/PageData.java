package com.miki.animestylebackend.dto.page;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class PageData<T> {
    final private List<T> data;
    final private Integer totalPages;
    final private Long totalElements;
    final private Boolean hasNext;
    final private boolean success = true;
    final private String message;
    final HttpStatus status = HttpStatus.OK;

    public PageData(Page<T> page, String message) {
        this.data = page.getContent();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.hasNext = page.hasNext();
        this.message = message;
    }
}

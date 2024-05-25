package com.miki.animestylebackend.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UpdateCategoryRequest {
    private String name;
    private String description;
}

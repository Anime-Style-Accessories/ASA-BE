package com.miki.animestylebackend.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreateCategoryRequest {
    private String name;
    private String description;
}

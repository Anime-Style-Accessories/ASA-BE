package com.miki.animestylebackend.dto;

import com.miki.animestylebackend.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryData {
    private UUID id;
    private String name;
    private String description;
    public CategoryData(String name, String description){
        this.name = name;
        this.description = description;
    }

}

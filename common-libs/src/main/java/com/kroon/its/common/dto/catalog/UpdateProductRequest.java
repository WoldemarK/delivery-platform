package com.kroon.its.common.dto.catalog;


import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductRequest {

    @Size(min = 1, max = 200,
            message = "Name must be between 1 and 200 characters")
    @Pattern(regexp = ".*\\S.*",
            message = "Name must not be blank")
    private String name;

    @Size(max = 2000,
            message = "Description must not exceed 2000 characters")
    private String description;

    private Boolean active;

    private boolean descriptionProvided;

    @JsonSetter("description")
    public void setDescription(String description) {
        this.description = description;
        this.descriptionProvided = true;
    }
}




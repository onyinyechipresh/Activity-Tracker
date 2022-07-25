package com.example.dtos;

import com.example.models.User;
import lombok.Data;
import javax.persistence.ManyToOne;

@Data
public class TaskDto {
    private String title;
    private String description;
    private String status;
    @ManyToOne
    private User user1;
}

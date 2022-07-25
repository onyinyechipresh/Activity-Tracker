package com.example.models;

import com.example.enums.TaskStatus;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private LocalDateTime dateCreated;
    private LocalDateTime updatedAt;
    private LocalDateTime dateCompleted;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @ManyToOne
    private User user1;

}

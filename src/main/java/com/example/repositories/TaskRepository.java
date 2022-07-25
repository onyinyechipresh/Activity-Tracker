package com.example.repositories;

import com.example.enums.TaskStatus;
import com.example.models.Task;
import com.example.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findTasksByUser1(User user);
    List<Task> findTasksByUser1AndStatus(User user, TaskStatus taskStatus);
    Optional<Task> findTaskByUser1AndId(User user, Long id);

}

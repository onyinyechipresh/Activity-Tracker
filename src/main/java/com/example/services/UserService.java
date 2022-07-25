package com.example.services;

import com.example.dtos.TaskDto;
import com.example.dtos.UserDto;
import com.example.models.Task;
import com.example.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void createUser(UserDto userDto);
    User fetchUser(Long id);
    User login(String email, String password);
    void addTask (Long user_id, TaskDto taskDto);
    List<Task> fetchCompetedTasks(Long id);
    List<Task> fetchInProgressTasks(Long id);
    List<Task> fetchPendingTasks(Long id);
    List<Task> fetchAllTasks(Long id);
    Task fetchTask(Long taskId, Long userId);
    Task pendingToInProgress(Long taskId, Long userId);
    Task pendingToCompleted(Long taskId, Long userId);
    Task inProgressToCompleted(Long taskId, Long userId);
    Task inProgressToPending(Long taskId, Long userId);
    Task completedToInProgress(Long taskId, Long userId);
    void deleteTask(Long taskId, Long userId);
    Task editTask(Long user_id, Long id, TaskDto taskDto);
}

package com.example.services.serviceImpl;

import com.example.dtos.TaskDto;
import com.example.dtos.UserDto;
import com.example.enums.TaskStatus;
import com.example.exceptions.CustomAppException;
import com.example.exceptions.ResourceAlreadyExistsException;
import com.example.exceptions.ResourceNotFoundException;
import com.example.models.Task;
import com.example.models.User;
import com.example.repositories.TaskRepository;
import org.springframework.stereotype.Service;
import com.example.repositories.UserRepository;
import com.example.services.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;


    public UserServiceImpl(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void createUser(UserDto userDto) {
        Optional<User> dbUser = userRepository.findByEmail(userDto.getEmail());
        if (dbUser.isPresent()) {
            throw new ResourceAlreadyExistsException("User already exists.");
        }

        User user = new User();

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        userRepository.save(user);
    }

    @Override
    public User fetchUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent())
            return optionalUser.get();
        else
            throw new ResourceNotFoundException("User doesn't exist!");
    }

    @Override
    public User login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(()-> new ResourceNotFoundException("Invalid email or password."));
    }

    @Override
    public void addTask(Long userId, TaskDto taskDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User doesn't exist."));

        Task task = new Task();
        task.setDescription(taskDto.getDescription());
        task.setDateCreated(LocalDateTime.now());
        task.setStatus(TaskStatus.PENDING);
        task.setTitle(taskDto.getTitle());
        task.setUser1(user);

        taskRepository.save(task);
    }

    @Override
    public List<Task> fetchCompetedTasks(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User doesn't exist."));

        return taskRepository.findTasksByUser1AndStatus(user, TaskStatus.COMPLETED);
    }

    @Override
    public List<Task> fetchInProgressTasks(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User doesn't exist."));

        return taskRepository.findTasksByUser1AndStatus(user, TaskStatus.IN_PROGRESS);
    }

    @Override
    public List<Task> fetchPendingTasks(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User doesn't exist."));

        return taskRepository.findTasksByUser1AndStatus(user, TaskStatus.PENDING);
    }

    @Override
    public List<Task> fetchAllTasks(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User doesn't exist."));
        List<Task> task =  taskRepository.findTasksByUser1(user);
        if (task.size() == 0 ) {
            throw new CustomAppException("No task added yet");
        }
        return task;
    }

    @Override
    public Task fetchTask(Long taskId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User doesn't exist."));
        Optional<Task> task = taskRepository.findTaskByUser1AndId(user, taskId);
        return task.
                orElseThrow(() -> new CustomAppException("No task added yet"));

    }

    @Override
    public Task pendingToInProgress(Long taskId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User doesn't exist."));

        Optional<Task> item = taskRepository.findTaskByUser1AndId(user, taskId);

        Task updateItem = new Task();
        if (item.isPresent()) {
            updateItem = item.get();
            updateItem.setStatus(TaskStatus.IN_PROGRESS);
            updateItem.setUpdatedAt(LocalDateTime.now());
            updateItem = taskRepository.save(updateItem);
        }

        return updateItem;
    }

    private Task inProgressOrPendingToFinished(Long taskId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User doesn't exist."));

        Optional<Task> item = taskRepository.findTaskByUser1AndId(user, taskId);

        Task updateItem = new Task();
        if (item.isPresent()) {
            updateItem = item.get();
            updateItem.setStatus(TaskStatus.COMPLETED);
            updateItem.setUpdatedAt(LocalDateTime.now());
            updateItem.setDateCompleted(LocalDateTime.now());
            updateItem = taskRepository.save(updateItem);
        }

        return updateItem;
    }

    @Override
    public Task pendingToCompleted(Long taskId, Long userId) {
        return inProgressOrPendingToFinished(taskId, userId);
    }

    @Override
    public Task inProgressToCompleted(Long taskId, Long userId) {
        return inProgressOrPendingToFinished(taskId, userId);
    }

    @Override
    public Task inProgressToPending(Long taskId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User doesn't exist."));

        Optional<Task> item = taskRepository.findTaskByUser1AndId(user, taskId);
        Task updateItem = new Task();
        if (item.isPresent()) {
            updateItem = item.get();
            updateItem.setStatus(TaskStatus.PENDING);
            updateItem.setUpdatedAt(LocalDateTime.now());
            updateItem = taskRepository.save(updateItem);
        }

        return updateItem;
    }

    @Override
    public Task completedToInProgress(Long taskId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User doesn't exist."));

        Optional<Task> item = taskRepository.findTaskByUser1AndId(user, taskId);
        Task updateItem = new Task();
        if(item.isPresent()){
            updateItem = item.get();
            updateItem.setStatus(TaskStatus.IN_PROGRESS);
            updateItem.setUpdatedAt(LocalDateTime.now());
            updateItem.setDateCompleted(null);
            updateItem = taskRepository.save(updateItem);
        }

        return updateItem;
    }

    @Override
    public void deleteTask(Long taskId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User doesn't exist."));

        Task task = taskRepository.findTaskByUser1AndId(user, taskId)
                .orElseThrow(() -> new CustomAppException("Task does not exist."));

        taskRepository.delete(task);
    }

    @Override
    public Task editTask(Long id, Long user_id, TaskDto taskDto) {
        User user = userRepository.findById(user_id)
                .orElseThrow(()-> new ResourceNotFoundException("User doesn't exit."));
        Task task = taskRepository.findTaskByUser1AndId(user, id)
                .orElseThrow(() -> new CustomAppException("Task does not exist."));
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }
}

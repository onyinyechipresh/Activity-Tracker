package com.example.controllers;
import com.example.dtos.TaskDto;
import com.example.dtos.UserDto;
import com.example.models.Task;
import com.example.models.User;
import com.example.services.serviceImpl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;


@RestController
public class UserController {
    private final UserServiceImpl userService;
    private final HttpSession httpSession;

    public UserController(UserServiceImpl userService, HttpSession httpSession) {
        this.userService = userService;
        this.httpSession = httpSession;
    }

    @PostMapping("/signup")
    public String createUser(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
        return "Registration successful";
    }

    @PostMapping("/login")
    public User logIn(@Valid @RequestBody UserDto userDto) {
        User user = userService.login(userDto.getEmail(), userDto.getPassword());
        httpSession.setAttribute("user_id", user.getId());
        return user;
    }

    @GetMapping("/signout")
    public String signOut(){
        httpSession.invalidate();
        return "Successfully logged out";
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable String id) {
        return userService.fetchUser(Long.parseLong(id));
    }

    @PostMapping("/addTask")
    public String addTask(@RequestBody TaskDto taskDto) {
        userService.addTask((Long) httpSession.getAttribute("user_id"), taskDto);
        return "Task successfully added";
    }

    @GetMapping("/viewTask/{id}")
    public Task fetchTask(@PathVariable String id) {
        return userService.fetchTask(Long.parseLong(id), (Long) httpSession.getAttribute("user_id"));
    }

    @GetMapping("/viewTasks")
    public List<Task> fetchAllTasks() {
        return userService.fetchAllTasks((Long) httpSession.getAttribute("user_id"));
    }

    @GetMapping("/pendingTasks")
    public List<Task> fetchPendingTasks() {
        return userService.fetchPendingTasks((Long) httpSession.getAttribute("user_id"));
    }

    @GetMapping("/inProgressTasks")
    public List<Task> fetchInProgressTasks() {
        return userService.fetchInProgressTasks((Long) httpSession.getAttribute("user_id"));
    }

    @GetMapping("/completedTasks")
    public List<Task> fetchCompletedTasks() {
        return userService.fetchCompetedTasks((Long) httpSession.getAttribute("user_id"));
    }

    @GetMapping("/pendingToInProgress/{id}")
    public Task pendingToInProgress(@PathVariable String id) {
        return userService.pendingToInProgress(Long.parseLong(id), (Long) httpSession.getAttribute("user_id"));
    }

    @GetMapping("/inProgressToFinished/{id}")
    public Task inProgressToFinished(@PathVariable String id) {
        return userService.inProgressToCompleted(Long.parseLong(id), (Long) httpSession.getAttribute("user_id"));
    }

    @GetMapping("/finishedToInProgress/{id}")
    public Task finishedToInProgress(@PathVariable String id) {
        return userService.completedToInProgress(Long.parseLong(id), (Long) httpSession.getAttribute("user_id"));
    }

    @GetMapping("/inProgressToPending/{id}")
    public Task inProgressToInPending(@PathVariable String id) {
        return userService.inProgressToPending(Long.parseLong(id), (Long) httpSession.getAttribute("user_id"));
    }

    @GetMapping("/pendingToFinished/{id}")
    public Task pendingToFinished(@PathVariable String id){
        return userService.pendingToCompleted(Long.parseLong(id), (Long) httpSession.getAttribute("user_id"));
    }

    @GetMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable String id) {
        userService.deleteTask(Long.parseLong(id), (Long) httpSession.getAttribute("user_id"));
        return "Task deleted successfully";
    }

    @PostMapping("/editTask/{id}")
    public Task editTask(@PathVariable String id, @RequestBody TaskDto taskDto) {
       return userService.editTask(Long.parseLong(id), (Long) httpSession.getAttribute("user_id"), taskDto);
    }
}

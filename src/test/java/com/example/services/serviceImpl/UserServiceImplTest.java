package com.example.services.serviceImpl;

import com.example.dtos.UserDto;
import com.example.enums.TaskStatus;
import com.example.exceptions.CustomAppException;
import com.example.models.User;
import com.example.repositories.TaskRepository;
import com.example.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private TaskRepository taskRepository;
    private UserServiceImpl underTest;
//    private AutoCloseable autoCloseable;


    @BeforeEach
    void setUp() {
//        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new UserServiceImpl(userRepository,taskRepository);
    }


    @Test
    void createUser() {
        UserDto userDto = new UserDto();
        userDto.setName("somto");
        userDto.setEmail("somto@mail.com");
        userDto.setPassword("123456");
        //when
        underTest.createUser(userDto);
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser.getName()).isEqualTo(userDto.getName());
        assertThat(capturedUser.getEmail()).isEqualTo(userDto.getEmail());
        assertThat(capturedUser.getPassword()).isEqualTo(userDto.getPassword());
    }

    @Test
    void fetchUser() {
        Optional<User> optionalUser = userRepository.findById(1L);
        if (optionalUser.isPresent())
            verify(userRepository).findById(1L);
    }

    @Test
    @Disabled
    void login() {
    }

    @Test
    @Disabled
    void addTask() {
    }

    @Test
    void fetchCompetedTasks() {
        Optional<User> optionalUser = userRepository.findById(1L);
        if (optionalUser.isPresent())
            verify(taskRepository).findTasksByUser1AndStatus(optionalUser.get(), TaskStatus.COMPLETED);

    }

    @Test
    void fetchInProgressTasks() {
        Optional<User> optionalUser = userRepository.findById(1L);
        if (optionalUser.isPresent())
            verify(taskRepository).findTasksByUser1AndStatus(optionalUser.get(), TaskStatus.IN_PROGRESS);

    }

    @Test
    void fetchPendingTasks() {
        Optional<User> optionalUser = userRepository.findById(1L);
        if (optionalUser.isPresent())
            verify(taskRepository).findTasksByUser1AndStatus(optionalUser.get(), TaskStatus.PENDING);
    }

    @Test
    void fetchAllTasks() {
        //given
        User myUser= new User();
        myUser.setName("samuel");
        myUser.setEmail("mymail@mail.com");
        myUser.setPassword("123456");
        userRepository.save(myUser);
        //when
        Optional<User> user = userRepository.findByEmail("mymail@mail.com");

        //then
        if (user.isPresent()){
            verify(taskRepository).findTasksByUser1(user.get());
        }
    }

    @Test
    @Disabled
    void fetchTask() {
    }

    @Test
    @Disabled
    void pendingToInProgress() {
    }

    @Test
    @Disabled
    void pendingToCompleted() {
    }

    @Test
    @Disabled
    void inProgressToCompleted() {
    }

    @Test
    @Disabled
    void inProgressToPending() {
    }

    @Test
    @Disabled
    void completedToInProgress() {
    }

    @Test
    @Disabled
    void deleteTask() {
    }

    @Test
    @Disabled
    void editTask() {
    }
}
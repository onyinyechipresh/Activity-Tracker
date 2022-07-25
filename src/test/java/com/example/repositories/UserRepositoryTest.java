package com.example.repositories;

import com.example.dtos.UserDto;
import com.example.models.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository underTest;
    @Test
    void findByEmail() {
        //given
        User myUser= new User();
        myUser.setName("samuel");
        myUser.setEmail("mail@mail.com");
        myUser.setPassword("123456");

        underTest.save(myUser);
        //when
        Optional<User> user = underTest.findByEmail("mail@mail.com");
        //then
        if (user.isPresent()){
            assertThat(user.get()).isEqualTo(myUser);
        }else{
            assertThat(user).isEqualTo(null);
        }

    }

    @Test
    void findByEmailAndPassword() {
        //given
        User myUser= new User();
        myUser.setName("samuel");
        myUser.setEmail("mail@mail.com");
        myUser.setPassword("123456");

        underTest.save(myUser);
        //when
        Optional<User> user = underTest.findByEmailAndPassword("mail@mail.com","123456");
        //then
        if (user.isPresent()){
            assertThat(user.get()).isEqualTo(myUser);
        }else{
            assertThat(user).isEqualTo(null);
        }
    }
}
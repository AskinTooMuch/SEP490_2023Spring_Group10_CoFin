package com.example;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.swing.text.html.Option;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {
    @Autowired private UserRepository repo;

    @Test
    public void testAddNew() {
        User user = new User();
        user.setEmail("testEmail3.addNew3@gmail.com");
        user.setPassword("16122001");
        user.setFullName("Asana Sakana Hana");

        User savedUser = repo.save(user);

        Assertions.assertNotEquals(savedUser, null);
        Assertions.assertTrue(savedUser.getId() > 0);
    }

    @Test
    public void testListAll(){
        Iterable<User> users = repo.findAll();
        Assertions.assertTrue(users.iterator().hasNext());

        for (User user : users) {
            System.out.println(user.toString());
        }
    }

    @Test
    public void testUpdate(){
        Integer userId = 3;
        Optional<User> userTest = repo.findById(userId);
        User user = userTest.get();
        user.setPassword("16122001no");
        repo.save(user);

        User updatedUser = repo.findById(userId).get();
        Assertions.assertTrue(updatedUser.getPassword() == "16122001no");
    }

    @Test
    public void testGet(){
        Integer userId = 3;
        Optional<User> userTest = repo.findById(userId);
        Assertions.assertTrue(userTest.isPresent());
        System.out.println(userTest.get());
    }

    @Test
    @Rollback
    public void testDelete(){
        Integer userId = 3;
        repo.deleteById(userId);

        Optional<User> userTest = repo.findById(userId);
        Assertions.assertTrue(!userTest.isPresent());
    }
}

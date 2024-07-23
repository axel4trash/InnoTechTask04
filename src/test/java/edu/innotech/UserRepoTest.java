package edu.innotech;

import edu.innotech.entity.User;
import edu.innotech.repository.UserRepo;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(connection= EmbeddedDatabaseConnection.H2)
public class UserRepoTest {

    @Autowired
    private UserRepo userRepository;

    @Test
    public void userRepoTest0() {

        //Arrange
        User newUser = new User("testUser", "Tester Test Testovich");

        //Act
        User savedUser = userRepository.save(newUser);

        //Assert
        Assertions.assertNotNull(savedUser);
    }

}

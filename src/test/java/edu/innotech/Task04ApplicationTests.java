package edu.innotech;

import edu.innotech.entity.User;
import edu.innotech.repository.LoginRepo;
import edu.innotech.repository.UserRepo;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.assertj.ApplicationContextAssert;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
public class Task04ApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    FileUsersReaderTest fileUsersReader;

    @Test
    public void fileReaderTest() {
        fileUsersReader = new FileUsersReaderTest();
        Set<User> users = fileUsersReader.read();
        System.out.println(".fileReaderTest: users="+users);
        Assertions.assertNotNull(users);
    }
}


package edu.innotech;

import edu.innotech.entity.Login;
import edu.innotech.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import edu.innotech.repository.LoginRepo;
import edu.innotech.repository.UserRepo;

import java.util.Set;

/*Компонента записи данных в БД*/
@Component
public class DataBaseWriter implements DataWriter{
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private LoginRepo loginRepo;

    public void writeUsers(Set<User> users){userRepo.saveAll(users);}

    public void writeLogins(Set<Login> logins){loginRepo.saveAll(logins);}

    @Override
    @LogTransformation
    public <T> void write(T data) {
        Set<User> userSet = (Set<User>)data;
        System.out.println(".DataBaseWriter: запишем userSet=" + userSet.size());
        writeUsers(userSet);
        System.out.println(".DataBaseWriter: Запись окончена!");
    }
}

package edu.innotech;

import edu.innotech.entity.Login;
import edu.innotech.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TransformApplication implements ApplicationRunner {
    @Autowired
    DataWriter dataBaseWriter;

    @Autowired
    FileUsersReader fileReader;

    @Autowired
    List<Checker<User>> userCheckers;

    @Autowired
    List<Checker<Login>> loginCheckers;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Set<User> userSet = new HashSet<>();
        userSet = fileReader.read();
        if (userSet.size() > 0) {
            //System.out.println("before check="+userSet.size());
            userSet.forEach(us->
            {   //Проверки на user
                userCheckers.forEach(ch->ch.check(us));
                //System.out.println("logins before filter:" + us.getLogins());
                //Проверки на login
                List<Login> newListLogin = us.getLogins()
                     .stream()
                     .filter(lg -> {
                         for (Checker<Login> ch: loginCheckers
                              ) {
                                if (!ch.check(lg)) {return false;}
                                }
                            return true;
                            })
                     .toList();
                //System.out.println("logins after  filter:" + newListLogin);
                us.setLogins(newListLogin);
            }
            );
            //System.out.println("after check="+userSet.size());

            dataBaseWriter.write(userSet);
        }
    }


}

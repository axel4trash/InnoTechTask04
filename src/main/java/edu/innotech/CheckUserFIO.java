package edu.innotech;

import edu.innotech.entity.User;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Order(10)
/*Промежуточная компонента
проверки данных исправляет ФИО так, чтобы каждый его компонент начинался с большой буквы.
* */
public class CheckUserFIO implements Checker<User> {

    @Override
    public boolean check(Object data) {
        System.out.println(".CheckUserFIO: ");
        User user = (User) data;
        // Сделать первые буквы ФИО большими
        user.setFio(Arrays.stream(user.getFio().split(" "))
                .map(StringUtils::capitalize)
                .collect(Collectors.joining(" ")));
        return true;
    }

}


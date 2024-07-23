package edu.innotech;

import edu.innotech.entity.Login;
import edu.innotech.entity.User;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Set;

/*Промежуточная компонента проверяет что тип приложения соответствует одному из: “web”, “mobile”.
Если там записано что-либо иное, то оно преобразуется к виду “other:”+значение.
* */
@Component
@Order(30)
public class CheckLoginAppType implements Checker<Login>{
    @Override
    public <T> boolean check(T data) {
        // Проверить значение типа приложения и при необходимости заменить
        boolean res = true;
        Login login = (Login) data;
        System.out.println(".CheckLoginAppType: login=" + login);
        if (!login.getApplication().matches("web|mobile")) {
            login.setApplication("other:" + login.getApplication());
        }
        return res;
    }


}

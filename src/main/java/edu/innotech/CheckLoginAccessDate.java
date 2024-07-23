package edu.innotech;

import edu.innotech.entity.Login;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Set;

/*5) Промежуточная компонента проверки даты проверяет её наличие.
Если дата не задана, то человек не вносится в базу,
а сведения о имени файла и значении человека заносятся в отдельный лог.
* */
@Component
@Order(20)
public class CheckLoginAccessDate implements Checker<Login>{
    @Override
    public <T> boolean check(T data) {
        // Найти входы без указания времени и записать информацию в лог
        Login login = (Login) data;
        System.out.println(".CheckLoginAccessDate: login="+login);
        System.out.println(" getAccess_date = " + login.getAccess_date());
        return login.getAccess_date() != null;
    }
}

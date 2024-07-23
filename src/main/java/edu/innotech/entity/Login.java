package edu.innotech.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="logins")
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    String logFileName;

    @Column(name = "access_date")
    private Timestamp access_date;

    @Column(name = "application")
    private String application;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "user_id")
    private User user;

    public Login(Timestamp access_time, String application, User user, String logFileName) {
        this.user = user;
        this.access_date = access_time;
        this.application = application;
        this.logFileName = logFileName;
    }

    @Override
    public String toString() {
        return "Login{" +
                "id=" + id +
                ", access_date=" + access_date +
                ", application='" + application + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Login login = (Login) o;
        return Objects.equals(logFileName, login.logFileName)
                && Objects.equals(id, login.id)
                && Objects.equals(user, login.user)
                && Objects.equals(access_date, login.access_date)
                && Objects.equals(application, login.application);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logFileName, id, user, access_date, application);
    }
}

package edu.innotech;

import edu.innotech.entity.Login;
import edu.innotech.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*Компонента чтения данных
получает адрес папки файловой системы, сканирует имеющиеся там файлы и возвращает прочитанные строки
*/
@Component
public class FileUsersReaderTest {
    //@Value("${spring.application.inputfilespath}")
    private String filePath = "src/main/resources/data/";

    //@Value("${spring.application.file-separator}")
    private String fileSeparator = ";";

    private final HashMap<User, User> listOfUsers = new HashMap<>();

    public void readFile (Path filePath) throws IOException {
        User user;
        Login login;

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath.toString()))) {
            // read line by line
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }

        System.out.println(sb);

    }

    // Метод для чтения информации о входах клиентов из текстового файла
    private void readUsersFromFile(Path filePath){
        User user;
        Login login;
        System.out.println(".FileUsersReader.readUsersFromFile: filePath = "+ filePath);
        System.out.println(".FileUsersReader.readUsersFromFile: fileSeparator = "+ fileSeparator);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line = reader.readLine();
            String[] strings;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date loginTime = null;
            while (line != null) {
                strings = line.split(fileSeparator);
                System.out.println("line="+line);
                System.out.println(".readUsersFromFile: strings[0]="+strings[0]+" strings[1]="+strings[1]+" strings[2]="+strings[2]+" strings[3]="+strings[3]);

                user = new User(strings[0], strings[1]);
                if (!listOfUsers.containsKey(user)){
                    System.out.println("(!) NO containsKey --> put");
                    listOfUsers.put(user, user);
                } else {
                    System.out.println("(!) containsKey --> get");
                    user = listOfUsers.get(user);
                }

                try {
                    loginTime = dateFormat.parse(strings[2]);
                } catch (Exception e) {
                    loginTime = null;
                }
                System.out.println(" loginTime = " + loginTime);
                login = new Login(loginTime != null ? new java.sql.Timestamp(loginTime.getTime()) : null, strings[3], user, filePath.toString());
                user.getLogins().add(login);

                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }
    // Получение списка файлов, имеющихся в каталоге
    public List<Path> getListOfFiles (String pathString) throws IOException {
        System.out.println(".FileUsersReader: getListOfFiles = "+pathString);
        Path path = Paths.get(pathString);

        List<Path> listOfFiles;
        try (Stream<Path> walk = Files.walk(path)) {
            listOfFiles = walk
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }
        return listOfFiles;
    }

    public Set<User> read() {
        System.out.println(".FileUsersReader: read");
        try {
            List<Path> listOfFiles = getListOfFiles(filePath);
            listOfFiles.forEach(this::readUsersFromFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listOfUsers.keySet();
    }
}

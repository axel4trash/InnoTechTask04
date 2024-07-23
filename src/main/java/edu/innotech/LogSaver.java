package edu.innotech;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogSaver {
    private static final Logger LOGGER = Logger.getLogger(LogSaver.class.getName());

    private static String logfilepath = "src/main/resources/log/";
    private static String logfilename = "log.txt";

    public static void saveToLog(String stringForLog){
        System.out.println("Logger Name: " + LOGGER.getName());
        System.out.println("Logger logpath: " + logfilepath + logfilename);
        try{
            FileHandler fileHandler  = new FileHandler(logfilepath + logfilename, true);
            LOGGER.addHandler(fileHandler);
            fileHandler.setFormatter(new SimpleFormatter());
            String msg = "Запись данных в БД. Время: "
                    + new SimpleDateFormat("yyyyMMdd_HHmmss")
                    .format(Calendar.getInstance().getTime());
            System.out.println("LogSaver.saveToLog: msg="+msg);
            LOGGER.info(msg);
        } catch (SecurityException | IOException e) {
            LOGGER.log(Level.SEVERE, "Ошибка при записи в лог!", e);
        }
    }
}

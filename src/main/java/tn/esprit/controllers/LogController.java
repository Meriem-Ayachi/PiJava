package tn.esprit.controllers;
import tn.esprit.models.Log;
import tn.esprit.models.LogType;
import tn.esprit.services.LogService;

import java.time.LocalDateTime;

public class LogController {
    private static LogService logService = new LogService();

    public static void saveLog(String message, LogType logType, int userId) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Log log = new Log(currentDateTime,logType.name(), message, userId);
        logService.add(log);
    }

}
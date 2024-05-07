package tn.esprit.controllers;
import tn.esprit.models.Log;
import tn.esprit.models.LogType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LogController {
    private List<Log> logs;

    public LogController() {
        this.logs = new ArrayList<>();
    }

    public void saveLog(String message, LogType logType) {
        Log log = new Log();
        logs.add(log);
        // Vous pouvez également enregistrer le log dans une base de données ou dans un fichier, selon vos besoins.
    }

    // Méthode pour récupérer tous les logs
    public List<Log> getAllLogs() {
        return logs;
    }
}
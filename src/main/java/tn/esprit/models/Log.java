package tn.esprit.models;

import java.time.LocalDateTime;

public class Log {
    private long logId;
    private LocalDateTime timestamp;
    private String type;
    private String description;
    private int userId;

    public Log(){
    }
    public Log(LocalDateTime timestamp, String type, String description, int userId){
        this.timestamp = timestamp;
        this.type = type;
        this.description = description;
        this.userId = userId;
    }
    public Log(long logId, LocalDateTime timestamp, String type, String description, int userId) {
        this.logId = logId;
        this.timestamp = timestamp;
        this.type = type;
        this.description = description;
        this.userId = userId;
    }

    // Getters and setters
    public long getLogId() {
        return logId;
    }

    public void setLogId(long logId) {
        this.logId = logId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Log{" +
                "logId=" + logId +
                ", timestamp=" + timestamp +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
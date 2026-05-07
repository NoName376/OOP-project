package core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SystemLogger implements Serializable {
    public SystemLogger() {
        this.logs = new ArrayList<>();
    }

    public void log(String message) {
        logs.add(message);
        System.out.println("[LOG]: " + message);
    }

    public List<String> getLogs() {
        return logs;
    }

    private List<String> logs;
}

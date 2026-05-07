package academic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Transcript implements Serializable {
    public Transcript() {
        this.records = new HashMap<>();
    }

    public void addRecord(Course course, Mark mark) {
        records.put(course, mark);
    }

    public double calculateGPA() {
        if (records.isEmpty()) return 0.0;
        double totalPoints = 0;
        int totalCredits = 0;
        for (Map.Entry<Course, Mark> entry : records.entrySet()) {
            Course course = entry.getKey();
            Mark mark = entry.getValue();
            totalPoints += (mark.getTotal() / 100.0) * 4.0 * course.getCredits();
            totalCredits += course.getCredits();
        }
        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }

    public Map<Course, Mark> getRecords() {
        return records;
    }

    public byte[] generatePDF() {
        // Placeholder implementation
        return new byte[0];
    }

    private Map<Course, Mark> records;
}

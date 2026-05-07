package infrastructure;

import java.io.Serializable;

public class Registration implements Serializable {
    private String registrationId;
    private String studentId;
    private String courseId;

    public Registration(String registrationId, String studentId, String courseId) {
        this.registrationId = registrationId;
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public String getRegistrationId() { return registrationId; }
    public String getStudentId() { return studentId; }
    public String getCourseId() { return courseId; }
}

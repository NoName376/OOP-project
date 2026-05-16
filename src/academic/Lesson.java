package academic;

import java.io.Serializable;
import java.time.DayOfWeek;
import users.Teacher;

public class Lesson implements Serializable {
    public Lesson(LessonType type, String topic) {
        this.type = type;
        this.topic = topic;
    }

    public void assign(DayOfWeek day, String room, Teacher teacher, String time) {
        this.day = day;
        this.room = room;
        this.teacher = teacher;
        this.time = time;
    }

    public LessonType getType() { return type; }
    public String getTopic() { return topic; }
    public String getRoom() { return room; }
    public DayOfWeek getDay() { return day; }
    public Teacher getTeacher() { return teacher; }
    public String getTime() { return time; }

    @Override
    public String toString() {
        return String.format("%s: %s | %s %s in %s (Teacher: %s)", 
            type, topic, day, time, room, teacher != null ? teacher.getFullName() : "TBA");
    }

    private LessonType type;
    private String topic;
    private DayOfWeek day;
    private String room;
    private Teacher teacher;
    private String time;
}

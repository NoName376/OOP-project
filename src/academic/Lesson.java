package academic;

import java.io.Serializable;

public class Lesson implements Serializable {
    public Lesson(LessonType type, String topic, String room) {
        this.type = type;
        this.topic = topic;
        this.room = room;
    }

    public LessonType getType() { return type; }
    public String getTopic() { return topic; }
    public String getRoom() { return room; }

    @Override
    public String toString() {
        return "Lesson [" + type + ": " + topic + " in " + room + "]";
    }

    private LessonType type;
    private String topic;
    private String room;
}

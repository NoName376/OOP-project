package infrastructure;

import java.io.Serializable;
import java.util.Date;

public class NewsEntry implements Serializable {
    public NewsEntry(String title, String content) {
        this.title = title;
        this.content = content;
        this.datePosted = new Date();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    private String title;
    private String content;
    private Date datePosted;
}

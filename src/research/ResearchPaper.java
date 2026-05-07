package research;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class ResearchPaper implements Serializable {
    public ResearchPaper(String name, List<String> authors, String journal, int pages, LocalDate datePublished, int citations, String doi) {
        this.name = name;
        this.authors = authors;
        this.journal = journal;
        this.pages = pages;
        this.datePublished = datePublished;
        this.citations = citations;
        this.doi = doi;
    }

    public int getArticleLength() {
        return pages;
    }

    public int getCitations() {
        return citations;
    }

    public String getName() { return name; }
    public List<String> getAuthors() { return authors; }
    public String getJournal() { return journal; }
    public LocalDate getDatePublished() { return datePublished; }
    public String getDoi() { return doi; }

    @Override
    public String toString() {
        return "ResearchPaper [" + name + ", Citations=" + citations + "]";
    }

    private String name;
    private List<String> authors;
    private String journal;
    private int pages;
    private LocalDate datePublished;
    private int citations;
    private String doi;
}

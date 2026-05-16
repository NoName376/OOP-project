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

    public void addCitation(ResearchPaper p) {
        if (!citedBy.contains(p)) {
            citedBy.add(p);
            this.citations++;
        }
    }

    public void addAuthor(String authorName) {
        if (!authors.contains(authorName)) {
            authors.add(authorName);
        }
    }

    public int getCitations() { return citations; }
    public List<ResearchPaper> getCitedBy() { return citedBy; }
    public String getName() { return name; }
    public List<String> getAuthors() { return authors; }
    public String getJournal() { return journal; }
    public LocalDate getDatePublished() { return datePublished; }
    public String getDoi() { return doi; }

    public String getCitationFormat(boolean bibtex) {
        if (bibtex) {
            return String.format("@article{%s,\n  author={%s},\n  title={%s},\n  journal={%s},\n  year={%d},\n  doi={%s}\n}", 
                name.replace(" ", "_"), String.join(" and ", authors), name, journal, datePublished.getYear(), doi);
        }
        return String.format("%s. (%d). %s. %s. DOI: %s", 
            String.join(", ", authors), datePublished.getYear(), name, journal, doi);
    }

    @Override
    public String toString() {
        return String.format("'%s' in %s (%s). Citations: %d, Authors: %s", 
            name, journal, datePublished, citations, String.join(", ", authors));
    }

    private String name;
    private List<String> authors;
    private String journal;
    private int pages;
    private LocalDate datePublished;
    private int citations;
    private String doi;
    private List<ResearchPaper> citedBy = new java.util.ArrayList<>();
}

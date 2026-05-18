package research;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResearchPaper implements Serializable {
    private String name;
    private List<String> authors;
    private String journal;
    private int pages;
    private LocalDate datePublished;
    private int citations;
    private String doi;
    private final List<ResearchPaper> citedBy = new ArrayList<>();
    private boolean approved = true;

    public ResearchPaper(String name, List<String> authors, String journal, int pages, LocalDate datePublished, int citations, String doi) {
        this.name = name;
        this.authors = new ArrayList<>(authors);
        this.journal = journal;
        this.pages = pages;
        this.datePublished = datePublished;
        this.citations = citations;
        this.doi = doi;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getName() { 
        return name; 
    }

    public List<String> getAuthors() { 
        return authors; 
    }

    public String getJournal() { 
        return journal; 
    }

    public int getArticleLength() {
        return pages;
    }

    public LocalDate getDatePublished() { 
        return datePublished; 
    }

    public int getCitations() { 
        return citations; 
    }

    public String getDoi() { 
        return doi; 
    }

    public List<ResearchPaper> getCitedBy() { 
        return citedBy; 
    }

    public void addAuthor(String authorName) {
        if (authorName != null && !authors.contains(authorName)) {
            authors.add(authorName);
        }
    }

    public void addCitation(ResearchPaper p) {
        if (p != null && !citedBy.contains(p)) {
            citedBy.add(p);
            this.citations = this.citations + 1;
        }
    }

    public String getCitationFormat(boolean bibtex) {
        if (bibtex) {
            StringBuilder sb = new StringBuilder();
            sb.append("@article{").append(name.replace(" ", "_")).append(",\n")
              .append("  author={").append(String.join(" and ", authors)).append("},\n")
              .append("  title={").append(name).append("},\n")
              .append("  journal={").append(journal).append("},\n")
              .append("  year={").append(datePublished.getYear()).append("},\n")
              .append("  doi={").append(doi).append("}\n")
              .append("}");
            return sb.toString();
        }
        return String.join(", ", authors) + ". (" + datePublished.getYear() + "). " + name + ". " + journal + ". DOI: " + doi;
    }

    @Override
    public String toString() {
        return "'" + name + "' in " + journal + " (" + datePublished + "). Citations: " + citations + ", Authors: " + String.join(", ", authors);
    }
}

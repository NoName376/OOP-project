package research;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import users.User;

public class ResearchDecorator implements IResearcher {
    public ResearchDecorator(User user) {
        this.user = user;
        this.papers = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    @Override
    public int getHIndex() {
        if (papers.isEmpty()) return 0;
        List<ResearchPaper> sortedPapers = new ArrayList<>(papers);
        sortedPapers.sort((p1, p2) -> Integer.compare(p2.getCitations(), p1.getCitations()));
        
        int h = 0;
        for (int i = 0; i < sortedPapers.size(); i++) {
            if (sortedPapers.get(i).getCitations() >= i + 1) {
                h = i + 1;
            } else {
                break;
            }
        }
        return h;
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> sorter) {
        List<ResearchPaper> sortedPapers = new ArrayList<>(papers);
        sortedPapers.sort(sorter);
        for (ResearchPaper p : sortedPapers) {
            System.out.println(p);
        }
    }

    @Override
    public void addPaper(ResearchPaper p) {
        papers.add(p);
    }

    public User getUser() { return user; }
    @Override
    public List<ResearchPaper> getPapers() { return papers; }
    public List<ResearchProject> getProjects() { return projects; }

    private User user;
    private List<ResearchPaper> papers;
    private List<ResearchProject> projects;
}

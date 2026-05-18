package research;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import users.User;

public class ResearchDecorator implements IResearcher, java.io.Serializable {
    private final User targetUser;
    private final List<ResearchPaper> researchPapersList;
    private final List<ResearchProject> activeProjectsList;

    public ResearchDecorator(User user) {
        this.targetUser = user;
        this.researchPapersList = new ArrayList<>();
        this.activeProjectsList = new ArrayList<>();
    }

    @Override
    public void addPaper(ResearchPaper p) {
        if (p != null) {
            this.researchPapersList.add(p);
        }
    }

    @Override
    public List<ResearchPaper> getPapers() {
        return this.researchPapersList;
    }

    public List<ResearchProject> getProjects() {
        return this.activeProjectsList;
    }

    public User getUser() {
        return this.targetUser;
    }

    @Override
    public int getHIndex() {
        if (researchPapersList.isEmpty()) {
            return 0;
        }
        
        List<Integer> citations = researchPapersList.stream()
                .filter(ResearchPaper::isApproved)
                .map(ResearchPaper::getCitations)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        int hIndexValue = 0;
        while (hIndexValue < citations.size() && citations.get(hIndexValue) >= hIndexValue + 1) {
            hIndexValue++;
        }
        return hIndexValue;
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> sorter) {
        researchPapersList.stream()
                .sorted(sorter)
                .forEach(System.out::println);
    }
}

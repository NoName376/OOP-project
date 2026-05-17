package research;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import users.User;

public class ResearchDecorator implements IResearcher {
    public ResearchDecorator(User user) {
        this.user = user;
    }

    @Override
    public int getHIndex() {
        return 0;
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> sorter) {}

    @Override
    public void addPaper(ResearchPaper p) {}

    @Override
    public List<ResearchPaper> getPapers() {
        return new ArrayList<>();
    }

    public List<ResearchProject> getProjects() {
        return new ArrayList<>();
    }

    public User getUser() {
        return user;
    }

    private User user;
}

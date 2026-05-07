package research;

import java.util.Comparator;
import java.util.List;

public interface IResearcher {
    int getHIndex();
    List<ResearchPaper> getPapers();
    void printPapers(Comparator<ResearchPaper> sorter);
    void addPaper(ResearchPaper p);
}

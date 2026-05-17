package research;

import java.util.Comparator;
import java.util.List;

public interface IResearcher {
    void addPaper(ResearchPaper p);
    List<ResearchPaper> getPapers();
    int getHIndex();
    void printPapers(Comparator<ResearchPaper> sorter);
}

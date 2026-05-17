package research;

import exceptions.NonResearcherException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResearchProject implements Serializable {
    public ResearchProject(String topic) {
        this.topic = topic;
    }

    public void addParticipant(IResearcher r) throws NonResearcherException {}
    public void publishPaper(ResearchPaper p) {}
    public String getTopic() { return topic; }
    public List<IResearcher> getParticipants() { return new ArrayList<>(); }
    public List<ResearchPaper> getPublishedPapers() { return new ArrayList<>(); }

    private String topic;
}

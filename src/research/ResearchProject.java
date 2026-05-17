package research;

import exceptions.NonResearcherException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResearchProject implements Serializable {
    private String topicName;
    private final List<IResearcher> projectParticipants;
    private final List<ResearchPaper> publishedPapersList;

    public ResearchProject(String topic) {
        this.topicName = topic;
        this.projectParticipants = new ArrayList<>();
        this.publishedPapersList = new ArrayList<>();
    }

    public void addParticipant(IResearcher r) throws NonResearcherException {
        if (r == null) {
            throw new NonResearcherException("Participant must be a researcher");
        }
        if (!projectParticipants.contains(r)) {
            projectParticipants.add(r);
        }
    }

    public void publishPaper(ResearchPaper p) {
        if (p != null && !publishedPapersList.contains(p)) {
            publishedPapersList.add(p);
        }
    }

    public String getTopic() { 
        return this.topicName; 
    }

    public List<IResearcher> getParticipants() { 
        return this.projectParticipants; 
    }

    public List<ResearchPaper> getPublishedPapers() { 
        return this.publishedPapersList; 
    }
}

package research;

import exceptions.NonResearcherException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResearchProject implements Serializable {
    public ResearchProject(String topic) {
        this.topic = topic;
        this.participants = new ArrayList<>();
        this.publishedPapers = new ArrayList<>();
    }

    public void addParticipant(IResearcher r) throws NonResearcherException {
        if (r == null) {
            throw new NonResearcherException("Participant must be a researcher");
        }
        participants.add(r);
    }

    public void publishPaper(ResearchPaper p) {
        publishedPapers.add(p);
    }

    public String getTopic() { return topic; }
    public List<IResearcher> getParticipants() { return participants; }
    public List<ResearchPaper> getPublishedPapers() { return publishedPapers; }

    private String topic;
    private List<IResearcher> participants;
    private List<ResearchPaper> publishedPapers;
}

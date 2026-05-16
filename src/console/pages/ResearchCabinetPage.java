package console.pages;

import console.pagescore.Page;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import parsers.IntegerParser;
import research.IResearcher;
import research.ResearchPaper;
import users.Student;

public class ResearchCabinetPage extends Page {
    public ResearchCabinetPage() {
        super("Research Cabinet");
        addAction("View My Papers", this::viewPapers);
        addAction("View Supervisor", this::viewSupervisor);
        addAction("Sort My Papers", this::sortPapersMenu);
        addAction("Join Research Project", this::joinProject);
        addAction("University Research Info", this::universityResearchMenu);
    }

    private void universityResearchMenu() {
        console.getRenderer().renderHeader("University Research Global Info");
        addAction("All University Papers", this::viewAllUniversityPapers);
        addAction("Top Cited Researchers", this::viewTopResearchers);
        
        console.getRenderer().renderMenu(java.util.List.of("All University Papers", "Top Cited Researchers"));
        int choice = console.getInput().read("Option", new parsers.IntegerParser(0, 2));
        if (choice == 1) viewAllUniversityPapers();
        else if (choice == 2) viewTopResearchers();
    }

    private void viewAllUniversityPapers() {
        List<ResearchPaper> allPapers = new ArrayList<>();
        for (users.User u : core.UniversityKernel.getInstance().getUsers()) {
            if (u instanceof IResearcher) {
                allPapers.addAll(((IResearcher) u).getPapers());
            }
        }

        if (allPapers.isEmpty()) {
            console.getRenderer().renderMessage("No research papers found in the university.");
            console.getInput().waitForEnter();
            return;
        }

        console.getRenderer().renderHeader("All University Papers");
        console.getRenderer().renderMenu(java.util.List.of("Sort by Date", "Sort by Citations", "Sort by Length"));
        int sortChoice = console.getInput().read("Sort Option", new parsers.IntegerParser(0, 3));
        
        Comparator<ResearchPaper> comparator = switch (sortChoice) {
            case 1 -> Comparator.comparing(ResearchPaper::getDatePublished);
            case 2 -> Comparator.comparingInt(ResearchPaper::getCitations).reversed();
            case 3 -> Comparator.comparingInt(ResearchPaper::getArticleLength).reversed();
            default -> null;
        };

        if (comparator != null) {
            allPapers.sort(comparator);
            for (ResearchPaper p : allPapers) {
                console.getRenderer().renderData(p.getName(), String.format("Citations: %d | Journal: %s", p.getCitations(), p.getJournal()));
            }
        }
        console.getInput().waitForEnter();
    }

    private void viewTopResearchers() {
        List<IResearcher> researchers = new ArrayList<>();
        for (users.User u : core.UniversityKernel.getInstance().getUsers()) {
            if (u instanceof IResearcher) {
                researchers.add((IResearcher) u);
            }
        }

        if (researchers.isEmpty()) {
            console.getRenderer().renderMessage("No researchers found.");
            console.getInput().waitForEnter();
            return;
        }

        researchers.sort(Comparator.comparingInt(IResearcher::getHIndex).reversed());
        console.getRenderer().renderHeader("Top Cited Researchers (h-index)");
        for (int i = 0; i < Math.min(researchers.size(), 5); i++) {
            IResearcher r = researchers.get(i);
            String name = (r instanceof users.User) ? ((users.User) r).getFullName() : "Unknown";
            console.getRenderer().renderData((i + 1) + ". " + name, "h-index: " + r.getHIndex());
        }
        console.getInput().waitForEnter();
    }

    private void joinProject() {
        IResearcher researcher = getResearcher();
        if (researcher == null) {
            console.getRenderer().renderError("You must be a researcher to join projects.");
            return;
        }

        List<research.ResearchProject> projects = core.UniversityKernel.getInstance().getResearchProjects();
        if (projects.isEmpty()) {
            console.getRenderer().renderMessage("No research projects available at the moment.");
            console.getInput().waitForEnter();
            return;
        }

        console.getRenderer().renderHeader("Available Research Projects");
        for (int i = 0; i < projects.size(); i++) {
            console.getRenderer().renderData((i + 1) + ". " + projects.get(i).getTopic(), "");
        }

        int choice = console.getInput().read("Select project to join (0 to cancel)", new parsers.IntegerParser(0, projects.size()));
        if (choice == 0) return;

        research.ResearchProject selected = projects.get(choice - 1);
        try {
            selected.addParticipant(researcher);
            console.getRenderer().renderMessage("Successfully joined project: " + selected.getTopic());
        } catch (exceptions.NonResearcherException e) {
            console.getRenderer().renderError(e.getMessage());
        }
        console.getInput().waitForEnter();
    }

    private void viewPapers() {
        IResearcher researcher = getResearcher();
        if (researcher == null) {
            console.getRenderer().renderError("You are not a registered researcher.");
            return;
        }

        console.getRenderer().renderHeader("My Research Papers");
        List<ResearchPaper> papers = researcher.getPapers();
        if (papers.isEmpty()) {
            console.getRenderer().renderMessage("No papers published yet.");
        } else {
            for (ResearchPaper p : papers) {
                console.getRenderer().renderData(p.getName(), 
                    String.format("Journal: %s | Citations: %d | Date: %s", 
                    p.getJournal(), p.getCitations(), p.getDatePublished()));
            }
        }
        console.getInput().waitForEnter();
    }

    private void viewSupervisor() {
        if (!(console.getCurrentUser() instanceof Student)) {
            console.getRenderer().renderError("Only students have research supervisors.");
            return;
        }

        Student student = (Student) console.getCurrentUser();
        IResearcher supervisor = student.getResearchSupervisor();
        console.getRenderer().renderHeader("Research Supervisor");
        if (supervisor == null) {
            console.getRenderer().renderMessage("No supervisor assigned.");
        } else {
            console.getRenderer().renderData("Supervisor", "h-index: " + supervisor.getHIndex());
        }
        console.getInput().waitForEnter();
    }

    private void sortPapersMenu() {
        IResearcher researcher = getResearcher();
        if (researcher == null) return;

        console.getRenderer().renderHeader("Sort Papers By");
        console.getRenderer().renderMenu(List.of("Date Published", "Citations", "Article Length (Pages)"));
        int choice = console.getInput().read("Option", new IntegerParser(0, 3));
        if (choice == 0) return;

        Comparator<ResearchPaper> comparator = null;
        switch (choice) {
            case 1: comparator = Comparator.comparing(ResearchPaper::getDatePublished); break;
            case 2: comparator = Comparator.comparingInt(ResearchPaper::getCitations).reversed(); break;
            case 3: comparator = Comparator.comparingInt(ResearchPaper::getArticleLength).reversed(); break;
        }

        if (comparator != null) {
            researcher.printPapers(comparator);
        }
        console.getInput().waitForEnter();
    }

    private IResearcher getResearcher() {
        if (console.getCurrentUser() instanceof IResearcher) {
            return (IResearcher) console.getCurrentUser();
        }
        return null;
    }
}

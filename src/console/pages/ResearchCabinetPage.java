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
        addAction("Cite a Paper", this::citePaper);
        addAction("Manage Paper Authors", this::manageAuthors);
        addAction("View Citation Format", this::viewCitationFormat);
        addAction("University Research Info", this::universityResearchMenu);
    }

    private void viewCitationFormat() {
        IResearcher researcher = getResearcher();
        if (researcher == null) return;

        List<ResearchPaper> papers = researcher.getPapers();
        if (papers.isEmpty()) {
            console.getRenderer().renderError("No papers to format.");
            return;
        }

        console.getRenderer().renderHeader("Select Paper for Formatting");
        for (int i = 0; i < papers.size(); i++) console.getRenderer().renderData((i+1) + ". " + papers.get(i).getName(), "");
        int choice = console.getInput().read("Option", new parsers.IntegerParser(0, papers.size()));
        if (choice == 0) return;

        ResearchPaper selected = papers.get(choice - 1);
        console.getRenderer().renderHeader("Citation Formats for: " + selected.getName());
        console.getRenderer().renderData("APA", selected.getCitationFormat(false));
        console.getRenderer().renderData("BibTeX", selected.getCitationFormat(true));
        console.getInput().waitForEnter();
    }

    private void citePaper() {
        IResearcher researcher = getResearcher();
        if (researcher == null) return;

        List<ResearchPaper> myPapers = researcher.getPapers();
        if (myPapers.isEmpty()) {
            console.getRenderer().renderError("You need at least one paper to cite others.");
            return;
        }

        List<ResearchPaper> allPapers = new ArrayList<>();
        for (users.User u : core.UniversityKernel.getInstance().getUsers()) {
            if (u instanceof IResearcher) allPapers.addAll(((IResearcher) u).getPapers());
        }

        if (allPapers.isEmpty()) {
            console.getRenderer().renderMessage("No papers available in the university.");
            return;
        }

        console.getRenderer().renderHeader("Select Your Paper (the citing one)");
        for (int i = 0; i < myPapers.size(); i++) console.getRenderer().renderData((i+1) + ". " + myPapers.get(i).getName(), "");
        int myChoice = console.getInput().read("Option", new parsers.IntegerParser(0, myPapers.size()));
        if (myChoice == 0) return;

        console.getRenderer().renderHeader("Select Paper to Cite");
        for (int i = 0; i < allPapers.size(); i++) console.getRenderer().renderData((i+1) + ". " + allPapers.get(i).getName(), "");
        int targetChoice = console.getInput().read("Option", new parsers.IntegerParser(0, allPapers.size()));
        if (targetChoice == 0) return;

        allPapers.get(targetChoice - 1).addCitation(myPapers.get(myChoice - 1));
        console.getRenderer().renderMessage("Citation added successfully!");
        console.getInput().waitForEnter();
    }

    private void manageAuthors() {
        IResearcher researcher = getResearcher();
        if (researcher == null) return;

        List<ResearchPaper> myPapers = researcher.getPapers();
        if (myPapers.isEmpty()) {
            console.getRenderer().renderError("You have no papers.");
            return;
        }

        console.getRenderer().renderHeader("Select Paper to Manage Authors");
        for (int i = 0; i < myPapers.size(); i++) console.getRenderer().renderData((i+1) + ". " + myPapers.get(i).getName(), "");
        int choice = console.getInput().read("Option", new parsers.IntegerParser(0, myPapers.size()));
        if (choice == 0) return;

        ResearchPaper selected = myPapers.get(choice - 1);
        String newAuthor = console.getInput().readString("Enter new author name");
        selected.addAuthor(newAuthor);
        console.getRenderer().renderMessage("Author added!");
        console.getInput().waitForEnter();
    }

    private void universityResearchMenu() {
        console.getRenderer().renderHeader("University Research Global Info");
        
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
            if (u instanceof IResearcher) researchers.add((IResearcher) u);
        }

        if (researchers.isEmpty()) {
            console.getRenderer().renderMessage("No researchers found.");
            console.getInput().waitForEnter();
            return;
        }

        console.getRenderer().renderHeader("Top Researchers Selection");
        console.getRenderer().renderMenu(java.util.List.of("Global (All Schools)", "By Specific School", "By Specific Year (among all schools)"));
        int choice = console.getInput().read("Option", new parsers.IntegerParser(0, 3));

        if (choice == 2) {
            String school = console.getInput().readString("Enter School/Department Name (e.g., FIT, BS)");
            researchers.removeIf(r -> {
                if (r instanceof users.Employee) return !((users.Employee) r).getDepartment().equalsIgnoreCase(school);
                return true;
            });
        } else if (choice == 3) {
            int year = console.getInput().read("Enter Year", new parsers.IntegerParser(1800, 2026));
            researchers.sort((r1, r2) -> {
                int c1 = r1.getPapers().stream()
                        .filter(p -> p.getDatePublished().getYear() == year)
                        .mapToInt(ResearchPaper::getCitations)
                        .sum();
                int c2 = r2.getPapers().stream()
                        .filter(p -> p.getDatePublished().getYear() == year)
                        .mapToInt(ResearchPaper::getCitations)
                        .sum();
                return Integer.compare(c2, c1);
            });
            console.getRenderer().renderHeader("Top Cited Researchers of " + year);
            for (int i = 0; i < Math.min(researchers.size(), 5); i++) {
                IResearcher r = researchers.get(i);
                int totalCit = r.getPapers().stream()
                        .filter(p -> p.getDatePublished().getYear() == year)
                        .mapToInt(ResearchPaper::getCitations)
                        .sum();
                String name = (r instanceof users.User) ? ((users.User) r).getFullName() : "Unknown";
                console.getRenderer().renderData((i + 1) + ". " + name, "Citations in " + year + ": " + totalCit);
            }
            console.getInput().waitForEnter();
            return;
        }

        if (researchers.isEmpty()) {
            console.getRenderer().renderError("No researchers found for this criteria.");
            console.getInput().waitForEnter();
            return;
        }

        researchers.sort(Comparator.comparingInt(IResearcher::getHIndex).reversed());
        console.getRenderer().renderHeader("Top Cited Researchers");
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
        users.User user = console.getCurrentUser();
        if (user instanceof users.Student) {
            return ((users.Student) user).getResearchComponent();
        }
        if (user instanceof users.Employee) {
            return ((users.Employee) user).getResearchComponent();
        }
        return null;
    }
}

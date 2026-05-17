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
        addAction("Add Paper", this::addPaper);
        addAction("View Supervisor", this::viewSupervisor);
        addAction("Sort My Papers", this::sortPapersMenu);
        addAction("Join Research Project", this::joinProject);
        addAction("Publish Paper to Project", this::publishPaperToProject);
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
            if (u instanceof IResearcher && ((IResearcher) u).getHIndex() > 0) {
                researchers.add((IResearcher) u);
            }
            if (u instanceof users.Student) {
                research.ResearchDecorator rc = ((users.Student) u).getResearchComponent();
                if (rc != null && !researchers.contains(rc) && rc.getHIndex() > 0) {
                }
            }
        }

        if (researchers.isEmpty()) {
            console.getRenderer().renderMessage("No researchers with published papers found.");
            console.getInput().waitForEnter();
            return;
        }

        console.getRenderer().renderHeader("Top Researchers Selection");
        console.getRenderer().renderMenu(java.util.List.of("Global (All Schools)", "By Specific School/Department"));
        int choice = console.getInput().read("Option", new parsers.IntegerParser(0, 2));

        List<IResearcher> filtered = new ArrayList<>(researchers);
        if (choice == 2) {
            String school = console.getInput().readString("Enter School/Department Name (e.g., FIT, BS)");
            filtered.removeIf(r -> {
                if (r instanceof users.Employee) {
                    return !((users.Employee) r).getDepartment().equalsIgnoreCase(school);
                }
                return true;
            });
        }

        if (filtered.isEmpty()) {
            console.getRenderer().renderError("No researchers found for this criteria.");
            console.getInput().waitForEnter();
            return;
        }

        filtered.sort(Comparator.comparingInt(IResearcher::getHIndex).reversed());
        console.getRenderer().renderHeader("Top Cited Researchers");
        for (int i = 0; i < Math.min(filtered.size(), 5); i++) {
            IResearcher r = filtered.get(i);
            String name = (r instanceof users.User) ? ((users.User) r).getFullName() : "Unknown";
            console.getRenderer().renderData((i + 1) + ". " + name,
                    "h-index: " + r.getHIndex() + " | Papers: " + r.getPapers().size());
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

    private void addPaper() {
        IResearcher researcher = getResearcher();
        if (researcher == null) {
            console.getRenderer().renderError("You are not a registered researcher.");
            console.getInput().waitForEnter();
            return;
        }

        console.getRenderer().renderHeader("Add Research Paper");
        String name = console.getInput().readString("Paper Title");
        String journal = console.getInput().readString("Journal Name");
        String doi = console.getInput().readString("DOI (e.g. 10.1234/example)");

        int pages = console.getInput().read("Number of Pages", new parsers.IntegerParser(1, 10000));
        int citations = console.getInput().read("Initial Citations", new parsers.IntegerParser(0, 100000));

        console.getRenderer().renderMessage("Enter publication date:");
        int year = console.getInput().read("Year", new parsers.IntegerParser(1900, 2100));
        int month = console.getInput().read("Month (1-12)", new parsers.IntegerParser(1, 12));
        int day = console.getInput().read("Day (1-31)", new parsers.IntegerParser(1, 31));
        java.time.LocalDate datePublished;
        try {
            datePublished = java.time.LocalDate.of(year, month, day);
        } catch (Exception e) {
            console.getRenderer().renderError("Invalid date. Using today.");
            datePublished = java.time.LocalDate.now();
        }

        java.util.List<String> authors = new java.util.ArrayList<>();
        String selfName = ((users.User) console.getCurrentUser()).getFullName();
        authors.add(selfName);
        console.getRenderer().renderMessage("Author '" + selfName + "' added automatically.");
        while (true) {
            String author = console.getInput().readString("Add co-author name (or 'done' to finish)");
            if (author.equalsIgnoreCase("done")) break;
            authors.add(author);
            console.getRenderer().renderMessage("Added: " + author);
        }

        research.ResearchPaper paper = new research.ResearchPaper(name, authors, journal, pages, datePublished, citations, doi);
        researcher.addPaper(paper);
        console.getRenderer().renderSuccess("Paper '" + name + "' added to your profile!");
        console.getInput().waitForEnter();
    }

    private void publishPaperToProject() {
        IResearcher researcher = getResearcher();
        if (researcher == null) {
            console.getRenderer().renderError("You are not a registered researcher.");
            console.getInput().waitForEnter();
            return;
        }

        java.util.List<research.ResearchPaper> myPapers = researcher.getPapers();
        if (myPapers.isEmpty()) {
            console.getRenderer().renderError("You have no papers to publish. Add a paper first.");
            console.getInput().waitForEnter();
            return;
        }

        java.util.List<research.ResearchProject> projects = core.UniversityKernel.getInstance().getResearchProjects();
        if (projects.isEmpty()) {
            console.getRenderer().renderError("No research projects exist in the university.");
            console.getInput().waitForEnter();
            return;
        }

        java.util.List<research.ResearchProject> myProjects = new java.util.ArrayList<>();
        for (research.ResearchProject p : projects) {
            if (p.getParticipants().contains(researcher)) myProjects.add(p);
        }

        if (myProjects.isEmpty()) {
            console.getRenderer().renderError("You are not a participant in any project. Join a project first.");
            console.getInput().waitForEnter();
            return;
        }

        console.getRenderer().renderHeader("Select Project to Publish In");
        for (int i = 0; i < myProjects.size(); i++) {
            console.getRenderer().renderData((i + 1) + ". " + myProjects.get(i).getTopic(),
                    "Published papers: " + myProjects.get(i).getPublishedPapers().size());
        }
        int projChoice = console.getInput().read("Select project (0 to cancel)", new parsers.IntegerParser(0, myProjects.size()));
        if (projChoice == 0) return;
        research.ResearchProject project = myProjects.get(projChoice - 1);

        console.getRenderer().renderHeader("Select Paper to Publish");
        for (int i = 0; i < myPapers.size(); i++) {
            console.getRenderer().renderData((i + 1) + ". " + myPapers.get(i).getName(),
                    "Journal: " + myPapers.get(i).getJournal());
        }
        int paperChoice = console.getInput().read("Select paper (0 to cancel)", new parsers.IntegerParser(0, myPapers.size()));
        if (paperChoice == 0) return;

        research.ResearchPaper selected = myPapers.get(paperChoice - 1);
        project.publishPaper(selected);
        console.getRenderer().renderSuccess("Paper '" + selected.getName() + "' published to project: " + project.getTopic());
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
            String name = (supervisor instanceof users.User) ? ((users.User) supervisor).getFullName() : "Unknown";
            console.getRenderer().renderData("Name", name);
            console.getRenderer().renderData("H-Index", String.valueOf(supervisor.getHIndex()));
            console.getRenderer().renderData("Papers", String.valueOf(supervisor.getPapers().size()));
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
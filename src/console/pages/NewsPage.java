package console.pages;

import console.pagescore.Page;
import core.UniversityKernel;
import infrastructure.NewsEntry;

public class NewsPage extends Page {
    public NewsPage() {
        super("University News");
        addAction("View News", this::viewNews);
    }

    private void viewNews() {
        var newsList = UniversityKernel.getInstance().getNews();
        console.getRenderer().renderHeader("Latest News");
        
        if (newsList.isEmpty()) {
            console.getRenderer().renderMessage("No news available at the moment.");
        } else {
            for (NewsEntry entry : newsList) {
                console.getRenderer().renderMessage("[" + entry.getDatePosted() + "] " + entry.getTitle());
                System.out.println("    " + entry.getContent());
                console.getRenderer().renderDivider();
            }
        }
        console.getInput().waitForEnter();
    }
}

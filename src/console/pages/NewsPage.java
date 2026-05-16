package console.pages;

import console.pagescore.Page;

public class NewsPage extends Page {
    public NewsPage() {
        super("News");
        addAction("View News", () -> { console.getRenderer().renderMessage("Тут должны быть новости"); });
        addAction("Back", () -> {});
    }
}

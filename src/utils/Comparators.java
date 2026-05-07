package utils;

import java.util.Comparator;
import research.ResearchPaper;

public class Comparators {
    public static class ArticleLengthSorter implements Comparator<ResearchPaper> {
        @Override
        public int compare(ResearchPaper p1, ResearchPaper p2) {
            return Integer.compare(p1.getArticleLength(), p2.getArticleLength());
        }
    }

    public static class DatePublishedSorter implements Comparator<ResearchPaper> {
        @Override
        public int compare(ResearchPaper p1, ResearchPaper p2) {
            return p1.getDatePublished().compareTo(p2.getDatePublished());
        }
    }

    public static class CitationSorter implements Comparator<ResearchPaper> {
        @Override
        public int compare(ResearchPaper p1, ResearchPaper p2) {
            return Integer.compare(p1.getCitations(), p2.getCitations());
        }
    }
}

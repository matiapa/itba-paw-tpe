package ar.edu.itba.paw.models.ui;

public class BreadcrumbItem {

    private final String title, url;

    public BreadcrumbItem(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

}

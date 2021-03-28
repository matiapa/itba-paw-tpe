package ar.edu.itba.paw.models.ui;

public class Panel {

    private final String title;
    private final String moreLink;
    private final String viewName;

    public Panel(String title, String moreLink, String viewName) {
        this.title = title;
        this.moreLink = moreLink;
        this.viewName = viewName;
    }

    public String getTitle() {
        return title;
    }

    public String getMoreLink() {
        return moreLink;
    }

    public String getViewName() {
        return viewName;
    }

}

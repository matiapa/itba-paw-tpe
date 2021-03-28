package ar.edu.itba.paw.models.ui;

public class MainParams {

    private final String screenTitle;
    private final Iterable<BreadcrumbItem> breadcrumbItems;
    private final Panel panel1, panel2, panel3, panel4;

    public MainParams(String screenTitle, Iterable<BreadcrumbItem> breadcrumbItems,
                      Panel panel1, Panel panel2, Panel panel3, Panel panel4) {
        this.screenTitle = screenTitle;
        this.breadcrumbItems = breadcrumbItems;
        this.panel1 = panel1;
        this.panel2 = panel2;
        this.panel3 = panel3;
        this.panel4 = panel4;
    }

    public String getScreenTitle() {
        return screenTitle;
    }

    public Panel getPanel1() {
        return panel1;
    }

    public Panel getPanel2() {
        return panel2;
    }

    public Panel getPanel3() {
        return panel3;
    }

    public Panel getPanel4() {
        return panel4;
    }

    public Iterable<BreadcrumbItem> getBreadcrumbItems() {
        return breadcrumbItems;
    }
}

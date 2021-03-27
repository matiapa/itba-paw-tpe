package ar.edu.itba.paw.webapp.viewparams;

public class MainParams {

    final String screenTitle;
    final String panel1Title, panel2Title;
    final String pollsTitle, announcementsTitle;

    public MainParams(String screenTitle, String panel1Title, String panel2Title,
                      String pollsTitle, String announcementsTitle) {
        this.screenTitle = screenTitle;
        this.panel1Title = panel1Title;
        this.panel2Title = panel2Title;
        this.pollsTitle = pollsTitle;
        this.announcementsTitle = announcementsTitle;
    }

    public String getScreenTitle() {
        return screenTitle;
    }

    public String getPanel1Title() {
        return panel1Title;
    }

    public String getPanel2Title() {
        return panel2Title;
    }

    public String getPollsTitle() {
        return pollsTitle;
    }

    public String getAnnouncementsTitle() {
        return announcementsTitle;
    }
}

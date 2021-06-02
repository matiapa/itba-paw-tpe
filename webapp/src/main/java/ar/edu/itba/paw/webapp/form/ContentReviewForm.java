package ar.edu.itba.paw.webapp.form;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ContentReviewForm {

    @NotNull
    @Size(min = 1, max = 500)
    private String reviewBody;

    public String getReviewBody() {
        return reviewBody;
    }

    public void setReviewBody(String reviewBody) {
        this.reviewBody = reviewBody;
    }
}

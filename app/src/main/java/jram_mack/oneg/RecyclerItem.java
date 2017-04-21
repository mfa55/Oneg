package jram_mack.oneg;

/**
 * Created by white_000 on 3/28/2017.
 */

public class RecyclerItem {
    private String Title;
    private String Description;

    public RecyclerItem(String title, String description) {
        Title = title;
        Description = description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}

package jram_mack.oneg;




/**
 *
 * Java Object containing the card view's content
 * @author  JRAM-MACK
 * @author  CMPS253
 * @since 2/11/2017
 *
 * @version 1.0
 *
 */
public class RecyclerItem {
    private String Title;
    private String Description;

    /**
     *
     * @param title : title of the card view
     * @param description : description inside the card view
     */
    public RecyclerItem(String title, String description) {
        Title = title;
        Description = description;
    }

    /**
     *
     * @return the title of the card view
     */
    public String getTitle() {
        return Title;
    }

    /**
     *
     * @param title : string representing the title of the card view
     */
    public void setTitle(String title) {
        Title = title;
    }


    /**
     *
     * @return the description of the card view
     */
    public String getDescription() {
        return Description;
    }

    /**
     *
     * @param description string representing the description of the card view
     */
    public void setDescription(String description) {
        Description = description;
    }
}

package m2tp.serietemp.models;

public class Serie {
    private Long id;
    private String title;
    private String description;

    public Serie() {

    }

    public Serie(Long id, String title, String description){
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

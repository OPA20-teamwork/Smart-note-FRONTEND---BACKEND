package Files;

public class Note {
    private int id;
    private String text;
    private int date;
    private String imageUrl;
    private String title;

    public Note() {

    }



    public Note(String text, int date, String imageUrl, String title) {
        this.text = text;
        this.date = date;
        this.imageUrl = imageUrl;
        this.title = title;
    }

    public Note(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", imageUrl='" + imageUrl + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}

package Files;

import java.util.List;

public class Note {
    private int id;
    private String text;
    private String title;

    public Note() {

    }



    public Note(String text, String title) {
        this.text = text;
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
                ", title='" + title + '\'' +
                '}';
    }
}

package Files;

public class File {
    private int id;
    private String imageUrl;
    private int notesID;

    public File() {
    }

    public File(String imageUrl, int notesID) {
        this.imageUrl = imageUrl;
        this.notesID = notesID;
    }

    public File(int id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.notesID = notesID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getNotesID() {
        return notesID;
    }

    public void setNotesID(int notesID) {
        this.notesID = notesID;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", imageUrl='" + imageUrl + '\'' +
                ", notesID=" + notesID +
                '}';
    }
}


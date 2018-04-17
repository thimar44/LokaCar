package fr.eni.lokacar.lokacar.been;

public class Photo {
    private int id;
    private String uri;

    public Photo() {
    }

    public Photo(String uri) {
        this.uri = uri;
    }

    public Photo(int id, String uri) {
        this.id = id;
        this.uri = uri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", uri='" + uri + '\'' +
                '}';
    }
}

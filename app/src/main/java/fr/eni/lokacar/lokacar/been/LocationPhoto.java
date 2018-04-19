package fr.eni.lokacar.lokacar.been;

public class LocationPhoto {
    private int id;
    private Location location;
    private Photo photo;
    private String typeLocationPhoto;

    public LocationPhoto(int id, Location location, Photo photo, String typeLocationPhoto) {
        this.id = id;
        this.location = location;
        this.photo = photo;
        this.typeLocationPhoto = typeLocationPhoto;
    }

    public LocationPhoto(Location location, Photo photo, String typeLocationPhoto) {
        this.location = location;
        this.photo = photo;
        this.typeLocationPhoto = typeLocationPhoto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public String getTypeLocationPhoto() {
        return typeLocationPhoto;
    }

    public void setTypeLocationPhoto(String typeLocationPhoto) {
        this.typeLocationPhoto = typeLocationPhoto;
    }
}

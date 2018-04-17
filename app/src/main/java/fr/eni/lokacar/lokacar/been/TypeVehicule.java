package fr.eni.lokacar.lokacar.been;

public class TypeVehicule {
    private int id;
    private String libelle;

    public TypeVehicule() {
    }

    public TypeVehicule(String libelle) {
        this.libelle = libelle;
    }

    public TypeVehicule(int id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return libelle;
    }
}

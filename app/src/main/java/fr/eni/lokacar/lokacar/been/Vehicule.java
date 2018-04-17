package fr.eni.lokacar.lokacar.been;

import java.util.List;

public class Vehicule {

    private int id;
    private Agence agence;
    private TypeVehicule typeVehicule;
    private TypeCarburant typeCarburant;
    private int kilometrage;
    private int prixJour;
    private boolean enLocation;
    private String designation;
    private String immatriculation;
    private List<Photo> photos;
    private Marque marque;

    public Vehicule() {
    }

    public Vehicule(Agence agence, TypeVehicule typeVehicule, TypeCarburant typeCarburant, int kilometrage, int prixJour, boolean enLocation, String designation, String immatriculation, List<Photo> photos, Marque marque) {
        this.agence = agence;
        this.typeVehicule = typeVehicule;
        this.typeCarburant = typeCarburant;
        this.kilometrage = kilometrage;
        this.prixJour = prixJour;
        this.enLocation = enLocation;
        this.designation = designation;
        this.immatriculation = immatriculation;
        this.photos = photos;
        this.marque = marque;
    }

    public Vehicule(int id, Agence agence, TypeVehicule typeVehicule, TypeCarburant typeCarburant, int kilometrage, int prixJour, boolean enLocation, String designation, String immatriculation, List<Photo> photos, Marque marque) {
        this.id = id;
        this.agence = agence;
        this.typeVehicule = typeVehicule;
        this.typeCarburant = typeCarburant;
        this.kilometrage = kilometrage;
        this.prixJour = prixJour;
        this.enLocation = enLocation;
        this.designation = designation;
        this.immatriculation = immatriculation;
        this.photos = photos;
        this.marque = marque;
    }

    public Vehicule(int id, Agence agence, TypeVehicule typeVehicule, TypeCarburant typeCarburant, int kilometrage, int prixJour, boolean enLocation, String designation, String immatriculation, Marque marque) {
        this.id = id;
        this.agence = agence;
        this.typeVehicule = typeVehicule;
        this.typeCarburant = typeCarburant;
        this.kilometrage = kilometrage;
        this.prixJour = prixJour;
        this.enLocation = enLocation;
        this.designation = designation;
        this.immatriculation = immatriculation;
        this.marque = marque;
    }

    public Vehicule(Agence agence, TypeVehicule typeVehicule, TypeCarburant typeCarburant, int kilometrage, int prixJour, boolean enLocation, String designation, String immatriculation, Marque marque) {
        this.agence = agence;
        this.typeVehicule = typeVehicule;
        this.typeCarburant = typeCarburant;
        this.kilometrage = kilometrage;
        this.prixJour = prixJour;
        this.enLocation = enLocation;
        this.designation = designation;
        this.immatriculation = immatriculation;
        this.marque = marque;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }

    public TypeVehicule getTypeVehicule() {
        return typeVehicule;
    }

    public void setTypeVehicule(TypeVehicule typeVehicule) {
        this.typeVehicule = typeVehicule;
    }

    public TypeCarburant getTypeCarburant() {
        return typeCarburant;
    }

    public void setTypeCarburant(TypeCarburant typeCarburant) {
        this.typeCarburant = typeCarburant;
    }

    public int getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(int kilometrage) {
        this.kilometrage = kilometrage;
    }

    public int getPrixJour() {
        return prixJour;
    }

    public void setPrixJour(int prixJour) {
        this.prixJour = prixJour;
    }

    public boolean isEnLocation() {
        return enLocation;
    }

    public void setEnLocation(boolean enLocation) {
        this.enLocation = enLocation;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}

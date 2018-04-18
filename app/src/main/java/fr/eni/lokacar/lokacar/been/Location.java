package fr.eni.lokacar.lokacar.been;

import java.util.Date;
import java.util.List;

import fr.eni.lokacar.lokacar.dao.ClientDao;

public class Location {
    private int id;
    private Client client;
    private Vehicule vehicule;
    private Date dateDebut;
    private Date dateFin;
    private int prix;
    private int kilometrageParcouru;
    private boolean etat; //true si véhicule non rendu et false si le véhicule a été retourné
    private List<Photo> photoEntrees;
    private List<Photo> photoSorties;

    public Location() {
    }


    public Location(Client client, Vehicule vehicule, Date dateDebut, Date dateFin, int kilometrageParcouru, boolean etat, int prix) {
        this.client = client;
        this.vehicule = vehicule;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.kilometrageParcouru = kilometrageParcouru;
        this.etat = etat;
        this.prix = prix;
    }

    public Location(int id, Client client, Vehicule vehicule, Date dateDebut, Date dateFin, int kilometrageParcouru, boolean etat, int prix) {
        this.id = id;
        this.client = client;
        this.vehicule = vehicule;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.kilometrageParcouru = kilometrageParcouru;
        this.etat = etat;
        this.prix = prix;
    }

    public Location(Client client, Vehicule vehicule, Date dateDebut, Date dateFin, int kilometrageParcouru, boolean etat, List<Photo> photoEntrees, List<Photo> photoSorties, int prix ) {
        this.client = client;
        this.vehicule = vehicule;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.kilometrageParcouru = kilometrageParcouru;
        this.etat = etat;
        this.photoEntrees = photoEntrees;
        this.photoSorties = photoSorties;
        this.prix = prix;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public int getKilometrageParcouru() {
        return kilometrageParcouru;
    }

    public void setKilometrageParcouru(int kilometrageParcouru) {  this.kilometrageParcouru = kilometrageParcouru;  }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public List<Photo> getPhotoEntrees() {
        return photoEntrees;
    }

    public void setPhotoEntrees(List<Photo> photoEntrees) {
        this.photoEntrees = photoEntrees;
    }

    public List<Photo> getPhotoSorties() {
        return photoSorties;
    }

    public void setPhotoSorties(List<Photo> photoSorties) {
        this.photoSorties = photoSorties;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getPrix() { return prix; }

    public void setPrix(int prix) { this.prix = prix; }
}

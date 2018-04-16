package fr.eni.lokacar.lokacar.been;

public class Agence {
    private int id;
    private int codePostal;
    private String nom;
    private String adresse;
    private String ville;

    public Agence() {
    }

    public Agence(int codePostal, String nom, String adresse, String ville) {
        this.codePostal = codePostal;
        this.nom = nom;
        this.adresse = adresse;
        this.ville = ville;
    }

    public Agence(int id, int codePostal, String nom, String adresse, String ville) {
        this.id = id;
        this.codePostal = codePostal;
        this.nom = nom;
        this.adresse = adresse;
        this.ville = ville;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}

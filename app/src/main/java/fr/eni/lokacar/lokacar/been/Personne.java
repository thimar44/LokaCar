package fr.eni.lokacar.lokacar.been;

public class Personne {
    private int id;
    private Agence agence;
    private String nom;
    private String prenom;
    private String identifiant;
    private String motDePasse;

    public Personne() {
    }

    public Personne(Agence agence, String nom, String prenom, String identifiant, String motDePasse) {
        this.agence = agence;
        this.nom = nom;
        this.prenom = prenom;
        this.identifiant = identifiant;
        this.motDePasse = motDePasse;
    }

    public Personne(int id, Agence agence, String nom, String prenom, String identifiant, String motDePasse) {
        this.id = id;
        this.agence = agence;
        this.nom = nom;
        this.prenom = prenom;
        this.identifiant = identifiant;
        this.motDePasse = motDePasse;
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}



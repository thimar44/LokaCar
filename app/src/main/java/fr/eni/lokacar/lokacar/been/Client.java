package fr.eni.lokacar.lokacar.been;

public class Client {
    private int id;
    private int codePostal;
    private int numeroTelephone;
    private String mail;
    private String nom;
    private String prenom;
    private String adresse;
    private String ville;

    public Client() {
    }

    public Client(int codePostal, int numeroTelephone, String mail, String nom, String prenom, String adresse, String ville) {
        this.codePostal = codePostal;
        this.numeroTelephone = numeroTelephone;
        this.mail = mail;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.ville = ville;
    }

    public Client(int id, int codePostal, int numeroTelephone, String mail, String nom, String prenom, String adresse, String ville) {
        this.id = id;
        this.codePostal = codePostal;
        this.numeroTelephone = numeroTelephone;
        this.mail = mail;
        this.nom = nom;
        this.prenom = prenom;
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

    public int getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(int numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

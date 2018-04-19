package fr.eni.lokacar.lokacar.helper;

public abstract class DataContract {
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    public final static String DATABASE_NAME = "lokacar.db";
    public final static int DATABASE_VERSION = 6;

    /**
     * TABLE AGENCE
     */
    public final static String AGENCE_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + "AGENCE ("
            + "ID INTEGER PRIMARY KEY, "
            + "CODEPOSTAL INTEGER,"
            + "NOM TEXT,"
            + "ADRESSE TEXT,"
            + "VILLE TEXT)";

    public final static String QUERY_DELETE_TABLE_AGENCE = "DROP TABLE IF EXISTS AGENCE";
    public final static String TABLE_AGENCE_NAME = "AGENCE";

    public final static String AGENCE_ID = "ID";
    public final static String AGENCE_CODEPOSTAL = "CODEPOSTAL";
    public final static String AGENCE_NOM = "NOM";
    public final static String AGENCE_ADRESSE = "ADRESSE";
    public final static String AGENCE_VILLE = "VILLE";

    /**
     * TABLE Client
     */
    public final static String CLIENT_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + "CLIENT ("
            + "ID INTEGER PRIMARY KEY, "
            + "CODEPOSTAL INTEGER,"
            + "NUMERO_TELEPHONE INTEGER,"
            + "ADRESSE TEXT,"
            + "VILLE TEXT,"
            + "NOM TEXT,"
            + "PRENOM TEXT,"
            + "MAIL TEXT"
            + ")";

    public final static String QUERY_DELETE_TABLE_CLIENT = "DROP TABLE IF EXISTS CLIENT";
    public final static String TABLE_CLIENT_NAME = "CLIENT";

    public final static String CLIENT_ID = "ID";
    public final static String CLIENT_CODEPOSTAL = "CODEPOSTAL";
    public final static String CLIENT_NOM = "NOM";
    public final static String CLIENT_PRENOM = "PRENOM";
    public final static String CLIENT_ADRESSE = "ADRESSE";
    public final static String CLIENT_VILLE = "VILLE";
    public final static String CLIENT_NUMERO_TELEPHONE = "NUMERO_TELEPHONE";
    public final static String CLIENT_MAIL = "MAIL";

    /**
     * TABLE Location
     */
    //TODO - VOIR POUR ENTREES_PHOTO et SORTIES_PHOTO
    public final static String LOCATION_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + "LOCATION ("
            + "ID INTEGER PRIMARY KEY, "
            + "IDCLIENT INTEGER, "
            + "PRIX INTEGER, "
            + "IDVEHICULE INTEGER, "
            + "DATE_DEBUT DATETIME,"
            + "DATE_FIN DATETIME,"
            + "KILOMETRAGE_PARCOURU INTEGER,"
            + "ETAT BOOLEAN"
            + ")";

    public final static String QUERY_DELETE_TABLE_LOCATION = "DROP TABLE IF EXISTS LOCATION";
    public final static String TABLE_LOCATION_NAME = "LOCATION";
    public final static String LOCATION_PRIX = "PRIX";
    public final static String LOCATION_ID = "ID";
    public final static String LOCATION_IDCLIENT = "IDCLIENT";
    public final static String LOCATION_IDVEHICULE = "IDVEHICULE";
    public final static String LOCATION_DATE_DEBUT = "DATE_DEBUT";
    public final static String LOCATION_DATE_FIN = "DATE_FIN";
    public final static String LOCATION_KILOMETRAGE_PARCOURU = "KILOMETRAGE_PARCOURU";
    public final static String LOCATION_ETAT = "ETAT";

    /**
     * TABLE Marque
     */
    public final static String MARQUE_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + "MARQUE ("
            + "ID INTEGER PRIMARY KEY, "
            + "LIBELLE TEXT"
            + ")";

    public final static String QUERY_DELETE_TABLE_MARQUE = "DROP TABLE IF EXISTS MARQUE";
    public final static String TABLE_MARQUE_NAME = "MARQUE";

    public final static String MARQUE_ID = "ID";
    public final static String MARQUE_LIBELLE = "LIBELLE";

    /**
     * TABLE Personne
     */
    public final static String PERSONNE_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + "PERSONNE ("
            + "ID INTEGER PRIMARY KEY, "
            + "IDAGENCE INTEGER,"
            + "NOM TEXT,"
            + "PRENOM TEXT,"
            + "IDENTIFIANT TEXT,"
            + "MOTDEPASSE TEXT"
            + ")";

    public final static String QUERY_DELETE_TABLE_PERSONNE = "DROP TABLE IF EXISTS PERSONNE";
    public final static String TABLE_PERSONNE_NAME = "PERSONNE";

    public final static String PERSONNE_ID = "ID";
    public final static String PERSONNE_IDAGENCE = "IDAGENCE";
    public final static String PERSONNE_NOM = "NOM";
    public final static String PERSONNE_PRENOM = "PRENOM";
    public final static String PERSONNE_IDENTIFIANT = "IDENTIFIANT";
    public final static String PERSONNE_MOTDEPASSE = "MOTDEPASSE";

    /**
     * TABLE Photo
     */
    public final static String PHOTO_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + "PHOTO ("
            + "ID INTEGER PRIMARY KEY, "
            + "URI TEXT"
            + ")";

    public final static String QUERY_DELETE_TABLE_PHOTO = "DROP TABLE IF EXISTS PHOTO";
    public final static String TABLE_PHOTO_NAME = "PHOTO";

    public final static String PHOTO_ID = "ID";
    public final static String PHOTO_URI = "URI";

    /**
     * TABLE TypeCarburant
     */
    public final static String TYPE_CARBURANT_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + "TYPE_CARBURANT ("
            + "ID INTEGER PRIMARY KEY, "
            + "LIBELLE TEXT"
            + ")";

    public final static String QUERY_DELETE_TABLE_TYPE_CARBURANT = "DROP TABLE IF EXISTS TYPE_CARBURANT";
    public final static String TABLE_TYPE_CARBURANT_NAME = "TYPE_CARBURANT";

    public final static String TYPE_CARBURANT_ID = "ID";
    public final static String TYPE_CARBURANT_LIBELLE = "LIBELLE";

    /**
     * TABLE TypeVehicule
     */
    public final static String TYPE_VEHICULE_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + "TYPE_VEHICULE ("
            + "ID INTEGER PRIMARY KEY, "
            + "LIBELLE TEXT"
            + ")";

    public final static String QUERY_DELETE_TABLE_TYPE_VEHICULE = "DROP TABLE IF EXISTS TYPE_VEHICULE";
    public final static String TABLE_TYPE_VEHICULE_NAME = "TYPE_VEHICULE";

    public final static String TYPE_VEHICULE_ID = "ID";
    public final static String TYPE_VEHICULE_LIBELLE = "LIBELLE";

    /**
     * TABLE Vehicule
     */

    public final static String VEHICULE_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + "VEHICULE ("
            + "ID INTEGER PRIMARY KEY, "
            + "IDAGENCE INTEGER,"
            + "IDTYPE_VEHICULE INTEGER,"
            + "IDTYPE_CARBURANT INTEGER,"
            + "ID_PHOTO INTEGER,"
            + "IDMARQUE INTEGER,"
            + "KILOMETRAGE INTEGER,"
            + "PRIXJOUR INTEGER,"
            + "ENLOCATION BOOLEAN,"
            + "DESIGNATION TEXT,"
            + "IMMATRICULATION TEXT"
            + ")";

    public final static String QUERY_DELETE_TABLE_VEHICULE = "DROP TABLE IF EXISTS VEHICULE";
    public final static String TABLE_VEHICULE_NAME = "VEHICULE";

    public final static String VEHICULE_ID = "ID";
    public final static String VEHICULE_IDAGENCE = "IDAGENCE";
    public final static String VEHICULE_IDPHOTO = "ID_PHOTO";
    public final static String VEHICULE_IDTYPE_VEHICULE = "IDTYPE_VEHICULE";
    public final static String VEHICULE_IDTYPE_CARBURANT = "IDTYPE_CARBURANT";
    public final static String VEHICULE_IDMARQUE = "IDMARQUE";
    public final static String VEHICULE_KILOMETRAGE = "KILOMETRAGE";
    public final static String VEHICULE_PRIXJOUR = "PRIXJOUR";
    public final static String VEHICULE_ENLOCATION = "ENLOCATION";
    public final static String VEHICULE_DESIGNATION = "DESIGNATION";
    public final static String VEHICULE_IMMATRICULATION = "IMMATRICULATION";

    /**
     * TABLE LocationPhoto
     */

    public final static String LOCATION_PHOTO_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + "LOCATION_PHOTO ("
            + "ID INTEGER PRIMARY KEY, "
            + "IDLOCATION INTEGER,"
            + "IDPHOTO INTEGER,"
            + "TYPE TEXT"
            + ")";

    public final static String QUERY_DELETE_TABLE_LOCATION_PHOTO = "DROP TABLE IF EXISTS LOCATION_PHOTO";
    public final static String TABLE_LOCATION_PHOTO_NAME = "LOCATION_PHOTO";

    public final static String LOCATION_PHOTO_ID = "ID";
    public final static String LOCATION_PHOTO_IDLOCATION = "IDLOCATION";
    public final static String LOCATION_PHOTO_IDPHOTO = "IDPHOTO";
    public final static String LOCATION_PHOTO_TYPE = "TYPE";
}
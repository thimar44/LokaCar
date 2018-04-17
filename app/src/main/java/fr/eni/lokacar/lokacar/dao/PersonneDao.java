package fr.eni.lokacar.lokacar.dao;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.eni.lokacar.lokacar.been.Agence;
import fr.eni.lokacar.lokacar.been.Personne;
import fr.eni.lokacar.lokacar.helper.DataContract;
import fr.eni.lokacar.lokacar.helper.ModeleHelper;

public class PersonneDao {
    private ModeleHelper dbHelper;
    private AgenceDao agenceDao;

    public PersonneDao(Context context) {
        this.dbHelper = new ModeleHelper(context);
        this.agenceDao = new AgenceDao(context);
    }

    /**
     * Create ContentValues with modele
     *
     * @param personne
     * @return
     */
    private ContentValues constructValuesDB(Personne personne) {
        ContentValues values = new ContentValues();
        values.put(DataContract.PERSONNE_ID, personne.getId());
        values.put(DataContract.PERSONNE_IDAGENCE, personne.getAgence().getId());
        values.put(DataContract.PERSONNE_NOM, personne.getNom());
        values.put(DataContract.PERSONNE_PRENOM, personne.getPrenom());
        values.put(DataContract.PERSONNE_IDENTIFIANT, personne.getIdentifiant());
        values.put(DataContract.PERSONNE_MOTDEPASSE, personne.getMotDePasse());

        return values;
    }

    public long insert(Personne personne) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long id = db.insert(DataContract.TABLE_PERSONNE_NAME, null, constructValuesDB(personne));

        db.close();

        return id;
    }

    public long insertOrUpdate(Personne personne) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = -1;
        Cursor c = db.query(DataContract.TABLE_PERSONNE_NAME, null,
                "ID=" + personne.getId(), null, null, null, null);

        if (c.getCount() > 0) {
            update(personne);
        } else {
            insert(personne);
        }

        db.close();

        return id;
    }

    public List<Personne> getListe() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DataContract.TABLE_PERSONNE_NAME, null,
                null,
                null,
                null,
                null,
                null);

        List<Personne> objects = new ArrayList<Personne>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DataContract.PERSONNE_ID));
                int idAgence = cursor.getInt(cursor.getColumnIndex(DataContract.PERSONNE_IDAGENCE));
                String nom = cursor.getString(cursor.getColumnIndex(DataContract.PERSONNE_NOM));
                String prenom = cursor.getString(cursor.getColumnIndex(DataContract.PERSONNE_PRENOM));
                String identifiant = cursor.getString(cursor.getColumnIndex(DataContract.PERSONNE_IDENTIFIANT));
                String motDePasse = cursor.getString(cursor.getColumnIndex(DataContract.PERSONNE_MOTDEPASSE));

                Agence agence = this.agenceDao.getAgenceFromId(idAgence);
                objects.add(new Personne(id, agence, nom, prenom, identifiant, motDePasse));

            } while (cursor.moveToNext());

            cursor.close();
        }

        return objects;
    }

    public Personne getPersonneFromId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DataContract.TABLE_PERSONNE_NAME, null,
                "ID = " + id,
                null,
                null,
                null,
                null);

        Personne object = null;

        if (cursor != null && cursor.moveToFirst()) {

            int idPersonne = cursor.getInt(cursor.getColumnIndex(DataContract.PERSONNE_ID));
            int idAgence = cursor.getInt(cursor.getColumnIndex(DataContract.PERSONNE_IDAGENCE));
            String nom = cursor.getString(cursor.getColumnIndex(DataContract.PERSONNE_NOM));
            String prenom = cursor.getString(cursor.getColumnIndex(DataContract.PERSONNE_PRENOM));
            String identifiant = cursor.getString(cursor.getColumnIndex(DataContract.PERSONNE_IDENTIFIANT));
            String motDePasse = cursor.getString(cursor.getColumnIndex(DataContract.PERSONNE_MOTDEPASSE));

            Agence agence = this.agenceDao.getAgenceFromId(idAgence);
            object = new Personne(idPersonne, agence, nom, prenom, identifiant, motDePasse);
            cursor.close();
        }

        return object;
    }


    public void update(int id, Personne personne) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update(DataContract.TABLE_PERSONNE_NAME, constructValuesDB(personne),
                "ID=" + id,
                null);
        db.close();

    }

    public void update(Personne personne) {
        update(personne.getId(), personne);
    }


    public boolean isRegistered(String login, String password){
        boolean registered = false;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query(DataContract.TABLE_PERSONNE_NAME, null,
                "IDENTIFIANT=\"" + login + "\" AND MOTDEPASSE = \"" + password + "\"", null,null,null,null);
        if(c.getCount() > 0){
            registered = true;
        }
        db.close();
        return registered;
    }

}

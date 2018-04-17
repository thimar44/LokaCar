package fr.eni.lokacar.lokacar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fr.eni.lokacar.lokacar.been.Personne;
import fr.eni.lokacar.lokacar.helper.DataContract;
import fr.eni.lokacar.lokacar.helper.ModeleHelper;

public class PersonneDao {
    private ModeleHelper dbHelper;



    public PersonneDao(Context context) {
        this.dbHelper = new ModeleHelper(context);

    }

    /**
     * Create ContentValues with modele
     * @param personne
     * @return
     */
    private ContentValues constructValuesDB(Personne personne) {
        ContentValues values = new ContentValues();
        values.put(DataContract.PERSONNE_ID , personne.getId());
        values.put(DataContract.PERSONNE_NOM , personne.getNom());
        values.put(DataContract.PERSONNE_PRENOM ,personne.getPrenom());
        values.put(DataContract.PERSONNE_IDENTIFIANT , personne.getIdentifiant());
        values.put(DataContract.PERSONNE_MOTDEPASSE , personne.getMotDePasse());
        values.put(DataContract.PERSONNE_IDAGENCE, (personne.getAgence().getId()));
        return values;
    }


    public long insert(Personne personne){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = db.insert(DataContract.TABLE_PERSONNE_NAME, null, constructValuesDB(personne));
        db.close();
        return id;
    }

    public long insertOrUpdate(Personne personne){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = -1;
        Cursor c = db.query(DataContract.TABLE_PERSONNE_NAME, null,
                "ID="+personne.getId(), null,null,null,null);

        if(c.getCount() > 0){
            update(personne);
        }
        else {
            insert(personne);
        }

        db.close();

        return id;
    }


    public void update(int id, Personne personne) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update(DataContract.TABLE_PERSONNE_NAME, constructValuesDB(personne),
                "ID=" + id,
                null);
        db.close();

    }

    public void update( Personne personne) {
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

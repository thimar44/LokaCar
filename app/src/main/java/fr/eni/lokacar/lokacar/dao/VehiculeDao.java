package fr.eni.lokacar.lokacar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import junit.runner.BaseTestRunner;

import java.util.ArrayList;
import java.util.List;

import fr.eni.lokacar.lokacar.been.Agence;
import fr.eni.lokacar.lokacar.been.Marque;
import fr.eni.lokacar.lokacar.been.Photo;
import fr.eni.lokacar.lokacar.been.TypeCarburant;
import fr.eni.lokacar.lokacar.been.TypeVehicule;
import fr.eni.lokacar.lokacar.been.Vehicule;
import fr.eni.lokacar.lokacar.helper.DataContract;
import fr.eni.lokacar.lokacar.helper.ModeleHelper;

import static android.content.Context.MODE_PRIVATE;
import static fr.eni.lokacar.lokacar.helper.DataContract.MY_PREFS_NAME;

public class VehiculeDao {
    private ModeleHelper dbHelper;
    private AgenceDao agenceDao;
    private TypeVehiculeDao typeVehiculeDao;
    private TypeCarburantDao typeCarburantDao;
    private MarqueDao marqueDao;
    private PhotoDao photoDao;
    private SharedPreferences prefs;

    public VehiculeDao(Context context) {
        this.dbHelper = new ModeleHelper(context);
        this.agenceDao = new AgenceDao(context);
        this.typeVehiculeDao = new TypeVehiculeDao(context);
        this.typeCarburantDao = new TypeCarburantDao(context);
        this.photoDao = new PhotoDao(context);
        this.marqueDao = new MarqueDao(context);
        prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
    }

    /**
     * Create ContentValues with modele
     *
     * @param vehicule
     * @return
     */
    //TODO -- Gerer les photos
    private ContentValues constructValuesDB(Vehicule vehicule) {
        ContentValues values = new ContentValues();
        //values.put(DataContract.VEHICULE_ID, vehicule.getId());
        values.put(DataContract.VEHICULE_IDAGENCE, vehicule.getAgence().getId());
        values.put(DataContract.VEHICULE_IDTYPE_VEHICULE, vehicule.getTypeVehicule().getId());
        values.put(DataContract.VEHICULE_IDTYPE_CARBURANT, vehicule.getTypeCarburant().getId());
        values.put(DataContract.VEHICULE_IDMARQUE, vehicule.getMarque().getId());
        values.put(DataContract.VEHICULE_KILOMETRAGE, vehicule.getKilometrage());
        values.put(DataContract.VEHICULE_PRIXJOUR, vehicule.getPrixJour());
        values.put(DataContract.VEHICULE_ENLOCATION, vehicule.isEnLocation());
        values.put(DataContract.VEHICULE_DESIGNATION, vehicule.getDesignation());
        values.put(DataContract.VEHICULE_IMMATRICULATION, vehicule.getImmatriculation());
        values.put(DataContract.VEHICULE_IDPHOTO, vehicule.getPhoto().getId());
        return values;
    }

    public long insert(Vehicule vehicule) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = -1;
        try {
            id = db.insert(DataContract.TABLE_VEHICULE_NAME, null, constructValuesDB(vehicule));
        } catch (SQLException e) {
            Log.v("SQL => ", e.getMessage());
        }
        db.close();
        return id;
    }

    public long insertOrUpdate(Vehicule vehicule) {
        long id = -1;
        if (vehicule.getId() > 0) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            Cursor c = db.query(DataContract.TABLE_VEHICULE_NAME, null,
                    "ID=" + vehicule.getId(), null, null, null, null);

            if (c.getCount() > 0) {
                update(vehicule);
            } else {
                id = insert(vehicule);
            }
            db.close();
        } else {
            id = insert(vehicule);
        }

        return id;
    }

    public List<Vehicule> getListe() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int idAgenceShared = prefs.getInt("idAgence", 0);

        Cursor cursor = db.query(
                DataContract.TABLE_VEHICULE_NAME, null,
                DataContract.VEHICULE_IDAGENCE + "=" + idAgenceShared,
                null,
                null,
                null,
                null);

        List<Vehicule> objects = new ArrayList<Vehicule>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                boolean isEnLocation = false;

                int id = cursor.getInt(cursor.getColumnIndex(DataContract.VEHICULE_ID));
                int idAgence = cursor.getInt(cursor.getColumnIndex(DataContract.VEHICULE_IDAGENCE));
                int idTypevehicule = cursor.getInt(cursor.getColumnIndex(DataContract.VEHICULE_IDTYPE_VEHICULE));
                int idTypeCarburant = cursor.getInt(cursor.getColumnIndex(DataContract.VEHICULE_IDTYPE_CARBURANT));
                int kilometrage = cursor.getInt(cursor.getColumnIndex(DataContract.VEHICULE_KILOMETRAGE));
                int prixJour = cursor.getInt(cursor.getColumnIndex(DataContract.VEHICULE_PRIXJOUR));
                int enLocation = cursor.getInt(cursor.getColumnIndex(DataContract.VEHICULE_ENLOCATION));
                if (enLocation == 1) {
                    isEnLocation = true;
                }
                String designation = cursor.getString(cursor.getColumnIndex(DataContract.VEHICULE_DESIGNATION));
                String immatriculation = cursor.getString(cursor.getColumnIndex(DataContract.VEHICULE_IMMATRICULATION));
                int idMarque = cursor.getInt(cursor.getColumnIndex(DataContract.VEHICULE_IDMARQUE));
                int idPhoto = cursor.getInt(cursor.getColumnIndex(DataContract.VEHICULE_IDPHOTO));

                Agence agence = agenceDao.getAgenceFromId(idAgence);
                TypeVehicule typeVehicule = typeVehiculeDao.getTypeVehiculeFromId(idTypevehicule);
                TypeCarburant typeCarburant = typeCarburantDao.getTypeCarburantFromId(idTypeCarburant);
                Marque marque = marqueDao.getMarqueFromId(idMarque);

                if(idPhoto > 0){
                    Photo photo = photoDao.getPhotoFromId(idPhoto);
                    objects.add(new Vehicule(id, agence, typeVehicule, typeCarburant, kilometrage, prixJour, isEnLocation, designation, immatriculation, photo, marque));
                }else{
                    objects.add(new Vehicule(id, agence, typeVehicule, typeCarburant, kilometrage, prixJour, isEnLocation, designation, immatriculation, marque));
                }
            } while (cursor.moveToNext());

            cursor.close();
        }
        return objects;
    }

    public Vehicule getVehiculeFromId(int id) {


        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DataContract.TABLE_VEHICULE_NAME, null,
                "ID=" + id,
                null,
                null,
                null,
                null);

        Vehicule object = null;

        if (cursor != null && cursor.moveToFirst()) {

            boolean isEnLocation = false;

            int idVehicule = cursor.getInt(cursor.getColumnIndex(DataContract.VEHICULE_ID));
            int idAgence = cursor.getInt(cursor.getColumnIndex(DataContract.VEHICULE_IDAGENCE));
            int idTypevehicule = cursor.getInt(cursor.getColumnIndex(DataContract.VEHICULE_IDTYPE_VEHICULE));
            int idTypeCarburant = cursor.getInt(cursor.getColumnIndex(DataContract.VEHICULE_IDTYPE_CARBURANT));
            int kilometrage = cursor.getInt(cursor.getColumnIndex(DataContract.VEHICULE_KILOMETRAGE));
            int prixJour = cursor.getInt(cursor.getColumnIndex(DataContract.VEHICULE_PRIXJOUR));
            int enLocation = cursor.getInt(cursor.getColumnIndex(DataContract.VEHICULE_ENLOCATION));
            if (enLocation == 1) {
                isEnLocation = true;
            }
            String designation = cursor.getString(cursor.getColumnIndex(DataContract.VEHICULE_DESIGNATION));
            String immatriculation = cursor.getString(cursor.getColumnIndex(DataContract.VEHICULE_IMMATRICULATION));
            int idMarque = cursor.getInt(cursor.getColumnIndex(DataContract.VEHICULE_IDMARQUE));
            int idPhoto = cursor.getInt(cursor.getColumnIndex(DataContract.VEHICULE_IDPHOTO));


            Agence agence = agenceDao.getAgenceFromId(idAgence);
            TypeVehicule typeVehicule = typeVehiculeDao.getTypeVehiculeFromId(idTypevehicule);
            TypeCarburant typeCarburant = typeCarburantDao.getTypeCarburantFromId(idTypeCarburant);
            Marque marque = marqueDao.getMarqueFromId(idMarque);

            if(idPhoto > 0){
                Photo photo = photoDao.getPhotoFromId(idPhoto);
                object = new Vehicule(idVehicule, agence, typeVehicule, typeCarburant, kilometrage, prixJour, isEnLocation, designation, immatriculation, photo, marque);
            }else{
                object = new Vehicule(idVehicule, agence, typeVehicule, typeCarburant, kilometrage, prixJour, isEnLocation, designation, immatriculation, marque);
            }


            cursor.close();
        }
        return object;
    }

    public void update(int id, Vehicule vehicule) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update(DataContract.TABLE_VEHICULE_NAME, constructValuesDB(vehicule),
                "ID=" + id,
                null);
        db.close();
    }

    public void update(Vehicule vehicule) {
        update(vehicule.getId(), vehicule);
    }

    public List<Vehicule> getListeWithParams(TypeVehicule typeVehicule, Marque marque, TypeCarburant typeCarburant, int etat) {

        int idAgenceShared = prefs.getInt("idAgence", 0);
        StringBuilder builder = new StringBuilder();
        builder.append( DataContract.VEHICULE_IDAGENCE + " = " + String.valueOf(idAgenceShared));

        if (typeCarburant != null) {
            builder.append(" AND " + DataContract.VEHICULE_IDTYPE_CARBURANT + " = " + String.valueOf(typeCarburant.getId()));
        }
        if (typeVehicule != null) {
            builder.append(" AND " + DataContract.VEHICULE_IDTYPE_VEHICULE + " = " + String.valueOf(typeVehicule.getId()));
        }
        if (marque != null) {
            builder.append(" AND " + DataContract.VEHICULE_IDMARQUE + " = " + String.valueOf(marque.getId()));
        }
        if(etat != -1){
            builder.append(" AND " + DataContract.VEHICULE_ENLOCATION + " = " + etat);
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DataContract.TABLE_VEHICULE_NAME, null,
                builder.toString(),
                null,
                null,
                null,
                null);

        List<Vehicule> objects = new ArrayList<Vehicule>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                boolean isEnLocation = false;

                int id = cursor.getInt(cursor.getColumnIndex(DataContract.VEHICULE_ID));
                int idAgence = cursor.getInt(cursor.getColumnIndex(DataContract.VEHICULE_IDAGENCE));
                int idTypevehicule = cursor.getInt(cursor.getColumnIndex(DataContract.VEHICULE_IDTYPE_VEHICULE));
                int idTypeCarburant = cursor.getInt(cursor.getColumnIndex(DataContract.VEHICULE_IDTYPE_CARBURANT));
                int kilometrage = cursor.getInt(cursor.getColumnIndex(DataContract.VEHICULE_KILOMETRAGE));
                int prixJour = cursor.getInt(cursor.getColumnIndex(DataContract.VEHICULE_PRIXJOUR));
                int enLocation = cursor.getInt(cursor.getColumnIndex(DataContract.VEHICULE_ENLOCATION));
                if (enLocation == 1) {
                    isEnLocation = true;
                }
                String designation = cursor.getString(cursor.getColumnIndex(DataContract.VEHICULE_DESIGNATION));
                String immatriculation = cursor.getString(cursor.getColumnIndex(DataContract.VEHICULE_IMMATRICULATION));
                int idMarque = cursor.getInt(cursor.getColumnIndex(DataContract.VEHICULE_IDMARQUE));

                Agence agence = agenceDao.getAgenceFromId(idAgence);
                TypeVehicule typeVehicule2 = typeVehiculeDao.getTypeVehiculeFromId(idTypevehicule);
                TypeCarburant typeCarburant2 = typeCarburantDao.getTypeCarburantFromId(idTypeCarburant);
                Marque marque2 = marqueDao.getMarqueFromId(idMarque);

                int idPhoto = cursor.getInt(cursor.getColumnIndex(DataContract.VEHICULE_IDPHOTO));
                if(idPhoto > 0){
                    Photo photo = photoDao.getPhotoFromId(idPhoto);
                    objects.add(new Vehicule(id, agence, typeVehicule2, typeCarburant2, kilometrage, prixJour, isEnLocation, designation, immatriculation, photo, marque2));
                }else{
                    objects.add(new Vehicule(id, agence, typeVehicule2, typeCarburant2, kilometrage, prixJour, isEnLocation, designation, immatriculation, marque2));
                }

            } while (cursor.moveToNext());

            cursor.close();
        }

        return objects;
    }
}


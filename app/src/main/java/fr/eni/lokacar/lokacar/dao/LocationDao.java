package fr.eni.lokacar.lokacar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.eni.lokacar.lokacar.been.Client;
import fr.eni.lokacar.lokacar.been.Location;
import fr.eni.lokacar.lokacar.been.Marque;
import fr.eni.lokacar.lokacar.been.Vehicule;
import fr.eni.lokacar.lokacar.helper.DataContract;
import fr.eni.lokacar.lokacar.helper.ModeleHelper;

public class LocationDao {
    private ModeleHelper dbHelper;
    private ClientDao clientDao;
    private VehiculeDao vehiculeDao;

    public LocationDao(Context context) {
        this.dbHelper = new ModeleHelper(context);
        this.clientDao = new ClientDao(context);
        this.vehiculeDao = new VehiculeDao(context);
    }

    /**
     * Create ContentValues with modele
     *
     * @param location
     * @return
     */
    private ContentValues constructValuesDB(Location location) {
        ContentValues values = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        values.put(DataContract.LOCATION_PRIX, location.getPrix());
        values.put(DataContract.LOCATION_IDCLIENT, location.getClient().getId());
        values.put(DataContract.LOCATION_IDVEHICULE, location.getVehicule().getId());
        values.put(DataContract.LOCATION_DATE_DEBUT, dateFormat.format(location.getDateDebut()));
        values.put(DataContract.LOCATION_DATE_FIN, dateFormat.format(location.getDateFin()));
        values.put(DataContract.LOCATION_KILOMETRAGE_PARCOURU, location.getKilometrageParcouru());
        values.put(DataContract.LOCATION_ETAT, location.isEtat());
        return values;
    }




    public long insert(Location location) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long id = db.insert(DataContract.TABLE_LOCATION_NAME, null, constructValuesDB(location));

        db.close();

        return id;
    }


    public Location getCurrentLocationWithVehiculeId(int vehiculeId) {
        Location location = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        Cursor cursor = db.query(
                DataContract.TABLE_LOCATION_NAME, null,
                DataContract.LOCATION_IDVEHICULE + "=" + vehiculeId + " AND " + DataContract.LOCATION_ETAT + "=1 " ,
                null,
                null,
                null,
                null);

        if (cursor != null && cursor.moveToFirst()) {

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Boolean etat = false;
                Date DateDebut = null;
                Date DateFin = null;

                Integer idClient = cursor.getInt(cursor.getColumnIndex(DataContract.LOCATION_IDCLIENT));
                Integer idVehicule = cursor.getInt(cursor.getColumnIndex(DataContract.LOCATION_IDVEHICULE));
                Integer prix = cursor.getInt(cursor.getColumnIndex(DataContract.LOCATION_PRIX));

                try {
                    DateDebut = format.parse(cursor.getString(cursor.getColumnIndex(DataContract.LOCATION_DATE_DEBUT)));
                    DateFin = format.parse(cursor.getString(cursor.getColumnIndex(DataContract.LOCATION_DATE_FIN)));
                } catch (Exception e) {
                    Log.e("LOG => ", e.getMessage());
                }
                int kilometrageParcouru = cursor.getInt(cursor.getColumnIndex(DataContract.LOCATION_KILOMETRAGE_PARCOURU));
                int state = cursor.getInt(cursor.getColumnIndex(DataContract.LOCATION_ETAT));
                if (state == 1) {
                    etat = true;
                } else {
                    etat = false;
                }
                Client client = clientDao.getClientFromId(idClient);
                Vehicule vehicule = vehiculeDao.getVehiculeFromId(idVehicule);
                location = new Location(client, vehicule, DateDebut, DateFin, kilometrageParcouru, etat, prix);



            cursor.close();
        }


        return location;
    }

    public long insertOrUpdate(Location location) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = -1;
        Cursor c = db.query(DataContract.TABLE_LOCATION_NAME, null,
                DataContract.LOCATION_ID+"=" + location.getId(), null, null, null, null);

        if (c.getCount() > 0) {
            update(location);
        } else {
            insert(location);
        }

        db.close();

        return id;
    }

    //TODO - VOIR POUR PHOTOS
    public List<Location> getListe(String marqueModele) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DataContract.TABLE_LOCATION_NAME, null,
                null,
                null,
                null,
                null,
                null);

        List<Location> objects = new ArrayList<Location>();

        if (cursor != null && cursor.moveToFirst()) {
            do {

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Boolean etat = false;
                Date DateDebut = null;
                Date DateFin = null;

                Integer id = cursor.getInt(cursor.getColumnIndex(DataContract.LOCATION_ID));
                Integer idClient = cursor.getInt(cursor.getColumnIndex(DataContract.LOCATION_IDCLIENT));
                Integer idVehicule = cursor.getInt(cursor.getColumnIndex(DataContract.LOCATION_IDVEHICULE));
                Integer prix = cursor.getInt(cursor.getColumnIndex(DataContract.LOCATION_PRIX));
                try {
                    DateDebut = format.parse(cursor.getString(cursor.getColumnIndex(DataContract.LOCATION_DATE_DEBUT)));
                    DateFin = format.parse(cursor.getString(cursor.getColumnIndex(DataContract.LOCATION_DATE_FIN)));
                } catch (Exception e) {
                    Log.e("LOG => ", e.getMessage());
                }
                int kilometrageParcouru = cursor.getInt(cursor.getColumnIndex(DataContract.LOCATION_KILOMETRAGE_PARCOURU));
                int state = cursor.getInt(cursor.getColumnIndex(DataContract.LOCATION_ETAT));
                if (state == 1) {
                    etat = true;
                } else {
                    etat = false;
                }
                Client client = clientDao.getClientFromId(idClient);
                Vehicule vehicule = vehiculeDao.getVehiculeFromId(idVehicule);
                objects.add(new Location(id, client, vehicule, DateDebut, DateFin, kilometrageParcouru, etat,prix));

            } while (cursor.moveToNext());

            cursor.close();
        }

        return objects;
    }


    public void update(int idLocation, Location location) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update(DataContract.TABLE_LOCATION_NAME, constructValuesDB(location),
                "ID=" + idLocation,
                null);
        db.close();
    }
    
    public long update(Location location) {
        update(location.getId(), location);
        return location.getId();
    }


    public Location getLocationFromId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DataContract.TABLE_LOCATION_NAME, null,
                "ID=" + id,
                null,
                null,
                null,
                null);

        Location object = null;

        if (cursor != null && cursor.moveToFirst()) {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Boolean etat = false;
            Date DateDebut = null;
            Date DateFin = null;

            Integer id2 = cursor.getInt(cursor.getColumnIndex(DataContract.LOCATION_ID));
            Integer idClient = cursor.getInt(cursor.getColumnIndex(DataContract.LOCATION_IDCLIENT));
            Integer idVehicule = cursor.getInt(cursor.getColumnIndex(DataContract.LOCATION_IDVEHICULE));
            Integer prix = cursor.getInt(cursor.getColumnIndex(DataContract.LOCATION_PRIX));
            try {
                DateDebut = format.parse(cursor.getString(cursor.getColumnIndex(DataContract.LOCATION_DATE_DEBUT)));
                DateFin = format.parse(cursor.getString(cursor.getColumnIndex(DataContract.LOCATION_DATE_FIN)));
            } catch (Exception e) {
                Log.e("LOG => ", e.getMessage());
            }
            int kilometrageParcouru = cursor.getInt(cursor.getColumnIndex(DataContract.LOCATION_KILOMETRAGE_PARCOURU));
            int state = cursor.getInt(cursor.getColumnIndex(DataContract.LOCATION_ETAT));
            if (state == 1) {
                etat = true;
            } else {
                etat = false;
            }
            Client client = clientDao.getClientFromId(idClient);
            Vehicule vehicule = vehiculeDao.getVehiculeFromId(idVehicule);

            object = new Location(id2, client, vehicule, DateDebut, DateFin, kilometrageParcouru, etat,prix);

            cursor.close();
        }

        return object;
    }
}
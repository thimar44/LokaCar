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
        values.put(DataContract.LOCATION_IDCLIENT, location.getClient().getId());
        values.put(DataContract.LOCATION_IDVEHICULE, location.getVehicule().getId());
        values.put(DataContract.LOCATION_DATE_DEBUT, location.getDateDebut().toString());
        values.put(DataContract.LOCATION_DATE_FIN, location.getDateFin().toString());
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

    public long insertOrUpdate(Location location) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = -1;
        Cursor c = db.query(DataContract.TABLE_LOCATION_NAME, null,
                "ID_VEHICULE = " + location.getVehicule().getId() + " AND ID_CLIENT = "+ location.getClient().getId(), null, null, null, null);

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

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                Boolean etat = false;
                Date DateDebut = null;
                Date DateFin = null;

                Integer idClient = cursor.getInt(cursor.getColumnIndex(DataContract.LOCATION_IDCLIENT));
                Integer idVehicule = cursor.getInt(cursor.getColumnIndex(DataContract.LOCATION_IDVEHICULE));
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
                objects.add(new Location(client, vehicule, DateDebut, DateFin, kilometrageParcouru, etat));

            } while (cursor.moveToNext());

            cursor.close();
        }

        return objects;
    }


    public void update(int idClient, int idVehicule, Location location) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update(DataContract.TABLE_CLIENT_NAME, constructValuesDB(location),
                "ID_VEHICULE = " + idVehicule + " AND ID_CLIENT = "+ idClient,
                null);
        db.close();

    }
    
    public void update(Location location) {
        update(location.getClient().getId(), location.getVehicule().getId(), location);
    }


}
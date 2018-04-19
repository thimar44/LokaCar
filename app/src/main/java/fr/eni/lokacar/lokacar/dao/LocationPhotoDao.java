package fr.eni.lokacar.lokacar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.eni.lokacar.lokacar.been.Agence;
import fr.eni.lokacar.lokacar.been.Location;
import fr.eni.lokacar.lokacar.been.LocationPhoto;
import fr.eni.lokacar.lokacar.been.Marque;
import fr.eni.lokacar.lokacar.been.Photo;
import fr.eni.lokacar.lokacar.been.TypeCarburant;
import fr.eni.lokacar.lokacar.been.TypeVehicule;
import fr.eni.lokacar.lokacar.been.Vehicule;
import fr.eni.lokacar.lokacar.helper.DataContract;
import fr.eni.lokacar.lokacar.helper.ModeleHelper;

public class LocationPhotoDao {
    private ModeleHelper dbHelper;
    private LocationDao locationDao;
    private PhotoDao photoDao;


    public LocationPhotoDao(Context context) {
        this.dbHelper = new ModeleHelper(context);
        this.locationDao = new LocationDao(context);
        this.photoDao = new PhotoDao(context);
    }

    /**
     * Create ContentValues with modele
     *
     * @param locationPhoto
     * @return
     */
    private ContentValues constructValuesDB(LocationPhoto locationPhoto) {
        ContentValues values = new ContentValues();
        values.put(DataContract.LOCATION_PHOTO_IDLOCATION, locationPhoto.getLocation().getId());
        values.put(DataContract.LOCATION_PHOTO_IDPHOTO, locationPhoto.getPhoto().getId());
        values.put(DataContract.LOCATION_PHOTO_TYPE, locationPhoto.getTypeLocationPhoto());
        return values;
    }

    public long insert(LocationPhoto locationPhoto) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = -1;
        try {
            id = db.insert(DataContract.TABLE_LOCATION_PHOTO_NAME, null, constructValuesDB(locationPhoto));
        } catch (SQLException e) {
            Log.v("SQL => ", e.getMessage());
        }
        db.close();
        return id;
    }

    public long insertOrUpdate(LocationPhoto locationPhoto) {
        long id = -1;
        if (locationPhoto.getId() > 0) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            Cursor c = db.query(DataContract.TABLE_LOCATION_PHOTO_NAME, null,
                    "ID=" + locationPhoto.getId(), null, null, null, null);

            if (c.getCount() > 0) {
                update(locationPhoto);
            } else {
                id = insert(locationPhoto);
            }
            db.close();
        } else {
            id = insert(locationPhoto);
        }

        return id;
    }

    public LocationPhoto getLocationPhotoFromId(int id) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DataContract.TABLE_LOCATION_PHOTO_NAME, null,
                "ID=" + id,
                null,
                null,
                null,
                null);

        LocationPhoto object = null;

        if (cursor != null && cursor.moveToFirst()) {
            int idPhoto = cursor.getInt(cursor.getColumnIndex(DataContract.LOCATION_PHOTO_IDPHOTO));
            int idLocation = cursor.getInt(cursor.getColumnIndex(DataContract.LOCATION_PHOTO_IDLOCATION));
            String type = cursor.getString(cursor.getColumnIndex(DataContract.LOCATION_PHOTO_TYPE));
            Photo photo = photoDao.getPhotoFromId(idPhoto);
            Location location = locationDao.getLocationFromId(idLocation);
            object = new LocationPhoto(location, photo, type);
            cursor.close();
        }
        return object;
    }

    public void update(int id, LocationPhoto locationPhoto) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update(DataContract.TABLE_LOCATION_PHOTO_NAME, constructValuesDB(locationPhoto),
                "ID=" + id,
                null);
        db.close();
    }

    public void update(LocationPhoto locationPhoto) {
        update(locationPhoto.getId(), locationPhoto);
    }

    public List<LocationPhoto> getListeForLocation(int idLocation, String type) {

        StringBuilder builder = new StringBuilder();

        builder.append(DataContract.LOCATION_PHOTO_IDLOCATION + " = " + String.valueOf(idLocation));
        builder.append(" AND " + DataContract.LOCATION_PHOTO_TYPE + " = " + type);


        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DataContract.TABLE_LOCATION_PHOTO_NAME, null,
                builder.toString(),
                null,
                null,
                null,
                null);

        List<LocationPhoto> objects = new ArrayList<LocationPhoto>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idPhotoList = cursor.getInt(cursor.getColumnIndex(DataContract.LOCATION_PHOTO_IDPHOTO));
                int idLocationList = cursor.getInt(cursor.getColumnIndex(DataContract.LOCATION_PHOTO_IDLOCATION));
                String typelist = cursor.getString(cursor.getColumnIndex(DataContract.LOCATION_PHOTO_TYPE));
                Photo photo = photoDao.getPhotoFromId(idPhotoList);
                Location location = locationDao.getLocationFromId(idLocationList);
                objects.add(new LocationPhoto(location, photo, typelist));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return objects;
    }
}

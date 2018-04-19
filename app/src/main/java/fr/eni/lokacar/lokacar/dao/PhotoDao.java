package fr.eni.lokacar.lokacar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.eni.lokacar.lokacar.been.Photo;
import fr.eni.lokacar.lokacar.helper.DataContract;
import fr.eni.lokacar.lokacar.helper.ModeleHelper;

public class PhotoDao {
    private ModeleHelper dbHelper;

    public PhotoDao(Context context) {
        this.dbHelper = new ModeleHelper(context);

    }

    /**
     * Create ContentValues with modele
     *
     * @param photo
     * @return
     */
    private ContentValues constructValuesDB(Photo photo) {
        ContentValues values = new ContentValues();
        //values.put(DataContract.PHOTO_ID, photo.getId());
        values.put(DataContract.PHOTO_URI, photo.getUri());

        return values;
    }

    public long insert(Photo photo) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long id = db.insert(DataContract.TABLE_PHOTO_NAME, null, constructValuesDB(photo));

        db.close();

        return id;
    }

    public long insertOrUpdate(Photo photo) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = -1;
        Cursor c = db.query(DataContract.TABLE_PHOTO_NAME, null,
                "ID=" + photo.getId(), null, null, null, null);

        if (c.getCount() > 0) {
            update(photo);
        } else {
            insert(photo);
        }

        db.close();

        return id;
    }

    public List<Photo> getListe() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DataContract.TABLE_PHOTO_NAME, null,
                null,
                null,
                null,
                null,
                null);

        List<Photo> objects = new ArrayList<Photo>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DataContract.PHOTO_ID));
                String uri = cursor.getString(cursor.getColumnIndex(DataContract.PHOTO_URI));
                objects.add(new Photo(id, uri));

            } while (cursor.moveToNext());

            cursor.close();
        }

        return objects;
    }

    public Photo getPhotoFromId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DataContract.TABLE_PHOTO_NAME, null,
                "ID=" + id,
                null,
                null,
                null,
                null);

        Photo object = null;

        if (cursor != null && cursor.moveToFirst()) {

            int idPhoto = cursor.getInt(cursor.getColumnIndex(DataContract.PHOTO_ID));
            String uri = cursor.getString(cursor.getColumnIndex(DataContract.PHOTO_URI));

            object = new Photo(idPhoto, uri);
            cursor.close();
        }

        return object;
    }


    public void update(int id, Photo photo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update(DataContract.TABLE_PHOTO_NAME, constructValuesDB(photo),
                "ID=" + id,
                null);
        db.close();

    }

    public void update(Photo photo) {
        update(photo.getId(), photo);
    }

}


package fr.eni.lokacar.lokacar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.eni.lokacar.lokacar.been.Marque;
import fr.eni.lokacar.lokacar.helper.DataContract;
import fr.eni.lokacar.lokacar.helper.ModeleHelper;

public class MarqueDao {
    private ModeleHelper dbHelper;

    public MarqueDao(Context context) {
        this.dbHelper = new ModeleHelper(context);

    }

    /**
     * Create ContentValues with modele
     *
     * @param marque
     * @return
     */
    private ContentValues constructValuesDB(Marque marque) {
        ContentValues values = new ContentValues();
        values.put(DataContract.MARQUE_ID, marque.getId());
        values.put(DataContract.MARQUE_LIBELLE, marque.getLibelle());

        return values;
    }

    public long insert(Marque marque) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long id = db.insert(DataContract.TABLE_MARQUE_NAME, null, constructValuesDB(marque));

        db.close();

        return id;
    }

    public long insertOrUpdate(Marque marque) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = -1;
        Cursor c = db.query(DataContract.TABLE_MARQUE_NAME, null,
                "ID=" + marque.getId(), null, null, null, null);

        if (c.getCount() > 0) {
            update(marque);
        } else {
            insert(marque);
        }

        db.close();

        return id;
    }

    public List<Marque> getListe() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DataContract.TABLE_MARQUE_NAME, null,
                null,
                null,
                null,
                null,
                null);

        List<Marque> objects = new ArrayList<Marque>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DataContract.MARQUE_ID));
                String libelle = cursor.getString(cursor.getColumnIndex(DataContract.MARQUE_LIBELLE));
                objects.add(new Marque(id, libelle));

            } while (cursor.moveToNext());

            cursor.close();
        }

        return objects;
    }

    public Marque getMarqueFromId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DataContract.TABLE_MARQUE_NAME, null,
                "ID = " + id,
                null,
                null,
                null,
                null);

        Marque object = null;

        if (cursor != null && cursor.moveToFirst()) {

            int idMarque = cursor.getInt(cursor.getColumnIndex(DataContract.MARQUE_ID));
            String libelle = cursor.getString(cursor.getColumnIndex(DataContract.MARQUE_LIBELLE));

            object = new Marque(idMarque, libelle);
            cursor.close();
        }

        return object;
    }


    public void update(int id, Marque marque) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update(DataContract.TABLE_MARQUE_NAME, constructValuesDB(marque),
                "ID=" + id,
                null);
        db.close();

    }

    public void update(Marque marque) {
        update(marque.getId(), marque);
    }
}

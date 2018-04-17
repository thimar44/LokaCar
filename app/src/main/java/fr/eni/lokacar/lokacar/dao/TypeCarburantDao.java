package fr.eni.lokacar.lokacar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.eni.lokacar.lokacar.been.TypeCarburant;
import fr.eni.lokacar.lokacar.helper.DataContract;
import fr.eni.lokacar.lokacar.helper.ModeleHelper;

public class TypeCarburantDao {
    private ModeleHelper dbHelper;

    public TypeCarburantDao(Context context) {
        this.dbHelper = new ModeleHelper(context);

    }

    /**
     * Create ContentValues with modele
     *
     * @param typeCarburant
     * @return
     */
    private ContentValues constructValuesDB(TypeCarburant typeCarburant) {
        ContentValues values = new ContentValues();
        values.put(DataContract.TYPE_CARBURANT_ID, typeCarburant.getId());
        values.put(DataContract.TYPE_CARBURANT_LIBELLE, typeCarburant.getLibelle());

        return values;
    }

    public long insert(TypeCarburant typeCarburant) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long id = db.insert(DataContract.TABLE_TYPE_CARBURANT_NAME, null, constructValuesDB(typeCarburant));

        db.close();

        return id;
    }

    public long insertOrUpdate(TypeCarburant typeCarburant) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = -1;
        Cursor c = db.query(DataContract.TABLE_TYPE_CARBURANT_NAME, null,
                "ID=" + typeCarburant.getId(), null, null, null, null);

        if (c.getCount() > 0) {
            update(typeCarburant);
        } else {
            insert(typeCarburant);
        }
        db.close();
        return id;
    }

    public List<TypeCarburant> getListe() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DataContract.TABLE_TYPE_CARBURANT_NAME, null,
                null,
                null,
                null,
                null,
                null);

        List<TypeCarburant> objects = new ArrayList<TypeCarburant>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DataContract.TYPE_CARBURANT_ID));
                String libelle = cursor.getString(cursor.getColumnIndex(DataContract.TYPE_CARBURANT_LIBELLE));
                objects.add(new TypeCarburant(id, libelle));

            } while (cursor.moveToNext());

            cursor.close();
        }

        return objects;
    }

    public TypeCarburant getTypeCarburantFromId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DataContract.TABLE_TYPE_CARBURANT_NAME, null,
                "ID = " + id,
                null,
                null,
                null,
                null);

        TypeCarburant object = null;

        if (cursor != null && cursor.moveToFirst()) {

            int idTypeCarburant = cursor.getInt(cursor.getColumnIndex(DataContract.TYPE_CARBURANT_ID));
            String libelle = cursor.getString(cursor.getColumnIndex(DataContract.TYPE_CARBURANT_LIBELLE));

            object = new TypeCarburant(idTypeCarburant, libelle);
            cursor.close();
        }

        return object;
    }


    public void update(int id, TypeCarburant typeCarburant) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update(DataContract.TABLE_TYPE_CARBURANT_NAME, constructValuesDB(typeCarburant),
                "ID=" + id,
                null);
        db.close();

    }

    public void update(TypeCarburant typeCarburant) {
        update(typeCarburant.getId(), typeCarburant);
    }

}

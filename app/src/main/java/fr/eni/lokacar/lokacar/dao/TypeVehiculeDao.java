package fr.eni.lokacar.lokacar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.eni.lokacar.lokacar.been.TypeVehicule;
import fr.eni.lokacar.lokacar.helper.DataContract;
import fr.eni.lokacar.lokacar.helper.ModeleHelper;

public class TypeVehiculeDao {
    private ModeleHelper dbHelper;

    public TypeVehiculeDao(Context context) {
        this.dbHelper = new ModeleHelper(context);

    }

    /**
     * Create ContentValues with modele
     *
     * @param typeVehicule
     * @return
     */
    private ContentValues constructValuesDB(TypeVehicule typeVehicule) {
        ContentValues values = new ContentValues();
        values.put(DataContract.TYPE_VEHICULE_ID, typeVehicule.getId());
        values.put(DataContract.TYPE_VEHICULE_LIBELLE, typeVehicule.getLibelle());

        return values;
    }

    public long insert(TypeVehicule typeVehicule) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long id = db.insert(DataContract.TABLE_TYPE_VEHICULE_NAME, null, constructValuesDB(typeVehicule));

        db.close();

        return id;
    }

    public long insertOrUpdate(TypeVehicule typeVehicule) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = -1;
        Cursor c = db.query(DataContract.TABLE_TYPE_VEHICULE_NAME, null,
                "ID=" + typeVehicule.getId(), null, null, null, null);

        if (c.getCount() > 0) {
            update(typeVehicule);
        } else {
            insert(typeVehicule);
        }
        db.close();
        return id;
    }

    public List<TypeVehicule> getListe() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DataContract.TABLE_TYPE_VEHICULE_NAME, null,
                null,
                null,
                null,
                null,
                null);

        List<TypeVehicule> objects = new ArrayList<TypeVehicule>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DataContract.TYPE_VEHICULE_ID));
                String libelle = cursor.getString(cursor.getColumnIndex(DataContract.TYPE_VEHICULE_LIBELLE));
                objects.add(new TypeVehicule(id, libelle));

            } while (cursor.moveToNext());

            cursor.close();
        }

        return objects;
    }

    public TypeVehicule getTypeVehiculeFromId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DataContract.TABLE_TYPE_VEHICULE_NAME, null,
                "ID = " + id,
                null,
                null,
                null,
                null);

        TypeVehicule object = null;

        if (cursor != null && cursor.moveToFirst()) {

            int idTypeVehicule = cursor.getInt(cursor.getColumnIndex(DataContract.TYPE_VEHICULE_ID));
            String libelle = cursor.getString(cursor.getColumnIndex(DataContract.TYPE_VEHICULE_LIBELLE));

            object = new TypeVehicule(idTypeVehicule, libelle);
            cursor.close();
        }

        return object;
    }


    public void update(int id, TypeVehicule typeVehicule) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update(DataContract.TABLE_TYPE_VEHICULE_NAME, constructValuesDB(typeVehicule),
                "ID=" + id,
                null);
        db.close();

    }

    public void update(TypeVehicule typeVehicule) {
        update(typeVehicule.getId(), typeVehicule);
    }
}

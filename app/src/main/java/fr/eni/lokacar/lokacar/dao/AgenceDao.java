package fr.eni.lokacar.lokacar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import fr.eni.lokacar.lokacar.been.Agence;
import fr.eni.lokacar.lokacar.helper.DataContract;
import fr.eni.lokacar.lokacar.helper.ModeleHelper;

public class AgenceDao {

    private ModeleHelper dbHelper;

    public AgenceDao(Context context) {

        this.dbHelper = new ModeleHelper(context);

    }

    /**
     * Create ContentValues with modele
     * @param agence
     * @return
     */
    private ContentValues constructValuesDB(Agence agence) {
        ContentValues values = new ContentValues();
        values.put(DataContract.AGENCE_ID , agence.getId());
        values.put(DataContract.AGENCE_CODEPOSTAL , agence.getCodePostal());
        values.put(DataContract.AGENCE_NOM , agence.getNom());
        values.put(DataContract.AGENCE_ADRESSE ,agence.getAdresse());
        values.put(DataContract.AGENCE_VILLE , agence.getVille());
        return values;
    }

    public long insert(Agence agence){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = db.insert(DataContract.TABLE_AGENCE_NAME, null, constructValuesDB(agence));
        db.close();
        return id;
    }

    public long insertOrUpdate(Agence agence){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = -1;
        Cursor c = db.query(DataContract.TABLE_AGENCE_NAME, null,
                "ID="+agence.getId(), null,null,null,null);

        if(c.getCount() > 0){
            update(agence);
        }
        else {
            insert(agence);
        }

        db.close();

        return id;
    }

    public void update(int id, Agence agence) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update(DataContract.TABLE_AGENCE_NAME, constructValuesDB(agence),
                "ID=" + id,
                null);
        db.close();
    }

    public void update(Agence agence) {
        update(agence.getId(), agence);
    }


}

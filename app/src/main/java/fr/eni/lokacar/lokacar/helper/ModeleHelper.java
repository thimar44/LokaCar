package fr.eni.lokacar.lokacar.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ModeleHelper extends SQLiteOpenHelper {

    public ModeleHelper(Context context) {
        super(context, DataContract.DATABASE_NAME,
                null,
                DataContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataContract.AGENCE_CREATE_TABLE);
        db.execSQL(DataContract.CLIENT_CREATE_TABLE);
        db.execSQL(DataContract.LOCATION_CREATE_TABLE);
        db.execSQL(DataContract.MARQUE_CREATE_TABLE);
        db.execSQL(DataContract.PERSONNE_CREATE_TABLE);
        db.execSQL(DataContract.PHOTO_CREATE_TABLE);
        db.execSQL(DataContract.TYPE_CARBURANT_CREATE_TABLE);
        db.execSQL(DataContract.TYPE_VEHICULE_CREATE_TABLE);
        db.execSQL(DataContract.VEHICULE_CREATE_TABLE);
        db.execSQL(DataContract.LOCATION_PHOTO_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DataContract.QUERY_DELETE_TABLE_AGENCE);
        db.execSQL(DataContract.QUERY_DELETE_TABLE_CLIENT);
        db.execSQL(DataContract.QUERY_DELETE_TABLE_LOCATION);
        db.execSQL(DataContract.QUERY_DELETE_TABLE_MARQUE);
        db.execSQL(DataContract.QUERY_DELETE_TABLE_PERSONNE);
        db.execSQL(DataContract.QUERY_DELETE_TABLE_PHOTO);
        db.execSQL(DataContract.QUERY_DELETE_TABLE_TYPE_CARBURANT);
        db.execSQL(DataContract.QUERY_DELETE_TABLE_TYPE_VEHICULE);
        db.execSQL(DataContract.QUERY_DELETE_TABLE_VEHICULE);
        db.execSQL(DataContract.QUERY_DELETE_TABLE_LOCATION_PHOTO);
        onCreate(db);
    }
}

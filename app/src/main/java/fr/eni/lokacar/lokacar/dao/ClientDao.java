package fr.eni.lokacar.lokacar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.eni.lokacar.lokacar.been.Client;
import fr.eni.lokacar.lokacar.helper.DataContract;
import fr.eni.lokacar.lokacar.helper.ModeleHelper;

public class ClientDao {
    private ModeleHelper dbHelper;

    public ClientDao(Context context) {
        this.dbHelper = new ModeleHelper(context);

    }

    /**
     * Create ContentValues with modele
     * @param client
     * @return
     */
    private ContentValues constructValuesDB(Client client) {
        ContentValues values = new ContentValues();
        values.put(DataContract.CLIENT_ID , client.getId());
        values.put(DataContract.CLIENT_CODEPOSTAL , client.getCodePostal());
        values.put(DataContract.CLIENT_NOM , client.getNom());
        values.put(DataContract.CLIENT_PRENOM ,client.getPrenom());
        values.put(DataContract.CLIENT_ADRESSE , client.getAdresse());
        values.put(DataContract.CLIENT_VILLE , client.getVille());
        values.put(DataContract.CLIENT_NUMERO_TELEPHONE , client.getNumeroTelephone());
        values.put(DataContract.CLIENT_MAIL , client.getMail());
        return values;
    }

    public long insert(Client client){

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long id = db.insert(DataContract.TABLE_CLIENT_NAME, null, constructValuesDB(client));

        db.close();

        return id;
    }

    public long insertOrUpdate(Client client){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = -1;
        Cursor c = db.query(DataContract.TABLE_CLIENT_NAME, null,
                "ID="+client.getId(), null,null,null,null);

        if(c.getCount() > 0){
            update(client);
        }
        else {
            insert(client);
        }

        db.close();

        return id;
    }

    public List<Client> getListe(String marqueModele) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DataContract.TABLE_CLIENT_NAME, null,
                null,
                null,
                null,
                null,
                null);

        List<Client> objects = new ArrayList<Client>() ;

        if(cursor != null && cursor.moveToFirst()){
            do{

                Integer id = cursor.getInt(cursor.getColumnIndex(DataContract.CLIENT_ID ));
                int codePostal = cursor.getInt(cursor.getColumnIndex(DataContract.CLIENT_CODEPOSTAL ));
                String nom = cursor.getString(cursor.getColumnIndex(DataContract.CLIENT_NOM ));
                String prenom = cursor.getString(cursor.getColumnIndex(DataContract.CLIENT_PRENOM ));
                String adresse = cursor.getString(cursor.getColumnIndex(DataContract.CLIENT_ADRESSE ));
                String ville = cursor.getString(cursor.getColumnIndex(DataContract.CLIENT_VILLE ));
                int numeroTelephone = cursor.getInt(cursor.getColumnIndex(DataContract.CLIENT_NUMERO_TELEPHONE ));
                String mail = cursor.getString(cursor.getColumnIndex(DataContract.CLIENT_MAIL ));

                objects.add(new Client(id, codePostal, numeroTelephone, mail, nom, prenom, adresse, ville));

            }while (cursor.moveToNext());

            cursor.close();
        }

        return objects;
    }



    public void update(int id, Client client) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update(DataContract.TABLE_CLIENT_NAME, constructValuesDB(client),
                "ID=" + id,
                null);
        db.close();

    }

    public void update( Client client) {
        update(client.getId(), client);
    }


}

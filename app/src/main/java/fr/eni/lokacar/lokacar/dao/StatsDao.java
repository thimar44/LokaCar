package fr.eni.lokacar.lokacar.dao;

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

public class StatsDao {
    private ModeleHelper dbHelper;

    public StatsDao(Context context) {
        this.dbHelper = new ModeleHelper(context);

    }


    public int getCATotal() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + DataContract.LOCATION_PRIX +") FROM "+ DataContract.TABLE_LOCATION_NAME +";", null);
        int sum = 0;
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    sum = cursor.getInt(0);
                }
            } finally {
                cursor.close();
            }
        }

        return sum;
    }


    public int getCAMoyen() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT AVG(" + DataContract.LOCATION_PRIX +") FROM "+ DataContract.TABLE_LOCATION_NAME +" WHERE " + DataContract.LOCATION_PRIX + " NOT LIKE 0;", null);
        int sum = 0;
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    sum = cursor.getInt(0);
                }
            } finally {
                cursor.close();
            }
        }

        return sum;
    }


    public List<ResultCAVehicule> getCAMoyenVehicule(){
        List<ResultCAVehicule> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT" + " AVG(" + DataContract.LOCATION_PRIX +"),"+ DataContract.VEHICULE_DESIGNATION
                +" FROM " + DataContract.TABLE_LOCATION_NAME + " l "
                +" INNER JOIN " + DataContract.TABLE_VEHICULE_NAME  + " v "
                + "ON l." + DataContract.LOCATION_IDVEHICULE + " =v." + DataContract.VEHICULE_ID
                +" WHERE " + DataContract.LOCATION_PRIX + " NOT LIKE 0"
                +" GROUP BY " + DataContract.LOCATION_IDVEHICULE + ";", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {

                Integer moyenne = cursor.getInt(0);
                String denomication = cursor.getString(1);
                ResultCAVehicule resultCAVehicule = new ResultCAVehicule(moyenne,denomication);
                list.add(resultCAVehicule);


            } while (cursor.moveToNext());

            cursor.close();
        }

        return list;
    }










    public class ResultCAVehicule {
        private int CAMoyen;
        private String Denomination;


        public ResultCAVehicule(int CAMoyen, String denomination) {
            this.CAMoyen = CAMoyen;
            Denomination = denomination;
        }

        public int getCAMoyen() {return CAMoyen;}

        public void setCAMoyen(int CAMoyen) {this.CAMoyen = CAMoyen; }

        public String getDenomination() {return Denomination;}

        public void setDenomination(String denomination) {Denomination = denomination;}

        @Override
        public String toString() {
            return Denomination + " : " + CAMoyen + "€ /loc \n";
        }
    }
}

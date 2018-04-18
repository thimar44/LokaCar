package fr.eni.lokacar.lokacar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import fr.eni.lokacar.lokacar.been.Client;
import fr.eni.lokacar.lokacar.been.Location;
import fr.eni.lokacar.lokacar.been.Vehicule;
import fr.eni.lokacar.lokacar.dao.ClientDao;
import fr.eni.lokacar.lokacar.dao.LocationDao;
import fr.eni.lokacar.lokacar.dao.VehiculeDao;

import static fr.eni.lokacar.lokacar.helper.DataContract.MY_PREFS_NAME;

public class RetourActivity extends AppCompatActivity {

    private TextView tvVehMarque;
    private TextView tvVehDesignation;
    private TextView tvVehTypeCarburant;
    private TextView tvVehTypeVehicule;
    private TextView tvVehImmatriculation;
    private TextView tvVehKilometrage;
    private TextView tvVehPrixJour;

    private TextView tvClientNom;
    private TextView tvClientPrenom;
    private TextView tvClientTel;
    private TextView tvClientEmail;
    private TextView tvClientAdresse;
    private TextView tvClientCp;
    private TextView tvClientVille;

    private TextView tvLocPrix;
    private TextView tvLocDuree;
    private TextView tvLocDateDebut;
    private EditText edLocKm;

    private Vehicule vehicule;
    private Location location;
    private Client client;
    private Date datefin;

    private LocationDao locationDao;
    private VehiculeDao vehiculeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retour);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String villeAgence = prefs.getString("agenceName", " ");
        setTitle(getText(R.string.app_name) + " " + villeAgence);

        Toolbar toolbar = findViewById(R.id.ourToolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.TitleRetourActivity);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Intent intent = getIntent();
        int vehiculeId = intent.getIntExtra("car", 0);


        locationDao = new LocationDao(this.getApplicationContext());
        location = locationDao.getCurrentLocationWithVehiculeId(vehiculeId);
        vehicule = location.getVehicule();
        client = location.getClient();


        tvVehMarque = findViewById(R.id.marque);
        tvVehMarque.setText(vehicule.getMarque().getLibelle());
        tvVehDesignation = findViewById(R.id.designation);
        tvVehDesignation.setText(vehicule.getDesignation());
        tvVehTypeCarburant = findViewById(R.id.typeCarburant);
        tvVehTypeCarburant.setText(vehicule.getTypeCarburant().getLibelle());
        tvVehTypeVehicule = findViewById(R.id.typeVehicule);
        tvVehTypeVehicule.setText(vehicule.getTypeVehicule().getLibelle());
        tvVehImmatriculation = findViewById(R.id.immatriculation);
        tvVehImmatriculation.setText(vehicule.getImmatriculation());
        tvVehKilometrage = findViewById(R.id.kilometrage);
        tvVehKilometrage.setText(vehicule.getKilometrage() + "");
        tvVehPrixJour = findViewById(R.id.prixJour);
        tvVehPrixJour.setText(vehicule.getPrixJour() + "€ /j ");

        tvClientNom = findViewById(R.id.clientRetourNom);
        tvClientPrenom = findViewById(R.id.clientRetourPreNom);
        tvClientEmail = findViewById(R.id.clientRetourEmail);
        tvClientTel = findViewById(R.id.clientRetourTel);
        tvClientAdresse = findViewById(R.id.clientRetourAdresse);
        tvClientCp = findViewById(R.id.clientRetourCp);
        tvClientVille = findViewById(R.id.clientRetourVille);

        tvClientNom.setText(client.getNom());
        tvClientPrenom.setText(client.getPrenom());
        tvClientEmail.setText(client.getMail());
        tvClientTel.setText(client.getNumeroTelephone()+"");
        tvClientAdresse.setText(client.getAdresse());
        tvClientCp.setText(client.getCodePostal()+"");
        tvClientVille.setText(client.getVille());

        tvLocDateDebut = findViewById(R.id.locationRetourDateDebut);
        tvLocPrix = findViewById(R.id.locationRetourPrix);
        tvLocDuree = findViewById(R.id.locationRetourDuree);
        edLocKm = findViewById(R.id.locationRetourKm);

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(location.getDateDebut());
        String day = calendar.get(Calendar.DAY_OF_MONTH)+"";
        String month =  (calendar.get(Calendar.MONTH)+1)+"";
        String year =  calendar.get(Calendar.YEAR)+"";

        Date DateFin = Calendar.getInstance().getTime();
        tvLocDateDebut.setText(day + "/" + month+"/" + year);
        long diffMillis= Math.abs(DateFin.getTime() - location.getDateDebut().getTime());
        long differenceInDays = TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS);
        tvLocDuree.setText(differenceInDays+"");

        long prix = differenceInDays * vehicule.getPrixJour();
        tvLocPrix.setText(prix+"€ TTC");



    }

    public void validerRetour(View view) {
        if (validateForm()) {

            int kmRetour = Integer.valueOf(edLocKm.getText().toString());
            int kmParcouru = kmRetour - vehicule.getKilometrage();


            location.setDateFin(Calendar.getInstance().getTime());
            location.setEtat(false);
            location.setKilometrageParcouru(kmParcouru);
            locationDao = new LocationDao(this.getApplicationContext());
            locationDao.update(location);

            vehicule.setEnLocation(false);
            vehicule.setKilometrage(kmRetour);
            vehiculeDao = new VehiculeDao(this.getApplicationContext());
            vehiculeDao.update(vehicule);

            AlertDialog.Builder builder = new AlertDialog.Builder(RetourActivity.this);
            builder.setMessage("Voulez vous envoyer une facture au client")
                    .setTitle("");

            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });


            builder.setNegativeButton("non", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        } else {
            Toast.makeText(RetourActivity.this, "Vérifiez la saisie du kilométrage", Toast.LENGTH_LONG).show();
        }
    }


    public boolean validateForm() {
        boolean formIsValide = true;
        if ("".equals(edLocKm.getText().toString())) formIsValide = false;
        if(vehicule.getKilometrage()>Integer.valueOf(edLocKm.getText().toString())) formIsValide = false;
        return formIsValide;
    }

}

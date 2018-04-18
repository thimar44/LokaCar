package fr.eni.lokacar.lokacar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import fr.eni.lokacar.lokacar.been.Client;
import fr.eni.lokacar.lokacar.been.Location;
import fr.eni.lokacar.lokacar.been.Vehicule;
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


    private LocationDao locationDao;

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

        tvLocDateDebut.setText(location.getDateDebut().toString());

    }
}

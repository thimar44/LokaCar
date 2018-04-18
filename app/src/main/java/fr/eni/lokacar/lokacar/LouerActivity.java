package fr.eni.lokacar.lokacar;

import android.content.DialogInterface;
import android.content.Intent;
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

import fr.eni.lokacar.lokacar.been.Agence;
import fr.eni.lokacar.lokacar.been.Client;
import fr.eni.lokacar.lokacar.been.Location;
import fr.eni.lokacar.lokacar.been.Marque;
import fr.eni.lokacar.lokacar.been.TypeCarburant;
import fr.eni.lokacar.lokacar.been.TypeVehicule;
import fr.eni.lokacar.lokacar.been.Vehicule;
import fr.eni.lokacar.lokacar.dao.AgenceDao;
import fr.eni.lokacar.lokacar.dao.ClientDao;
import fr.eni.lokacar.lokacar.dao.LocationDao;
import fr.eni.lokacar.lokacar.dao.MarqueDao;
import fr.eni.lokacar.lokacar.dao.TypeCarburantDao;
import fr.eni.lokacar.lokacar.dao.TypeVehiculeDao;
import fr.eni.lokacar.lokacar.dao.VehiculeDao;

public class LouerActivity extends AppCompatActivity {

    private Vehicule vehicule;

    private EditText edClientNom;
    private EditText edClientPrenom;
    private EditText edClientTel;
    private EditText edClientEmail;
    private EditText edClientAdresse;
    private EditText edClientCodePostal;
    private EditText edClientVille;

    private TextView tvVehMarque;
    private TextView tvVehDesignation;
    private TextView tvVehTypeCarburant;
    private TextView tvVehTypeVehicule;
    private TextView tvVehImmatriculation;
    private TextView tvVehKilometrage;
    private TextView tvVehPrixJour;

    private ClientDao clientDao;
    private LocationDao locationDao;
    private VehiculeDao vehiculeDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_louer);
        Toolbar toolbar = findViewById(R.id.ourToolbar);
        toolbar.setTitle(R.string.TitleLouerActivity);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        Intent intent = getIntent();
        int vehiculeId = intent.getIntExtra("car", 0);
        vehiculeDao = new VehiculeDao(this.getApplicationContext());

        vehicule = vehiculeDao.getVehiculeFromId(vehiculeId);

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


        edClientNom = findViewById(R.id.clientNom);
        edClientPrenom = findViewById(R.id.clientPrenom);
        edClientTel = findViewById(R.id.clientTel);
        edClientEmail = findViewById(R.id.clientEmail);
        edClientAdresse = findViewById(R.id.clientAdresse);
        edClientCodePostal = findViewById(R.id.clientCp);
        edClientVille = findViewById(R.id.clientVille);

        // ******************************************************************    DUMMY  *****************************************************************
        edClientNom.setText("Pignon"); edClientPrenom.setText("Francois"); edClientTel.setText("15848");  edClientEmail.setText("francois.pignon@ledinerdecon.fr");
        edClientAdresse.setText("palais de l'élisée"); edClientCodePostal.setText("666");    edClientVille.setText("Meulin");
        // ******************************************************************    DUMMY  *****************************************************************
    }

    public void addPhotoLouer(View view) {
        Log.d("Thibaud", "ajouter une photo");
    }



    public void validerLouer(View view) {

        if (validateForm()) {
            Client client = new Client(
                    Integer.valueOf(edClientCodePostal.getText().toString()),
                    Integer.valueOf(edClientTel.getText().toString()),
                    edClientEmail.getText().toString(),
                    edClientNom.getText().toString(),
                    edClientPrenom.getText().toString(),
                    edClientAdresse.getText().toString(),
                    edClientVille.getText().toString()
            );
            clientDao = new ClientDao(this.getApplicationContext());
            long clientId = clientDao.insert(client);
            client.setId((int)clientId);

            Location location = new Location();
            location.setClient(client);
            location.setVehicule(vehicule);
            location.setDateDebut(Calendar.getInstance().getTime());
            location.setDateFin(Calendar.getInstance().getTime());
            location.setEtat(true);
            location.setKilometrageParcouru(0);

            locationDao = new LocationDao(this.getApplicationContext());
            locationDao.insert(location);

            vehicule.setEnLocation(true);
            vehiculeDao.update(vehicule);


            AlertDialog.Builder builder = new AlertDialog.Builder(LouerActivity.this);
            builder.setMessage("Voulez vous envoyer un récapitulatif au client")
                    .setTitle("");

            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Log.d("Thibaud", "On envoit le récapitulatif au client");
                    finish();
                }
            });


            builder.setNegativeButton("non", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                    finish();
                }
            });


            AlertDialog dialog = builder.create();
            dialog.show();


        } else {
            Toast.makeText(LouerActivity.this, "Vérifier les champs saisis", Toast.LENGTH_LONG).show();
        }
    }


    public boolean validateForm() {
        boolean formIsValide = true;
        if ("".equals(edClientNom.getText().toString())) formIsValide = false;
        if ("".equals(edClientPrenom.getText().toString())) formIsValide = false;
        if ("".equals(edClientTel.getText().toString())) formIsValide = false;
        if ("".equals(edClientEmail.getText().toString())) formIsValide = false;
        if ("".equals(edClientAdresse.getText().toString())) formIsValide = false;
        if ("".equals(edClientCodePostal.getText().toString())) formIsValide = false;
        if ("".equals(edClientVille.getText().toString())) formIsValide = false;
        return formIsValide;
    }


}

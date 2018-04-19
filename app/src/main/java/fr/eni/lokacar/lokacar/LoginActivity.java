package fr.eni.lokacar.lokacar;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import fr.eni.lokacar.lokacar.been.Personne;
import fr.eni.lokacar.lokacar.been.Photo;
import fr.eni.lokacar.lokacar.been.TypeCarburant;
import fr.eni.lokacar.lokacar.been.TypeVehicule;
import fr.eni.lokacar.lokacar.been.Vehicule;
import fr.eni.lokacar.lokacar.dao.AgenceDao;
import fr.eni.lokacar.lokacar.dao.ClientDao;
import fr.eni.lokacar.lokacar.dao.LocationDao;
import fr.eni.lokacar.lokacar.dao.MarqueDao;
import fr.eni.lokacar.lokacar.dao.PersonneDao;
import fr.eni.lokacar.lokacar.dao.PhotoDao;
import fr.eni.lokacar.lokacar.dao.TypeCarburantDao;
import fr.eni.lokacar.lokacar.dao.TypeVehiculeDao;
import fr.eni.lokacar.lokacar.dao.VehiculeDao;

import static fr.eni.lokacar.lokacar.helper.DataContract.MY_PREFS_NAME;

public class LoginActivity extends AppCompatActivity {

    private EditText edPassword;
    private EditText edLogin;
    private PersonneDao personneDao;
    private AgenceDao agenceDao;
    private VehiculeDao vehiculeDao;
    private TypeVehiculeDao typeVehiculeDao;
    private TypeCarburantDao typeCarburantDao;
    private MarqueDao marqueDao;
    private LocationDao locationDao;
    private ClientDao clientDao;
    private PhotoDao photoDao;

    private static int MY_PERMISSIONS_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //TOOLBAR
        setTitle(getText(R.string.app_name));

        Toolbar toolbar = findViewById(R.id.ourToolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.TitleLoginActivity);


        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        edPassword = findViewById(R.id.password);
        edLogin = findViewById(R.id.login);
        personneDao = new PersonneDao(this.getApplicationContext());
        agenceDao = new AgenceDao((this.getApplicationContext()));
        vehiculeDao = new VehiculeDao(this.getApplicationContext());
        typeVehiculeDao = new TypeVehiculeDao(this.getApplicationContext());
        typeCarburantDao = new TypeCarburantDao(this.getApplicationContext());
        marqueDao = new MarqueDao(this.getApplicationContext());
        locationDao = new LocationDao(this.getApplicationContext());
        clientDao = new ClientDao(this.getApplicationContext());
        photoDao = new PhotoDao(this.getApplicationContext());
        //ENREGISTREMENT DONNEES

        Agence agenceTEST = agenceTEST = new Agence(1, 44000, "NANTES", "10 rue du con qui dort", "Saint Herblain");
        agenceDao.insertOrUpdate(agenceTEST);

        Personne personneTest = new Personne(1, agenceTEST, "Jean-claude", "DUSS", "JCD", "JCD");
        personneDao.insertOrUpdate(personneTest);

        TypeVehicule typeVehicule1 = new TypeVehicule(1, "Routière");
        TypeVehicule typeVehicule2 = new TypeVehicule(2, "Ville");
        typeVehiculeDao.insertOrUpdate(typeVehicule1);
        typeVehiculeDao.insertOrUpdate(typeVehicule2);

        TypeCarburant typeCarburant1 = new TypeCarburant(1, "Sans Plomb 95");
        TypeCarburant typeCarburant2 = new TypeCarburant(2, "Sans Plomb 98");
        TypeCarburant typeCarburant3 = new TypeCarburant(3, "Diesel");
        typeCarburantDao.insertOrUpdate(typeCarburant1);
        typeCarburantDao.insertOrUpdate(typeCarburant2);
        typeCarburantDao.insertOrUpdate(typeCarburant3);

        Marque marque1 = new Marque(1, "Opel");
        Marque marque2 = new Marque(2, "Renault");
        Marque marque3 = new Marque(3, "Audi");
        Marque marque4 = new Marque(4, "Mercedes");
        Marque marque5 = new Marque(5, "Mini");
        marqueDao.insertOrUpdate(marque1);
        marqueDao.insertOrUpdate(marque2);
        marqueDao.insertOrUpdate(marque3);
        marqueDao.insertOrUpdate(marque4);
        marqueDao.insertOrUpdate(marque5);

        Photo photo = new Photo("TEST");
        int id = (int) photoDao.insert(photo);
        photo.setId(id);

        Vehicule vehicule1 = new Vehicule(1, agenceTEST, typeVehicule1, typeCarburant1, 54700, 50, false, "Clio 1", "ER-874-DF", photo, marque1);
        Vehicule vehicule2 = new Vehicule(2, agenceTEST, typeVehicule2, typeCarburant3, 189560, 65, true, "Clio 3", "MF-365-GE",photo, marque2);
        Vehicule vehicule3 = new Vehicule(3, agenceTEST, typeVehicule1, typeCarburant2, 39845, 200, true, "Classe C", "ND-987-BF",photo, marque4);
        Vehicule vehicule4 = new Vehicule(4, agenceTEST, typeVehicule2, typeCarburant1, 76987, 89, false, "Cooper", "QM-712-PO", photo,marque5);
        vehiculeDao.insertOrUpdate(vehicule1);
        vehiculeDao.insertOrUpdate(vehicule2);
        vehiculeDao.insertOrUpdate(vehicule3);
        vehiculeDao.insertOrUpdate(vehicule4);

        /*Client clientLocation = new Client(1,44140,633256985,"zz@gmail.fr","zidane","zinedine","12 rue","marseille");
        clientDao.insert(clientLocation);

        Location location1 = new Location(1,clientLocation, vehicule2, new Date(118,02,2),new Date(118,02,2),0,true,0);
        Location location2 = new Location(2,clientLocation, vehicule3, new Date(118,01,2),new Date(118,01,2),0,true,0);

        locationDao.insertOrUpdate(location1);
        locationDao.insertOrUpdate(location2);


        Location locationFinie1 = new Location(3,clientLocation, vehicule2, new Date(118,02,2),new Date(118,02,2),0,false,100);
        Location locationFinie2 = new Location(4,clientLocation, vehicule2, new Date(118,02,2),new Date(118,02,2),0,false,200);
        Location locationFinie3 = new Location(5,clientLocation, vehicule3, new Date(118,02,2),new Date(118,02,2),0,false,300);
        Location locationFinie4 = new Location(6,clientLocation, vehicule4, new Date(118,02,2),new Date(118,02,2),0,false,400);

        locationDao.insertOrUpdate(locationFinie1);
        locationDao.insertOrUpdate(locationFinie2);
        locationDao.insertOrUpdate(locationFinie3);
        locationDao.insertOrUpdate(locationFinie4);*/

    }

    public void loginCheck(View view) {

        if (personneDao.isRegistered(edLogin.getText().toString(), edPassword.getText().toString())) {

            /*SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            int name = prefs.getInt("idPersonne", 0);
            int idName = prefs.getInt("idAgence", 0);*/


            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this, "Identifiant ou mot de passe incorrect", Toast.LENGTH_LONG).show();
            edPassword.setText("");
        }

    }
}

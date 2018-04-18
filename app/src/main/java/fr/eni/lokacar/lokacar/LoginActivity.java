package fr.eni.lokacar.lokacar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import fr.eni.lokacar.lokacar.been.Agence;
import fr.eni.lokacar.lokacar.been.Marque;
import fr.eni.lokacar.lokacar.been.Personne;
import fr.eni.lokacar.lokacar.been.TypeCarburant;
import fr.eni.lokacar.lokacar.been.TypeVehicule;
import fr.eni.lokacar.lokacar.been.Vehicule;
import fr.eni.lokacar.lokacar.dao.AgenceDao;
import fr.eni.lokacar.lokacar.dao.MarqueDao;
import fr.eni.lokacar.lokacar.dao.PersonneDao;
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

        //ENREGISTREMENT DONNEES
        /*
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


        Vehicule vehicule1 = new Vehicule(1, agenceTEST, typeVehicule1, typeCarburant1, 54700, 50, false, "Voiture numero une", "ER-874-DF", marque1);
        Vehicule vehicule2 = new Vehicule(2, agenceTEST, typeVehicule2, typeCarburant3, 189560, 65, false, "Voiture numero quatre", "MF-365-GE", marque2);
        Vehicule vehicule3 = new Vehicule(3, agenceTEST, typeVehicule1, typeCarburant2, 39845, 200, false, "Voiture numero trois", "ND-987-BF", marque4);
        Vehicule vehicule4 = new Vehicule(4, agenceTEST, typeVehicule2, typeCarburant1, 76987, 89, false, "Voiture numero cinq", "QM-712-PO", marque5);
        vehiculeDao.insertOrUpdate(vehicule1);
        vehiculeDao.insertOrUpdate(vehicule2);
        vehiculeDao.insertOrUpdate(vehicule3);
        vehiculeDao.insertOrUpdate(vehicule4);*/
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

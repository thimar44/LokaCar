package fr.eni.lokacar.lokacar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import fr.eni.lokacar.lokacar.been.Agence;
import fr.eni.lokacar.lokacar.been.Personne;
import fr.eni.lokacar.lokacar.dao.AgenceDao;
import fr.eni.lokacar.lokacar.dao.PersonneDao;

public class LoginActivity extends AppCompatActivity {

    private EditText edPassword;
    private EditText edLogin;
    private PersonneDao personneDao;
    private AgenceDao agenceDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("LOCAKAR");

        edPassword = findViewById(R.id.password);
        edLogin = findViewById(R.id.login);
        personneDao = new PersonneDao(this.getApplicationContext());
        agenceDao = new AgenceDao((this.getApplicationContext()));


        Agence agenceTEST = agenceTEST = new Agence(1,44000,"NANTES", "10 rue du con qui dort", "Saint Herblain");
        agenceDao.insertOrUpdate(agenceTEST);

        Personne personneTest = new Personne(1,agenceTEST,"Jean-claude","DUSS","JCD","JCD");
        personneDao.insertOrUpdate(personneTest);


    }

    public void loginCheck(View view) {

        if(personneDao.isRegistered(edLogin.getText().toString(), edPassword.getText().toString()))

        {
           Intent intent = new Intent(LoginActivity.this, MainActivity.class);
           startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this, "Identifiant ou mot de passe incorrect", Toast.LENGTH_LONG).show();
            edPassword.setText("");
        }

    }
}

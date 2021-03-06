package fr.eni.lokacar.lokacar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import fr.eni.lokacar.lokacar.been.Agence;
import fr.eni.lokacar.lokacar.been.Marque;
import fr.eni.lokacar.lokacar.been.TypeCarburant;
import fr.eni.lokacar.lokacar.been.TypeVehicule;
import fr.eni.lokacar.lokacar.been.Vehicule;
import fr.eni.lokacar.lokacar.dao.AgenceDao;
import fr.eni.lokacar.lokacar.dao.MarqueDao;
import fr.eni.lokacar.lokacar.dao.TypeCarburantDao;
import fr.eni.lokacar.lokacar.dao.TypeVehiculeDao;
import fr.eni.lokacar.lokacar.dao.VehiculeDao;

import static fr.eni.lokacar.lokacar.helper.DataContract.MY_PREFS_NAME;

public class FiltrerActivity extends AppCompatActivity {
    private MarqueDao marqueDao;
    private TypeCarburantDao typeCarburantDao;
    private TypeVehiculeDao typeVehiculeDao;

    private Spinner vehiculeMarque;
    private Spinner vehiculeCarburant;
    private Spinner vehiculeType;
    private Spinner etatVehicule;
    private TextView textEtat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrer);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //TOOLBAR
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String villeAgence = prefs.getString("agenceName", " ");
        setTitle(getText(R.string.app_name) + " " + villeAgence);

        Toolbar toolbar = findViewById(R.id.ourToolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.TitleFiltrerActivity);
        //END TOOLBAR

        Context context = this.getApplicationContext();
        marqueDao = new MarqueDao(context);
        typeCarburantDao = new TypeCarburantDao(context);
        typeVehiculeDao = new TypeVehiculeDao(context);

        vehiculeMarque = findViewById(R.id.RechercherVehiculemarque);
        vehiculeCarburant = findViewById(R.id.RechercherVehiculetypeCarburant);
        vehiculeType = findViewById(R.id.RechercherVehiculetypeVehicule);
        etatVehicule = findViewById(R.id.etatVehiculeRechercher);

        spinnerParsage();
    }


    //Remplissage des spinners
    private void spinnerParsage() {

        List<Marque> marques = marqueDao.getListe();
        List<TypeCarburant> typeCarburants = typeCarburantDao.getListe();
        List<TypeVehicule> typeVehicules = typeVehiculeDao.getListe();
        List<String> etatsVehicule = new ArrayList<>();
        etatsVehicule.add("Tous");
        etatsVehicule.add("Oui");
        etatsVehicule.add("Non");

        Marque marque = new Marque("Tous");
        TypeCarburant typeCarburant = new TypeCarburant("Tous");
        TypeVehicule typeVehicule = new TypeVehicule("Tous");

        marques.add(0, marque);
        typeCarburants.add(0, typeCarburant);
        typeVehicules.add(0, typeVehicule);


        ArrayAdapter<Marque> adapterMarques = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_dropdown, marques);
        ArrayAdapter<String> adapterEtats = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_dropdown, etatsVehicule);
        ArrayAdapter<TypeCarburant> adapterTypeCarburant = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_dropdown, typeCarburants);
        ArrayAdapter<TypeVehicule> adapterTypeVehicule = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_dropdown, typeVehicules);

        adapterMarques.setDropDownViewResource(R.layout.spinner_dropdown);
        adapterTypeCarburant.setDropDownViewResource(R.layout.spinner_dropdown);
        adapterTypeVehicule.setDropDownViewResource(R.layout.spinner_dropdown);
        adapterEtats.setDropDownViewResource(R.layout.spinner_dropdown);

        vehiculeMarque.setAdapter(adapterMarques);
        vehiculeCarburant.setAdapter(adapterTypeCarburant);
        vehiculeType.setAdapter(adapterTypeVehicule);
        etatVehicule.setAdapter(adapterEtats);
    }

    public void RechercherVehicules(View view) {
            Marque marque = (Marque) vehiculeMarque.getSelectedItem();
            TypeCarburant typeCarburant = (TypeCarburant) vehiculeCarburant.getSelectedItem();
            TypeVehicule typeVehicule = (TypeVehicule) vehiculeType.getSelectedItem();
            String stateVehicule = (String) etatVehicule.getSelectedItem();

                Intent intent = new Intent(FiltrerActivity.this, MainActivity.class);
                intent.putExtra("idMarque", marque.getId());
                intent.putExtra("idTypeCarburant", typeCarburant.getId());
                intent.putExtra("idTypeVehicule", typeVehicule.getId());
                intent.putExtra("etat", stateVehicule);
                startActivity(intent);

    }

}

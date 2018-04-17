package fr.eni.lokacar.lokacar;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;
import fr.eni.lokacar.lokacar.adapter.RecycledModeleAdapter;
import fr.eni.lokacar.lokacar.been.Vehicule;
import fr.eni.lokacar.lokacar.dao.VehiculeDao;
import fr.eni.lokacar.lokacar.fragment.ListFragment;
import fr.eni.lokacar.lokacar.handler.ActivityMessage;

public class MainActivity extends AppCompatActivity implements ActivityMessage,
        ListFragment.OnListFragmentInteractionListener {

    private List<Vehicule> lstVehicules;
    private AlertDialog chargeAlert;
    private VehiculeDao vehiculeDao;
    private ListFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //Récupère la toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            //associe la toolbar
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("A VOIR");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
        //Deux fragments
        listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.listFragment);


        //Dao Modele
        vehiculeDao = new VehiculeDao(MainActivity.this);
        lstVehicules = new ArrayList<Vehicule>();


        //Création de la fenêtre de chargement
        AlertDialog.Builder build = new AlertDialog.Builder(MainActivity.this, R.style.CustomDialog);
        build.setView(R.layout.chargement_layout);
        chargeAlert = build.create();


        lstVehicules = vehiculeDao.getListe();

        if (listFragment != null && listFragment.isInLayout()) {
            setAdapterListe();
        }


    }

    //Affiche le dialog de chargement
    @Override
    public void onStartMessage() {
        chargeAlert.show();
    }

    /**
     * Appeler après le service les modèles doivent être en base
     *
     * @param i int
     */
    @Override
    public void onProgressMessage(int i) {
        lstVehicules = vehiculeDao.getListe();

        if (listFragment != null && listFragment.isInLayout()) {
            setAdapterListe();
        }

        //Cache le dialog
        chargeAlert.hide();

    }

    @Override
    public void onEndMessage() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Détruit le AlertDialog
        if (chargeAlert != null) {
            chargeAlert.dismiss();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Click sur un item de la RecyclerView
     *
     * @param item Modele
     */
    @Override
    public void onListFragmentInteraction(final Vehicule item) {
        //TODO Intent intent = new Intent(ModeleActivity.this, LouerActivity.class);
        //intent.putExtra("car", item.getId());
        //startActivity(intent);
    }


    //Renseigne la liste au fr&agment contenant le RecyclerView
    private void setAdapterListe() {
        listFragment.setAdapter(new RecycledModeleAdapter(lstVehicules, MainActivity.this));
    }
}
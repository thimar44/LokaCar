package fr.eni.lokacar.lokacar;

import android.Manifest;
import android.content.Intent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import fr.eni.lokacar.lokacar.adapter.RecycledModeleAdapter;
import fr.eni.lokacar.lokacar.been.Marque;
import fr.eni.lokacar.lokacar.been.TypeCarburant;
import fr.eni.lokacar.lokacar.been.TypeVehicule;
import fr.eni.lokacar.lokacar.been.Vehicule;
import fr.eni.lokacar.lokacar.dao.MarqueDao;
import fr.eni.lokacar.lokacar.dao.TypeCarburantDao;
import fr.eni.lokacar.lokacar.dao.TypeVehiculeDao;
import fr.eni.lokacar.lokacar.dao.VehiculeDao;
import fr.eni.lokacar.lokacar.fragment.ListFragment;
import fr.eni.lokacar.lokacar.handler.ActivityMessage;

import static fr.eni.lokacar.lokacar.helper.DataContract.MY_PREFS_NAME;

public class MainActivity extends AppCompatActivity implements ActivityMessage,
        ListFragment.OnListFragmentInteractionListener {

    private List<Vehicule> lstVehicules;
    private AlertDialog chargeAlert;
    private VehiculeDao vehiculeDao;
    private ListFragment listFragment;
    private FloatingActionButton fabButton;
    private MarqueDao marqueDao;
    private TypeVehiculeDao typeVehiculeDao;
    private TypeCarburantDao typeCarburantDao;

    private static boolean estFiltree;
    private static int MY_PERMISSIONS_REQUEST = 1;

    private int idMarqueIntent;
    private int idTypeCarburantIntent;
    private int idTypeVehiculeIntent;
    private String etatIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        fabButton = findViewById(R.id.fabButton);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST);

            }
        } else {

        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST);

            }
        } else {

        }

        /*Intent intent = getIntent();
        if (intent != null) {
            idMarqueIntent = intent.getIntExtra("idMarque", 0);
            idTypeCarburantIntent = intent.getIntExtra("idTypeCarburant", 0);
            idTypeVehiculeIntent = intent.getIntExtra("idTypeVehicule", 0);
            etatIntent = intent.getBooleanExtra("etat", false);
            if (idMarqueIntent != 0 || idTypeCarburantIntent != 0 || idTypeVehiculeIntent != 0) {
                estFiltree = true;
                marqueDao = new MarqueDao(this);
                typeVehiculeDao = new TypeVehiculeDao(this);
                typeCarburantDao = new TypeCarburantDao(this);
            }
        }*/


        //TOOLBAR
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String villeAgence = prefs.getString("agenceName", " ");
        setTitle(getText(R.string.app_name) + " " + villeAgence);

        android.widget.Toolbar toolbar = findViewById(R.id.ourToolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.TitleMainActivity);


        //Deux fragments
        listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.listFragment);
        //Dao Modele
        vehiculeDao = new VehiculeDao(MainActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        estFiltree = false;
        lstVehicules = new ArrayList<Vehicule>();

        //Création de la fenêtre de chargement
        AlertDialog.Builder build = new AlertDialog.Builder(MainActivity.this, R.style.CustomDialog);
        build.setView(R.layout.chargement_layout);
        chargeAlert = build.create();

        Intent intent = getIntent();
        if (intent != null) {
            idMarqueIntent = intent.getIntExtra("idMarque", 0);
            idTypeCarburantIntent = intent.getIntExtra("idTypeCarburant", 0);
            idTypeVehiculeIntent = intent.getIntExtra("idTypeVehicule", 0);
            etatIntent = intent.getStringExtra("etat");

            if (idMarqueIntent != 0 || idTypeCarburantIntent != 0 || idTypeVehiculeIntent != 0 || etatIntent != null) {
                estFiltree = true;
                marqueDao = new MarqueDao(this);
                typeVehiculeDao = new TypeVehiculeDao(this);
                typeCarburantDao = new TypeCarburantDao(this);
            }
        }

        if (!estFiltree) {
            lstVehicules = vehiculeDao.getListe();
        } else {
            Marque marque = marqueDao.getMarqueFromId(idMarqueIntent);
            TypeCarburant typeCarburant = typeCarburantDao.getTypeCarburantFromId(idTypeCarburantIntent);
            TypeVehicule typeVehicule = typeVehiculeDao.getTypeVehiculeFromId(idTypeVehiculeIntent);
            int valueEtat = -1;
            if (etatIntent.equals("Oui")) {
                valueEtat = 1;
            } else if (etatIntent.equals("Non")) {
                valueEtat = 0;
            }
            lstVehicules = vehiculeDao.getListeWithParams(typeVehicule, marque, typeCarburant, valueEtat);
        }

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, FiltrerActivity.class));
                break;
            case R.id.action_stats:
                startActivity(new Intent(MainActivity.this, StatsActivity.class));
                break;
        }
        return true;
    }

    /**
     * Click sur un item de la RecyclerView
     *
     * @param item Modele
     */
    @Override
    public void onListFragmentInteraction(final Vehicule item) {
        Intent intent;
        if (item.isEnLocation()) {
            intent = new Intent(MainActivity.this, RetourActivity.class);
        } else {
            intent = new Intent(MainActivity.this, LouerActivity.class);
        }
        intent.putExtra("car", item.getId());
        startActivity(intent);
    }


    //Renseigne la liste au fr&agment contenant le RecyclerView
    private void setAdapterListe() {
        listFragment.setAdapter(new RecycledModeleAdapter(lstVehicules, MainActivity.this));
    }

    public void AddNewCar(View view) {
        Intent intent = new Intent(MainActivity.this, AddCarActivity.class);
        startActivity(intent);
    }
}
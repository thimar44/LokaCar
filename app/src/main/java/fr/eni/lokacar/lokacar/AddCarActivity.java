package fr.eni.lokacar.lokacar;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import fr.eni.lokacar.lokacar.been.Agence;
import fr.eni.lokacar.lokacar.been.Marque;
import fr.eni.lokacar.lokacar.been.Photo;
import fr.eni.lokacar.lokacar.been.TypeCarburant;
import fr.eni.lokacar.lokacar.been.TypeVehicule;
import fr.eni.lokacar.lokacar.been.Vehicule;
import fr.eni.lokacar.lokacar.dao.AgenceDao;
import fr.eni.lokacar.lokacar.dao.MarqueDao;
import fr.eni.lokacar.lokacar.dao.PhotoDao;
import fr.eni.lokacar.lokacar.dao.TypeCarburantDao;
import fr.eni.lokacar.lokacar.dao.TypeVehiculeDao;
import fr.eni.lokacar.lokacar.dao.VehiculeDao;

import static fr.eni.lokacar.lokacar.helper.DataContract.MY_PREFS_NAME;

public class AddCarActivity extends AppCompatActivity {

    private VehiculeDao vehiculeDao;
    private MarqueDao marqueDao;
    private TypeCarburantDao typeCarburantDao;
    private TypeVehiculeDao typeVehiculeDao;
    private AgenceDao agenceDao;
    private PhotoDao photoDao;

    private EditText vehiculeDenomination;
    private EditText vehiculeImmatriculation;
    private Spinner vehiculeMarque;
    private Spinner vehiculeCarburant;
    private Spinner vehiculeType;
    private EditText vehiculeKilometrage;
    private EditText vehiculePrixJour;
    private ImageView vehiculePhoto;

    private String mCurrentPhotoPath;

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //TOOLBAR
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String villeAgence = prefs.getString("agenceName", " ");
        setTitle(getText(R.string.app_name) + " " + villeAgence);

        Toolbar toolbar = findViewById(R.id.ourToolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.TitleAddCarActivity);
        //END TOOLBAR

        Context context = this.getApplicationContext();
        vehiculeDao = new VehiculeDao(context);
        marqueDao = new MarqueDao(context);
        typeCarburantDao = new TypeCarburantDao(context);
        typeVehiculeDao = new TypeVehiculeDao(context);
        agenceDao = new AgenceDao(context);
        photoDao = new PhotoDao(context);

        vehiculeDenomination = findViewById(R.id.AddVehiculedenomination);
        vehiculeImmatriculation = findViewById(R.id.AddVehiculeImmatriculation);
        vehiculeMarque = findViewById(R.id.AddVehiculemarque);
        vehiculeCarburant = findViewById(R.id.AddVehiculetypeCarburant);
        vehiculeType = findViewById(R.id.AddVehiculetypeVehicule);
        vehiculeKilometrage = findViewById(R.id.AddVehiculeKilometrage);
        vehiculePrixJour = findViewById(R.id.AddVehiculePrixJour);
        vehiculePhoto = findViewById(R.id.AddVehiculePicture);

        spinnerParsage();
    }


    //Remplissage des spinners
    private void spinnerParsage() {

        List<Marque> marques = marqueDao.getListe();
        List<TypeCarburant> typeCarburants = typeCarburantDao.getListe();
        List<TypeVehicule> typeVehicules = typeVehiculeDao.getListe();

        ArrayAdapter<Marque> adapterMarques = new ArrayAdapter(this, R.layout.spinner_dropdown, marques);
        ArrayAdapter<TypeCarburant> adapterTypeCarburant = new ArrayAdapter<TypeCarburant>(getApplicationContext(), R.layout.spinner_dropdown, typeCarburants);
        ArrayAdapter<TypeVehicule> adapterTypeVehicule = new ArrayAdapter<TypeVehicule>(getApplicationContext(), R.layout.spinner_dropdown, typeVehicules);

        adapterMarques.setDropDownViewResource(R.layout.spinner_dropdown);
        adapterTypeCarburant.setDropDownViewResource(R.layout.spinner_dropdown);
        adapterTypeVehicule.setDropDownViewResource(R.layout.spinner_dropdown);

        vehiculeMarque.setAdapter(adapterMarques);
        vehiculeCarburant.setAdapter(adapterTypeCarburant);
        vehiculeType.setAdapter(adapterTypeVehicule);
    }

    public void addPhoto(View view) {

        dispatchTakePictureIntent();
    }

    public void addVehicule(View view) {
        if (validateForm()) {
            Photo photo = null;
            if (mCurrentPhotoPath != "") {
                photo = new Photo(mCurrentPhotoPath);
                long id = photoDao.insert(photo);
                if(id != 0){
                    photo.setId((int) id);
                }
            }


            String denomination = vehiculeDenomination.getText().toString();
            String immatriculation = vehiculeImmatriculation.getText().toString();
            Marque marque = (Marque) vehiculeMarque.getSelectedItem();
            TypeCarburant typeCarburant = (TypeCarburant) vehiculeCarburant.getSelectedItem();
            TypeVehicule typeVehicule = (TypeVehicule) vehiculeType.getSelectedItem();
            int kilometrage = Integer.parseInt(vehiculeKilometrage.getText().toString());
            int prixJour = Integer.parseInt(vehiculePrixJour.getText().toString());

            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            int idAgence = prefs.getInt("idAgence", 0);
            Agence agence = agenceDao.getAgenceFromId(idAgence);

            Vehicule vehicule;
            if (photo != null) {
                vehicule = new Vehicule(agence, typeVehicule, typeCarburant, kilometrage, prixJour, false, denomination, immatriculation, photo, marque);
            } else {
                vehicule = new Vehicule(agence, typeVehicule, typeCarburant, kilometrage, prixJour, false, denomination, immatriculation, marque);
            }
            long idVehicule = vehiculeDao.insertOrUpdate(vehicule);


            if (idVehicule != -1) {
                Intent intent = new Intent(AddCarActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(AddCarActivity.this, "Une erreur s'est produite", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(AddCarActivity.this, "Vérifier les champs saisis", Toast.LENGTH_LONG).show();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.v("LOG -> ", ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "fr.eni.lokacar.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, options);
            vehiculePhoto.setImageBitmap(bitmap);
        }
    }

    public boolean validateForm() {
        boolean formIsValide = true;
        if ("".equals(vehiculeDenomination.getText().toString())) formIsValide = false;
        if ("".equals(vehiculeImmatriculation.getText().toString())) formIsValide = false;
        if ("".equals(vehiculeMarque.toString())) formIsValide = false;
        if ("".equals(vehiculeCarburant.toString())) formIsValide = false;
        if ("".equals(vehiculeType.toString())) formIsValide = false;
        if ("".equals(vehiculeKilometrage.getText().toString())) formIsValide = false;
        if ("".equals(vehiculePrixJour.getText().toString())) formIsValide = false;
        return formIsValide;
    }
}


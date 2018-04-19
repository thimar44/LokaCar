package fr.eni.lokacar.lokacar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.eni.lokacar.lokacar.been.Agence;
import fr.eni.lokacar.lokacar.been.Client;
import fr.eni.lokacar.lokacar.been.Location;
import fr.eni.lokacar.lokacar.been.LocationPhoto;
import fr.eni.lokacar.lokacar.been.Marque;
import fr.eni.lokacar.lokacar.been.Photo;
import fr.eni.lokacar.lokacar.been.TypeCarburant;
import fr.eni.lokacar.lokacar.been.TypeVehicule;
import fr.eni.lokacar.lokacar.been.Vehicule;
import fr.eni.lokacar.lokacar.dao.AgenceDao;
import fr.eni.lokacar.lokacar.dao.ClientDao;
import fr.eni.lokacar.lokacar.dao.LocationDao;
import fr.eni.lokacar.lokacar.dao.LocationPhotoDao;
import fr.eni.lokacar.lokacar.dao.MarqueDao;
import fr.eni.lokacar.lokacar.dao.PhotoDao;
import fr.eni.lokacar.lokacar.dao.TypeCarburantDao;
import fr.eni.lokacar.lokacar.dao.TypeVehiculeDao;
import fr.eni.lokacar.lokacar.dao.VehiculeDao;

import static fr.eni.lokacar.lokacar.helper.DataContract.MY_PREFS_NAME;

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
    private ImageView photoVoiture;

    private ClientDao clientDao;
    private LocationDao locationDao;
    private VehiculeDao vehiculeDao;
    private PhotoDao photoDao;
    private LocationPhotoDao locationPhotoDao;

    private Client client;

    private List<String> mCurrentPhotoPath = new ArrayList<>();

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_louer);

        //TOOLBAR
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String villeAgence = prefs.getString("agenceName", " ");
        setTitle(getText(R.string.app_name) + " " + villeAgence);

        Toolbar toolbar = findViewById(R.id.ourToolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.TitleLouerActivity);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        Intent intent = getIntent();
        int vehiculeId = intent.getIntExtra("car", 0);

        Context context = this.getApplicationContext();
        locationDao = new LocationDao(context);
        photoDao = new PhotoDao(context);
        locationPhotoDao = new LocationPhotoDao(context);
        vehiculeDao = new VehiculeDao(context);

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


        photoVoiture = findViewById(R.id.photoVehicule);
        //Recupération de la photo
        try{
            String srcPhoto = vehicule.getPhoto().getUri();
            if (srcPhoto != null) {
                File imgFile = new File(srcPhoto);
                if (imgFile.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    photoVoiture.setImageBitmap(myBitmap);
                }
            }

        }catch(Exception e){
            Log.v("Prb image -> ", e.getMessage());
        }


        edClientNom = findViewById(R.id.clientNom);
        edClientPrenom = findViewById(R.id.clientPrenom);
        edClientTel = findViewById(R.id.clientTel);
        edClientEmail = findViewById(R.id.clientEmail);
        edClientAdresse = findViewById(R.id.clientAdresse);
        edClientCodePostal = findViewById(R.id.clientCp);
        edClientVille = findViewById(R.id.clientVille);

        // ******************************************************************    DUMMY  *****************************************************************
        edClientNom.setText("Pignon");
        edClientPrenom.setText("Francois");
        edClientTel.setText("15848");
        edClientEmail.setText("francois.pignon@ledinerdecon.fr");
        edClientAdresse.setText("palais de l'élisée");
        edClientCodePostal.setText("666");
        edClientVille.setText("Meulin");
        // ******************************************************************    DUMMY  *****************************************************************
    }

    public void addPhotoLouer(View view) {
        dispatchTakePictureIntent();
    }

    public void validerLouer(View view) {

        if (validateForm()) {
            client = new Client(
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
            client.setId((int) clientId);

            Location location = new Location();
            location.setClient(client);
            location.setVehicule(vehicule);

            location.setDateDebut(Calendar.getInstance().getTime());
            location.setDateFin(Calendar.getInstance().getTime());
            location.setEtat(true);
            location.setKilometrageParcouru(0);

            locationDao = new LocationDao(this.getApplicationContext());
            int idLocation = (int) locationDao.insert(location);
            location.setId(idLocation);

            vehicule.setEnLocation(true);
            vehiculeDao.update(vehicule);


            for (String path : mCurrentPhotoPath) {
                Photo photo = null;
                if (path != "") {
                    photo = new Photo(path);
                    long id = photoDao.insert(photo);
                    if (id != 0) {
                        photo.setId((int) id);
                        LocationPhoto newLocationPhoto = new LocationPhoto(location, photo, "LOUER");
                        locationPhotoDao.insert(newLocationPhoto);
                    }
                }
            }


            AlertDialog.Builder builder = new AlertDialog.Builder(LouerActivity.this);
            builder.setMessage("Voulez vous envoyer un récapitulatif au client")
                    .setTitle("");

            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + client.getNumeroTelephone()));

                    String message = "Bonjour " + client.getPrenom() + " " + client.getNom() + ",\n";
                    message += "Nous avons le plaisir de vous comfirmer la location du véhicule " + vehicule.getDesignation();
                    intent.putExtra("sms_body", message);
                    startActivity(intent);

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
        mCurrentPhotoPath.add(image.getAbsolutePath());
        return image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath.get(index), options);
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 350, 500, true);

            LinearLayout lL = findViewById(R.id.layoutPictures);

            ImageView imgView = new ImageView(this);

            imgView.setVisibility(View.VISIBLE);
            imgView.setImageBitmap(scaled);
            lL.addView(imgView);
            index++;
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

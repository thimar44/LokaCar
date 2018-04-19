package fr.eni.lokacar.lokacar;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import fr.eni.lokacar.lokacar.been.Client;
import fr.eni.lokacar.lokacar.been.Location;
import fr.eni.lokacar.lokacar.been.Vehicule;
import fr.eni.lokacar.lokacar.dao.ClientDao;
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


    private int prix;
    private LocationDao locationDao;
    private VehiculeDao vehiculeDao;

    private List<String> mCurrentPhotoPath = new ArrayList<>();
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static int index = 0;

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

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(location.getDateDebut());
        String day = calendar.get(Calendar.DAY_OF_MONTH)+"";
        String month =  (calendar.get(Calendar.MONTH)+1)+"";
        String year =  calendar.get(Calendar.YEAR)+"";

        Date DateFin = Calendar.getInstance().getTime();
        tvLocDateDebut.setText(day + "/" + month+"/" + year);
        long diffMillis= Math.abs(DateFin.getTime() - location.getDateDebut().getTime());
        long differenceInDays = TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS);
        tvLocDuree.setText(differenceInDays+"");

        prix = (int) differenceInDays * vehicule.getPrixJour();
        tvLocPrix.setText(prix+"€ TTC");
    }

    public void validerRetour(View view) {
        if (validateForm()) {

            int kmRetour = Integer.valueOf(edLocKm.getText().toString());
            int kmParcouru = kmRetour - vehicule.getKilometrage();


            location.setDateFin(Calendar.getInstance().getTime());
            location.setEtat(false);
            location.setKilometrageParcouru(kmParcouru);
            location.setPrix(prix);
            locationDao = new LocationDao(this.getApplicationContext());
            locationDao.update(location);

            vehicule.setEnLocation(false);
            vehicule.setKilometrage(kmRetour);
            vehiculeDao = new VehiculeDao(this.getApplicationContext());
            vehiculeDao.update(vehicule);

            AlertDialog.Builder builder = new AlertDialog.Builder(RetourActivity.this);
            builder.setMessage("Voulez vous envoyer une facture au client")
                    .setTitle("");

            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{client.getMail()});
                    i.putExtra(Intent.EXTRA_SUBJECT, "Facture Location");
                    String message = "Bonjour " + client.getPrenom() + " " + client.getNom() + ",\n";
                    message += "Votre facture est de " + location.getPrix() + " € TTC.";
                    i.putExtra(Intent.EXTRA_TEXT   , message);
                    try {
                        startActivity(Intent.createChooser(i, "Envoyer e-mail"));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(RetourActivity.this, "Vous n'avez pas de client e-mail installé.", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            });


            builder.setNegativeButton("non", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        } else {
            Toast.makeText(RetourActivity.this, "Vérifiez la saisie du kilométrage", Toast.LENGTH_LONG).show();
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
                Log.v("LOG -> " , ex.getMessage());
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
        if ("".equals(edLocKm.getText().toString())) formIsValide = false;
        if(vehicule.getKilometrage()>Integer.valueOf(edLocKm.getText().toString())) formIsValide = false;
        return formIsValide;
    }

    public void addPhotoLouer(View view) {
        dispatchTakePictureIntent();
    }
}

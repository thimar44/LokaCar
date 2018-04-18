package fr.eni.lokacar.lokacar;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import fr.eni.lokacar.lokacar.dao.StatsDao;

import static fr.eni.lokacar.lokacar.helper.DataContract.MY_PREFS_NAME;

public class StatsActivity extends AppCompatActivity {
    private TextView tvCATotal;
    private TextView tvCAMoyen;
    private TextView tvCAMoyenParVehicule;


    private List<StatsDao.ResultCAVehicule> caMoyenParVehicule;
    private int caTotal;
    private int caMoyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String villeAgence = prefs.getString("agenceName", " ");
        setTitle(getText(R.string.app_name) + " " + villeAgence);

        android.widget.Toolbar toolbar = findViewById(R.id.ourToolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.TitleStatsActivity);



        StatsDao statsDao = new StatsDao(this.getApplicationContext());

        caTotal = statsDao.getCATotal();
        tvCATotal = findViewById(R.id.CATotal);
        tvCATotal.setText(caTotal+"€");

        caMoyen = statsDao.getCAMoyen();
        tvCAMoyen = findViewById(R.id.CAMoyen);
        tvCAMoyen.setText(caMoyen+"€");

        tvCAMoyenParVehicule = findViewById(R.id.CAMoyenParVehicule);
        caMoyenParVehicule = statsDao.getCAMoyenVehicule();

        String text = "";
        for (StatsDao.ResultCAVehicule result: caMoyenParVehicule) {
            text+=result.toString();
        }
        tvCAMoyenParVehicule.setText(text);


    }
}

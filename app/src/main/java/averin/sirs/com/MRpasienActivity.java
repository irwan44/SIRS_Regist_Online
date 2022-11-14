package averin.sirs.com;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import averin.sirs.com.Adapter.MRpasienAdapter;
import averin.sirs.com.Model.MRpasien;

public class MRpasienActivity extends AppCompatActivity {

    RecyclerView MRpasien_Recyleview;
    private ArrayList<MRpasien> listMRpasien;
    private MRpasienAdapter MRpasienadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mrpasien2);

        //menerapkan tool bar sesuai id toolbar | ToolBarAtas adalah variabel buatan sndiri
        Toolbar LabToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(LabToolbar);
//        LabToolbar.setLogoDescription(getResources().getString(R.string.app_name));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LabToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        addData();
        MRpasien_Recyleview = (RecyclerView) findViewById(R.id.listMR_viewCycle);
        MRpasienadapter = new MRpasienAdapter(listMRpasien);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MRpasienActivity.this);
        MRpasien_Recyleview.setLayoutManager(layoutManager);
        MRpasien_Recyleview.setAdapter(MRpasienadapter);
    }
    void addData(){
        listMRpasien = new ArrayList<>();
        listMRpasien.add(new MRpasien("Klinik Averin Ceria", "Dr. Musa Nur Rahman", "Spesialis Kandungan", "2022-01-10 12:30"));
        listMRpasien.add(new MRpasien("Klinik CFW", "Dr. Bonge", "Spesialis Psikologi", "2022-11-02 10:00"));
        listMRpasien.add(new MRpasien("M.S Beauty", "Drs. Yusda Yusdi", "Spesialis Kecantikan", "2022-05-24 11:30"));
        listMRpasien.add(new MRpasien("Gambreng Dental", "Dr. Bambang Gambreng", "Spesialis Gigi", "2022-06-20 12:30"));
    }
}

package averin.sirs.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import averin.sirs.com.Adapter.RequestHandler;

public class DetailMR extends AppCompatActivity {

    String val_token, no_ktp, kd_klinik, idRegist, namaDokter, jenisPoli, tglPeriksa, keadaan_umum, tekanan_darah, suhu, tinggi_badan,
            kesadaran, nadi, pernafasan, bb, tindakan, nama_icd10, kd_resep;
    TextView txt_namapasien, txt_nomr, txt_namadokter, txt_namapoli, txt_tglperiksa, txt_KeadaanUmum, txt_TekananDarah, txt_suhu,
            txt_tinggi, txt_kesadaran, txt_nadi, txt_pernafasan, txt_bb, txt_nmobat1, txt_nmobat2, txt_nmobat3,
            txt_jmlobat1,txt_jmlobat2,txt_jmlobat3, txt_aturan1,txt_aturan2,txt_aturan3,txt_noteobat1,txt_noteobat2,
            txt_noteobat3, txt_ketobat1,txt_ketobat2,txt_ketobat3;
    EditText edt_tindakan, edt_icd10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mr);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        //menerapkan tool bar sesuai id toolbar | ToolBarAtas adalah variabel buatan sndiri
        Toolbar LabToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(LabToolbar);
//        LabToolbar.setLogoDescription(getResources().getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LabToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txt_namapasien = findViewById(R.id.txt_namapasien);
        txt_nomr = findViewById(R.id.txt_nomr);
        txt_namadokter = findViewById(R.id.txt_namadokter);
        txt_namapoli = findViewById(R.id.txt_namaPoli);
        txt_tglperiksa = findViewById(R.id.txt_tglperiksa);
        txt_KeadaanUmum = findViewById(R.id.txt_keadaanumum);
        txt_TekananDarah = findViewById(R.id.txt_TekananDarah);
        txt_suhu = findViewById(R.id.txt_Suhu);
        txt_tinggi = findViewById(R.id.txt_TinggiBadan);
        txt_nadi = findViewById(R.id.txt_Nadi);
        txt_kesadaran = findViewById(R.id.txt_Kesadaran);
        txt_bb = findViewById(R.id.txt_Berat);
        txt_pernafasan = findViewById(R.id.txt_Pernafasan);
        edt_tindakan = findViewById(R.id.edt_tindakan);
        edt_icd10 =  findViewById(R.id.edt_icd10);

        txt_nmobat1 = findViewById(R.id.nmobat1);
        txt_nmobat2 = findViewById(R.id.nmobat2);
        txt_nmobat3 = findViewById(R.id.nmobat3);

        txt_jmlobat1 = findViewById(R.id.jmlobat1);
        txt_jmlobat2 = findViewById(R.id.jmlobat2);
        txt_jmlobat3 = findViewById(R.id.jmlobat3);

        txt_aturan1 = findViewById(R.id.aturan1);
        txt_aturan2 = findViewById(R.id.aturan2);
        txt_aturan3 = findViewById(R.id.aturan3);

        txt_noteobat1 = findViewById(R.id.noteobat1);
        txt_noteobat2 = findViewById(R.id.noteobat2);
        txt_noteobat3 = findViewById(R.id.noteobat3);

        txt_ketobat1 = findViewById(R.id.ketobat1);
        txt_ketobat2 = findViewById(R.id.ketobat2);
        txt_ketobat3 = findViewById(R.id.ketobat3);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            namaDokter          = extras.get("nama_dokter").toString();
            jenisPoli           = extras.get("nama_poli").toString();
            tglPeriksa          = extras.get("tgl_periksa").toString();
            keadaan_umum        = extras.get("keadaan_umum").toString();
            tekanan_darah       = extras.get("tekanan_darah").toString();
            suhu                = extras.get("suhu").toString();
            tinggi_badan        = extras.get("tinggi_badan").toString();
            kesadaran           = extras.get("kesadaran").toString();
            nadi                = extras.get("nadi").toString();
            pernafasan          = extras.get("pernafasan").toString();
            bb                  = extras.get("bb").toString();
            tindakan            = extras.get("tindakan").toString();
            nama_icd10          = extras.get("nama_icd10").toString();
            kd_resep            = extras.get("kd_resep").toString();
        }

        if(kd_resep.equals("l1")){

            txt_nmobat1.setText("acetyl sistein / n asetil sistein tab");
            txt_jmlobat1.setText("12");
            txt_aturan1.setText("3 x 1");
            txt_noteobat1.setText("dihabiskan");
            txt_ketobat1.setText("Sesudah Makan");

            txt_nmobat2.setText("Acid Folic PROFOLAT");
            txt_jmlobat2.setText("23");
            txt_aturan2.setText("3 x 1");
            txt_noteobat2.setText("sampai sembuh");
            txt_ketobat2.setText("Sesudah Makan");

            txt_nmobat3.setText("Adalat oros 20mg");
            txt_jmlobat3.setText("25");
            txt_aturan3.setText("3 x 1");
            txt_noteobat3.setText("sampai kondisi mulai stabil");
            txt_ketobat3.setText("Sebelum Makan");
        }else if(kd_resep.equals("l2")){

            txt_nmobat1.setText("AMIODARON INJ 50 MG/ML IV");
            txt_jmlobat1.setText("16");
            txt_aturan1.setText("2 x 1");
            txt_noteobat1.setText("dihabiskan");
            txt_ketobat1.setText("Sesudah Makan");

            txt_nmobat2.setText("Acid Folic PROFOLAT");
            txt_jmlobat2.setText("10");
            txt_aturan2.setText("3 x 1");
            txt_noteobat2.setText("dihabiskan");
            txt_ketobat2.setText("Sesudah Makan");

            txt_nmobat3.setText("Amoxicillin 250ml");
            txt_jmlobat3.setText("12");
            txt_aturan3.setText("2 x 1");
            txt_noteobat3.setText("dhabiskan");
            txt_ketobat3.setText("\tSesudah Makan");
        }else if(kd_resep.equals("l3")) {

            txt_nmobat1.setText("Acid Folic PROFOLAT");
            txt_jmlobat1.setText("5");
            txt_aturan1.setText("3 x 1");
            txt_noteobat1.setText("dihabiskan");
            txt_ketobat1.setText("Sesudah Makan");

            txt_nmobat2.setText("Adalat oros 20mg");
            txt_jmlobat2.setText("24");
            txt_aturan2.setText("3 x 1");
            txt_noteobat2.setText("sampai jantung tidak berdetar");
            txt_ketobat2.setText("Sesudah Makan");

            txt_nmobat3.setText("Dulcolax 5 mg");
            txt_jmlobat3.setText("1");
            txt_aturan3.setText("2 x 1");
            txt_noteobat3.setText("bila perlu");
            txt_ketobat3.setText("Sesudah Makan\n");
        }

        txt_namadokter.setText(namaDokter);
        txt_namapoli.setText(jenisPoli);
        txt_tglperiksa.setText(tglPeriksa);
        txt_KeadaanUmum.setText(keadaan_umum);
        txt_TekananDarah.setText(tekanan_darah);
        txt_suhu.setText(suhu);
        txt_tinggi.setText(tinggi_badan);
        txt_nadi.setText(nadi);
        txt_kesadaran.setText(kesadaran);
        txt_pernafasan.setText(pernafasan);
        txt_bb.setText(bb);
        edt_tindakan.setText(tindakan);
        edt_icd10.setText(nama_icd10);

    }
}
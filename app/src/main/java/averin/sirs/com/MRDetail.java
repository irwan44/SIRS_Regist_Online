package averin.sirs.com;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MRDetail extends AppCompatActivity {

    String val_token, no_ktp, namaKlinik, namaDokter, jenisPoli, tglPeriksa, keadaan_umum, tekanan_darah, suhu, tinggi_badan,
            kesadaran, nadi, pernafasan, bb, tindakan, nama_icd10, kd_resep;
    TextView txt_namapasien, txt_nomr, txt_namadokter, txt_namapoli, txt_tglperiksa, txt_KeadaanUmum, txt_TekananDarah, txt_suhu,
            txt_tinggi, txt_kesadaran, txt_nadi, txt_pernafasan, txt_bb;
    EditText edt_tindakan, edt_icd10;
    ImageView imgKlinik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mr_pasien);
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

        }else if(kd_resep.equals("l2")){

        }else if(kd_resep.equals("l3")){

        }else if(kd_resep.equals("l4")){

        }

        txt_namapasien.setText("");
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

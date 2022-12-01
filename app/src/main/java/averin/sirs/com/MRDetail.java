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
    TextView txt_KeadaanUmum, txt_TekananDarah, txt_suhu, txt_tinggi, txt_kesadaran, txt_nadi, txt_pernafasan, txt_bb;
    EditText edt_tindakan, edt_icd10;
    ImageView imgKlinik;
    Button btn_scan;
    CardView cd_scan;
    DateFormat outputFormat = new SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault());
    DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat outputwaktu = new SimpleDateFormat("HH:mm", Locale.getDefault());
    DateFormat inputwaktu = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat sdf;
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
        if (extras != null) {();
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
            btn_scan.setVisibility(View.VISIBLE);
            cd_scan.setVisibility(View.VISIBLE);
            txt_info_antrian.setText("Datang 30 menit sebelum pemeriksaan dan \n " +
                    "konfirmasi kehadiran baik melalui FO atau Scan QR pada RS");
        }else if(kd_resep.equals("l2")){
            btn_scan.setVisibility(View.GONE);
            cd_scan.setVisibility(View.GONE);
            txt_info_antrian.setText("Silahkan datang ke FO dan membawa KTP anda \n untuk melakukan registrasi ulang");
        }else if(kd_resep.equals("l3")){

        }else if(kd_resep.equals("l4")){

        }

        Date tgl = null;
        Date wkt = null;
        try {
            tgl = inputFormat.parse(tgl_antri);
            wkt = inputwaktu.parse(jam_awal);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        tglKonvert = outputFormat.format(tgl);
        jam_konvert = outputwaktu.format(wkt);

        txt_noAntri.setText(no_antri);
        txt_nmDokter.setText(nm_dokter);
        txt_nmKlinik.setText(nm_klinik);
        txt_jnsPoli.setText(nm_bag);
        txt_wktPeriksa.setText(jam_awal+" - "+jam_akhir);
        txt_tglPeriksa.setText(tglKonvert);
        txt_jamPeriksa.setText(jam_konvert);
        if(stat_px.equals("lama")){

        }
//        if(flag_px.equals("0")){
//            lb_antrian.setText("No. Pesanan");
//        }else{
//            lb_antrian.setText("No. Antiran Saya");
//        }
    }

    //    SCANNER FUNCTION
    public void ScanKlinik(View v){
        Intent i = new Intent(AntrianDetail.this, ScanQRCode.class);
        i.putExtra("regId", regId);
        i.putExtra("tgl_antri", tgl_antri);
        i.putExtra("jam_awal", jam_awal);
        i.putExtra("jam_akhir", jam_akhir);
        i.putExtra("status_antri", status_antri);
        i.putExtra("nm_klinik", nm_klinik);
        i.putExtra("nm_bag", nm_bag);
        startActivity(i);
    }


}

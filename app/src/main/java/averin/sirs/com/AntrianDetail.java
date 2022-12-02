package averin.sirs.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
//Scanner Funct

public class AntrianDetail extends AppCompatActivity{

    String val_token, no_ktp, flag_px, regId, no_antri, nm_dokter, tgl_antri, jam_awal, jam_akhir, status_antri,
            nm_klinik, nm_bag, tglKonvert, jam_konvert, stat_px;
    TextView txt_nmKlinik, txt_jnsPoli, txt_nmDokter, txt_noAntri, txt_tglPeriksa, txt_jamPeriksa, txt_jnsPasien,
            txt_wktPeriksa, lb_antrian, txt_info_antrian, txt_stat_antri;
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

        setContentView(R.layout.activity_antrian_detail);
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

        txt_nmKlinik = findViewById(R.id.nmKlinik);
        txt_jnsPoli = findViewById(R.id.jenisPoli);
        txt_nmDokter = findViewById(R.id.nma_Dokter);
        txt_noAntri = findViewById(R.id.no_Antrian);
        txt_tglPeriksa = findViewById(R.id.tglPeriksa);
        txt_jamPeriksa = findViewById(R.id.jamPeriksa);
        txt_jnsPasien = findViewById(R.id.jenisPasien);
        txt_wktPeriksa = findViewById(R.id.wktPeriksa);
        txt_info_antrian = findViewById(R.id.txt_info_antrian);
        lb_antrian =  findViewById(R.id.lb_antrian);
        txt_stat_antri = findViewById(R.id.txt_stat_hadir);
        btn_scan = findViewById(R.id.btn_scan);
        cd_scan = findViewById(R.id.cd_btnscan);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
//            flag_px      = extras.get("px_flag").toString();
            regId        = extras.get("regId").toString();
            no_antri     = extras.get("noAntrian").toString();
            nm_dokter    = extras.get("nma_dokter").toString();
            tgl_antri    = extras.get("tgl_antri").toString();
            jam_awal     = extras.get("jam_awal").toString();
            jam_akhir    = extras.get("jam_akhir").toString();
            status_antri = extras.get("status_antri").toString();
            nm_klinik    = extras.get("nm_klinik").toString();
            nm_bag       = extras.get("nm_bag").toString();
            stat_px       = extras.get("stat_px").toString();
        }

        if(status_antri.equals("1")){
            btn_scan.setVisibility(View.GONE);
            txt_stat_antri.setVisibility(View.VISIBLE);
            txt_info_antrian.setText("Silahkan menuju FO untuk menanyakan status antrian Poli anda");
        }else {
//            btn_scan.setVisibility(View.VISIBLE);
//            txt_stat_antri.setVisibility(View.GONE);
            if (stat_px.equals("lama") || stat_px.equals("null")) {
                btn_scan.setVisibility(View.VISIBLE);
                cd_scan.setVisibility(View.VISIBLE);
                txt_info_antrian.setText("Datang 30 menit sebelum pemeriksaan dan \n konfirmasi kehadiran baik melalui FO atau \n Scan QR pada RS");
            } else {
                btn_scan.setVisibility(View.GONE);
                cd_scan.setVisibility(View.GONE);
                txt_info_antrian.setText("Silahkan datang ke FO dan membawa KTP anda \n untuk melakukan registrasi ulang");
            }
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
        i.putExtra("noAntrian", no_antri);
        i.putExtra("nma_dokter", nm_dokter);
        i.putExtra("tgl_antri", tgl_antri);
        i.putExtra("jam_awal", jam_awal);
        i.putExtra("jam_akhir", jam_akhir);
        i.putExtra("status_antri", status_antri);
        i.putExtra("nm_klinik", nm_klinik);
        i.putExtra("nm_bag", nm_bag);
        i.putExtra("stat_px", stat_px);
        startActivity(i);
    }
}
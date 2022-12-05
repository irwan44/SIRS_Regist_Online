package averin.sirs.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import averin.sirs.com.Adapter.RequestHandler;

public class DetailMR extends AppCompatActivity {

    String val_token, kodeklinik, idRegist, no_ktp, namaKlinik, namaDokter, jenisPoli, tglPeriksa, keadaan_umum, tekanan_darah, suhu, tinggi_badan,
            kesadaran, nadi, pernafasan, bb, tindakan, nama_icd10, kd_resep, nama_obat, note_obat, jml_obat, aturan_pakai, ket_obat;
    TextView txt_namapasien, txt_nomr, txt_namadokter, txt_namapoli, txt_tglperiksa, txt_KeadaanUmum, txt_TekananDarah, txt_suhu,
            txt_tinggi, txt_kesadaran, txt_nadi, txt_pernafasan, txt_bb, txt_nmobat,
            txt_jmlobat, txt_aturan, txt_noteobat, txt_ketobat, txt_no,
            txt_info_null;
    EditText edt_tindakan, edt_icd10;

    //Dialog Confirm
    AlertDialog.Builder dial_builder;
    AlertDialog dial_info_null;
    LayoutInflater inflater;
    View view_null_data;

    String APIurl = RequestHandler.APIdev;
    public String urlDetailMR = APIurl+"/api/vi/get-detail-riwayat.php";

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

        //Dialog Empty Data
        ViewGroup vg = findViewById(android.R.id.content);
        dial_builder = new AlertDialog.Builder(DetailMR.this,R.style.CustomAlertDialog);
        inflater = getLayoutInflater();
        txt_info_null = view_null_data.findViewById(R.id.txt_info_failed);
        view_null_data = inflater.inflate(R.layout.dialog_failed_regist, vg, false);
        Button btn_ok = view_null_data.findViewById(R.id.btn_oke_failed);
        dial_builder.setView(view_null_data);
        dial_info_null = dial_builder.create();
        dial_info_null.setCancelable(false);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dial_info_null.dismiss();
                Intent i = new Intent(DetailMR.this, MRpasienActivity.class);
                startActivity(i);
            }
        });

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
    private void getDetailMR() {
        final String iniToken = val_token;
        final String kd_klinik = kodeklinik;
        final String Regid = idRegist;

        class ambilDataMR extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("kode_klinik", kodeklinik);
                params.put("idReg", Regid);

                //returing the response
                return requestHandler.requestData(urlDetailMR, "POST", "application/json; charset=utf-8", "X-Api-Token",
                        iniToken, "X-Px-Key", "", params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                progressBar = findViewById(R.id.progressBar);
//                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                progressBar.setVisibility(View.GONE);

                try {//converting response to json object
                    JSONObject jr = new JSONObject(s);
                    //if no error in response
                    if (jr.getString("code").equals("500")) {
                        txt_info_null.setText(jr.getString("msg"));
                        dial_info_null.show();
                    } else if(jr.getString("code").equals("200")){
                        JSONArray res_vital_sign = jr.getJSONArray("vital_sign");
                        JSONArray res_tindakan = jr.getJSONArray("tindakan");
                        JSONArray res_icd10 = jr.getJSONArray("icd10");
                        JSONArray res_resep = jr.getJSONArray("resep");
                        int no = 1;

//                        Vital Sign
                        for(int vs = 0; vs < res_vital_sign.length(); vs++) {
                            JSONObject obj_vs = res_vital_sign.getJSONObject(vs);
                            keadaan_umum = obj_vs.getString("keadaan_umum");
                            tekanan_darah = obj_vs.getString("tekanan_darah");
                            suhu = obj_vs.getString("suhu");
                            tinggi_badan = obj_vs.getString("tinggi_badan");
                            kesadaran = obj_vs.getString("kesadaran_pasien");
                            nadi = obj_vs.getString("nadi");
                            pernafasan = obj_vs.getString("pernafasan");
                            bb = obj_vs.getString("berat_badan");

                            txt_KeadaanUmum.setText(keadaan_umum);
                            txt_TekananDarah.setText(tekanan_darah);
                            txt_suhu.setText(suhu);
                            txt_tinggi.setText(tinggi_badan);
                            txt_kesadaran.setText(kesadaran);
                            txt_nadi.setText(nadi);
                            txt_bb.setText(bb);
                        }

//                        Tindakan
                        for(int td = 0; td < res_tindakan.length(); td++) {
                            JSONObject obj_tdk = res_tindakan.getJSONObject(td);
//                            no++;
                            tindakan = obj_tdk.getString("nama_tindakan");
                            edt_tindakan.setText(no++ + ". "+tindakan);
                        }

//                        ICD-10
                        for(int icd = 0; icd < res_icd10.length(); icd++) {
                            JSONObject obj_icd = res_icd10.getJSONObject(icd);
//                            no++;
                            nama_icd10 = obj_icd.getString("nama_icd");
                            edt_icd10.setText(no++ + ". "+ nama_icd10);
                        }

//                        Resep Obat
                        for(int ro = 0; ro < res_resep.length(); ro++) {
                            JSONObject obj_ro = res_resep.getJSONObject(ro);
//                            no++;
                            nama_obat = obj_ro.getString("nama_brg");
                            note_obat = obj_ro.getString("note");
                            jml_obat = obj_ro.getString("jumlah_pesan");
                            aturan_pakai = obj_ro.getString("nama_dosis");
                            ket_obat = obj_ro.getString("ket");
                            txt_nmobat.setText(no++ + ". "+ nama_icd10);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        ambilDataMR pl = new ambilDataMR();
        pl.execute();
    }
}
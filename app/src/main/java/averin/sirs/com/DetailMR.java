package averin.sirs.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import averin.sirs.com.Adapter.DetailMRAdapter;
import averin.sirs.com.Adapter.KlinikAdapter;
import averin.sirs.com.Adapter.RequestHandler;
import averin.sirs.com.Adapter.ResepDokterAdapter;
import averin.sirs.com.Model.Klinik;
import averin.sirs.com.Model.Login;
import averin.sirs.com.Model.ResepObat;
import averin.sirs.com.Model.Token;
import averin.sirs.com.Model.isiSpinner;
import averin.sirs.com.Ui.AppController;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailMR extends AppCompatActivity {

    String val_token, no_ktp, foto_px, kodeklinik, idRegist,
            nama_px, nomr_px, umur_px, goldarah_px, gender_px,
            namaKlinik, namaDokter, namaBagian, tglPeriksa, wktPeriksa,
            keadaan_umum, tekanan_darah, suhu, tinggi_badan, kesadaran, nadi, pernafasan, bb,
            nama_obat,jml_obat,note_obat,ket_obat,aturan_pakai;
    TextView txt_namaklinik, txt_namadokter, txt_namapoli, txt_tglperiksa,
            txt_namapasien, txt_nomr, txt_umur, txt_goldarah, txt_jekel,
            txt_KeadaanUmum, txt_TekananDarah, txt_suhu, txt_tinggi, txt_kesadaran, txt_nadi, txt_pernafasan, txt_bb;

    //Dialog Confirm
    AlertDialog.Builder dial_builder;
    AlertDialog dial_info_null;
    LayoutInflater inflater;
    View view_null_data;
    CircleImageView imgPasien;

    //Tindakan & ICD10
    DetailMRAdapter adapt_tindakan, adapt_icd10;
    ListView lsTindakan, lsICD10;
    ArrayList<isiSpinner> listtindakan = new ArrayList<>();
    ArrayList<isiSpinner> listicd10 = new ArrayList<>();

    //Resep Dokter
    RecyclerView rc_tindakan, rc_icd10, rc_resepdokter;
    ResepDokterAdapter adaptResepDokter;
    List<ResepObat> listresep = new ArrayList<ResepObat>();
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;


    String APIurl = RequestHandler.APIdev;
    public String urlDetailMR = APIurl+"/api/v1/get-detail-riwayat.php";

    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mr);

//        LinearLayout linearLayout = findViewById(R.id.resepdolter);
//        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetBehavior);
//
//        Button button =findViewById(R.id.irwan);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//            }
//        });

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

        //GET DATA FROM CONTROLLER
        Login login = AppController.getInstance(this).getPasien();
        Token token = AppController.getInstance(this).isiToken();
        val_token = String.valueOf(token.gettoken());
        no_ktp    = String.valueOf(login.getKTP_pasien());
        nama_px   = String.valueOf(login.getNama_pasien());
        foto_px   = String.valueOf(login.getFoto_pasien());

        Bundle kiriman = getIntent().getExtras();
        if(kiriman != null){

            kodeklinik = kiriman.get("kd_klinik").toString();
            idRegist = kiriman.get("idRegKlinik").toString();

            namaKlinik = kiriman.get("nama_klinik").toString();
            namaBagian = kiriman.get("nama_bagian").toString();
            namaDokter = kiriman.get("nama_dokter").toString();
            tglPeriksa = kiriman.get("tgl_daftar").toString();

            gender_px = kiriman.get("gender_px").toString();
            goldarah_px = kiriman.get("goldarah_px").toString();

            getDetailMR();
        }

//        Card Pasien MR Info & Dokter Periksa
        imgPasien = findViewById(R.id.imgPasien);
        txt_namaklinik = findViewById(R.id.txt_namaklinik);
        txt_namadokter = findViewById(R.id.txt_namadokter);
        txt_namapoli = findViewById(R.id.txt_namaPoli);
        txt_tglperiksa = findViewById(R.id.txt_tgl_periksa);

        if(no_ktp.equals("3174586231698546")) {
            imgPasien.setImageResource(R.drawable.foto_bos);
        }else {
            if (foto_px.equals("null")) {
                imgPasien.setImageResource(R.drawable.profile22);
            } else {
                Glide.with(DetailMR.this).load(foto_px).into(imgPasien);
            }
        }

        txt_namaklinik.setText(namaKlinik);
        txt_namadokter.setText(namaDokter);
        txt_namapoli.setText(namaBagian);
        txt_tglperiksa.setText(tglPeriksa);

        txt_namapasien = findViewById(R.id.txt_namaPasien);
        txt_nomr = findViewById(R.id.txt_mrPasien);
        txt_umur = findViewById(R.id.txt_umurPasien);
        txt_goldarah = findViewById(R.id.txt_golDarah);
        txt_jekel = findViewById(R.id.txt_jekelPasien);

//        Vital Sign
        txt_KeadaanUmum = findViewById(R.id.txt_keadaanumum);
        txt_TekananDarah = findViewById(R.id.txt_TekananDarah);
        txt_suhu = findViewById(R.id.txt_Suhu);
        txt_tinggi = findViewById(R.id.txt_TinggiBadan);
        txt_nadi = findViewById(R.id.txt_Nadi);
        txt_kesadaran = findViewById(R.id.txt_Kesadaran);
        txt_bb = findViewById(R.id.txt_Berat);
        txt_pernafasan = findViewById(R.id.txt_Pernafasan);

//        Tindakan
        rc_tindakan = findViewById(R.id.rc_tindakan);
        rc_tindakan.setLayoutManager(new LinearLayoutManager(this));
        adapt_tindakan = new DetailMRAdapter(this, listtindakan,this);
        rc_tindakan.setAdapter(adapt_tindakan);

//        ICD10
        rc_icd10 = findViewById(R.id.rc_icd10);
        rc_icd10.setLayoutManager(new LinearLayoutManager(this));
        adapt_icd10 = new DetailMRAdapter(this, listicd10,this);
        rc_icd10.setAdapter(adapt_icd10);

//        Resep Obat
        rc_resepdokter = findViewById(R.id.rc_resepobat);
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        rc_resepdokter.setLayoutManager(RecyclerViewLayoutManager);
        HorizontalLayout = new LinearLayoutManager(DetailMR.this, LinearLayoutManager.HORIZONTAL,false);
        rc_resepdokter.setLayoutManager(HorizontalLayout);
        adaptResepDokter = new ResepDokterAdapter(listresep);
        rc_resepdokter.setAdapter(adaptResepDokter);

        //Dialog Empty Data
        ViewGroup vg = findViewById(android.R.id.content);
        dial_builder = new AlertDialog.Builder(DetailMR.this,R.style.CustomAlertDialog);
        inflater = getLayoutInflater();
//        txt_info_null = view_null_data.findViewById(R.id.txt_info_failed);
//        view_null_data = inflater.inflate(R.layout.dialog_failed_regist, vg, false);
//        Button btn_ok = view_null_data.findViewById(R.id.btn_oke_failed);
//        dial_builder.setView(view_null_data);
//        dial_info_null = dial_builder.create();
//        dial_info_null.setCancelable(false);
//        btn_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dial_info_null.dismiss();
//                Intent i = new Intent(DetailMR.this, MRpasienActivity.class);
//                startActivity(i);
//            }
//        });
    }
    private void getDetailMR() {
        final String iniToken = val_token;
        final String kdklinik = kodeklinik;
        final String Regid = idRegist;

        listtindakan.clear();listicd10.clear();

        class ambilDataMR extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("kode_klinik", kdklinik);
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
//                        txt_info_null.setText(jr.getString("msg"));
//                        dial_info_null.show();
                    } else if(jr.getString("code").equals("200")){
                        JSONArray res_vital_sign = jr.getJSONArray("vital_sign");
                        JSONArray res_tindakan = jr.getJSONArray("tindakan");
                        JSONArray res_icd10 = jr.getJSONArray("icd10");
                        JSONArray res_resep = jr.getJSONArray("resep");
//                        JSONObject res_nomr = jr.getJSONObject("no_mr");
//                        JSONObject res_umur = jr.getJSONObject("umur");

                        txt_namapasien.setText(nama_px);
                        txt_nomr.setText(jr.getString("no_mr"));
                        txt_umur.setText(jr.getString("umur"));
                        txt_goldarah.setText(goldarah_px);
                        txt_jekel.setText(gender_px);

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
                            txt_pernafasan.setText(pernafasan);
                            txt_tinggi.setText(tinggi_badan);
                            txt_kesadaran.setText(kesadaran);
                            txt_nadi.setText(nadi);
                            txt_bb.setText(bb);
                        }

//                        Tindakan
                        for(int td = 0; td < res_tindakan.length(); td++) {

                            isiSpinner item_tdk = new isiSpinner();
                            JSONObject obj_tdk = res_tindakan.getJSONObject(td);
                            item_tdk.setId(obj_tdk.getString("no_tdk"));
                            item_tdk.setKet(obj_tdk.getString("nama_tindakan"));
                            listtindakan.add(item_tdk);

                        }
                        adapt_tindakan.notifyDataSetChanged();

//                        ICD-10
                        for(int icd = 0; icd < res_icd10.length(); icd++) {

                            isiSpinner item_icd = new isiSpinner();
                            JSONObject obj_icd = res_icd10.getJSONObject(icd);
                            item_icd.setId(obj_icd.getString("no_icd"));
                            item_icd.setKet(obj_icd.getString("icd10"));
                            listicd10.add(item_icd);

                        }
                        adapt_icd10.notifyDataSetChanged();

//                        Resep Obat
                        for(int ro = 0; ro < res_resep.length(); ro++) {
                            JSONObject obj_ro = res_resep.getJSONObject(ro);
                            String nom = obj_ro.getString("no");
                            nama_obat = obj_ro.getString("nama_brg");
                            note_obat = obj_ro.getString("note");
                            jml_obat = obj_ro.getString("jumlah_pesan");
                            aturan_pakai = obj_ro.getString("nama_dosis");
                            ket_obat = obj_ro.getString("ket");

                            listresep.add(new ResepObat(nama_obat,note_obat,aturan_pakai,jml_obat,ket_obat));
                        }
                        adaptResepDokter.notifyDataSetChanged();
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
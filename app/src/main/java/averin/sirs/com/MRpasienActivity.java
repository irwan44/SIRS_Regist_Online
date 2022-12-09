package averin.sirs.com;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import averin.sirs.com.Adapter.MRpasienAdapter;
import averin.sirs.com.Adapter.RequestHandler;
import averin.sirs.com.Model.Klinik;
import averin.sirs.com.Model.Login;
import averin.sirs.com.Model.MRpasien;
import averin.sirs.com.Model.Token;
import averin.sirs.com.Ui.AppController;

public class MRpasienActivity extends AppCompatActivity {

    String val_token, no_ktp, iniktp, nama_px, umur_px, gender_px, goldarah_px,
            kode_klinik, nama_klinik, idReg, tgl_daftar, jam_awal, nama_bagian, nama_dokter, wkt_periksa;
    TextView tv_namapasien, tv_noktp, tv_goldarah, tv_gender, tv_umur;
    //    List MR
    RecyclerView MRpasien_Recyleview;
    private ArrayList<MRpasien> listMRpasien = new ArrayList<>();
    private MRpasienAdapter MRpasienadapter;
//    URL API
    public String APIurl = RequestHandler.APIdev;
    public String urlListMR = APIurl+"/api/v1/get-list-mr.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mrpasien2);

        //menerapkan tool bar sesuai id toolbar | ToolBarAtas adalah variabel buatan sndiri
        Toolbar LabToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(LabToolbar);
//        LabToolbar.setLogoDescription(getResources().getString(R.string.app_name));

        //GET DATA FROM CONTROLLER
        Login login = AppController.getInstance(this).getPasien();
        Token token = AppController.getInstance(this).isiToken();
        val_token = String.valueOf(token.gettoken());
        no_ktp    = String.valueOf(login.getKTP_pasien());

//        Declarate Object
//        tv_namapasien  = findViewById(R.id.txt_namaPasien);
//        tv_umur        = findViewById(R.id.txt_umurPasien);
//        tv_goldarah    = findViewById(R.id.txt_golDarah);
//        tv_gender      = findViewById(R.id.txt_jekelPasien);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LabToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        addData();
        viewMR();
        MRpasien_Recyleview = (RecyclerView) findViewById(R.id.listMR_viewCycle);
        MRpasienadapter = new MRpasienAdapter(listMRpasien);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MRpasienActivity.this);
        MRpasien_Recyleview.setLayoutManager(layoutManager);
        MRpasien_Recyleview.setAdapter(MRpasienadapter);
    }

    public void viewMR() {
        //first getting the values
        final String iniToken   = val_token;
        final String ktpPX      = no_ktp;
        listMRpasien.clear();

        //if everything is fine
        class masukPakEko extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("no_ktp", ktpPX);

                //returing the response
                return requestHandler.requestData(urlListMR, "POST", "application/json; charset=utf-8", "X-Api-Token",
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
                    JSONObject obj = new JSONObject(s);
                    //if no error in response
                    if(obj.getString("code").equals("500")){
//                        txt_infonull.setVisibility(View.VISIBLE);
                        MRpasien_Recyleview.setVisibility(View.GONE);

                    }else if(obj.getString("code").equals("200")){
                        MRpasien_Recyleview.setVisibility(View.VISIBLE);
                        JSONArray px = obj.getJSONArray("px");
                        JSONArray mr = obj.getJSONArray("res");

//                        Data Pasien
                        for(int p = 0; p < px.length(); p++) {
                            JSONObject jne = px.getJSONObject(p);
                            iniktp      = jne.getString("ktp_px");
                            nama_px     = jne.getString("nama_px");
                            gender_px   = jne.getString("gender");
                            goldarah_px = jne.getString("gol_darah");
                            umur_px     = jne.getString("umur");

                        }
                        for (int a = 0; a < mr.length(); a++) {
//                            txt_infonull.setVisibility(View.GONE);
                            JSONObject jso = mr.getJSONObject(a);
                            kode_klinik = jso.getString("kode_klinik");
                            nama_klinik = jso.getString("nama_klinik");
                            idReg = jso.getString("idReg");
                            tgl_daftar = jso.getString("tgl_daftar");
                            jam_awal = jso.getString("jam_awal");
                            nama_bagian = jso.getString("nama_bagian");
                            wkt_periksa = jso.getString("wkt_periksa");
                            nama_dokter = jso.getString("nama_dokter");
                            listMRpasien.add(new MRpasien(idReg,kode_klinik,nama_klinik,nama_dokter,nama_bagian,tgl_daftar,
                                    jam_awal, gender_px, umur_px, goldarah_px));
                        }
                        MRpasienadapter.notifyDataSetChanged();
//                        tv_namapasien.setText(nama_px);
////                        tv_noktp.setText(nama_px);
//                        tv_umur.setText(umur_px);
//                        tv_goldarah.setText(goldarah_px);
//                        if(gender_px.equals("l")){
//                            tv_gender.setText("Laki-laki");
//                        }else if (gender_px.equals("P")){
//                            tv_gender.setText("Perempuan");
//                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        masukPakEko pl = new masukPakEko();
        pl.execute();
    }
}

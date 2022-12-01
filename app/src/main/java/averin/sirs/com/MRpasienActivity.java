package averin.sirs.com;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

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

    String val_token, no_ktp;
    RecyclerView MRpasien_Recyleview;
    private ArrayList<MRpasien> listMRpasien;
    private MRpasienAdapter MRpasienadapter;

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
        listMRpasien.add(new MRpasien("dr. Pongky Andi Saputra", "Dr. Musa Nur Rahman", "Spesialis Kandungan",
                "2022-01-10 12:30"));
        listMRpasien.add(new MRpasien("dr. Bahrul Mubarok", "Dr. Bonge", "Spesialis Psikologi", "2022-11-02 10:00"));
        listMRpasien.add(new MRpasien("M.S Beauty", "Drs. Yusda Yusdi", "Spesialis Kecantikan", "2022-05-24 11:30"));
        listMRpasien.add(new MRpasien("Gambreng Dental", "Dr. Bambang Gambreng", "Spesialis Gigi", "2022-06-20 12:30"));listMRpasien.add(new MRpasien("Gambreng Dental", "Dr. Bambang Gambreng", "Spesialis Gigi", "2022-06-20 12:30"));
    }

//    public void viewMR() {
//        //first getting the values
//        final String iniToken   = val_token;
//        listMRpasien.clear();
//
//        //if everything is fine
//        class masukPakEko extends AsyncTask<Void, Void, String> {
//
//            private ProgressBar progressBar;
//
//            @Override
//            protected String doInBackground(Void... voids) {
//                //creating request handler object
//                RequestHandler requestHandler = new RequestHandler();
//
//                //creating request parameters
//                HashMap<String, String> params = new HashMap<>();
//                params.put("idKota", idKota);
////                params.put("User", user);
////                params.put("Pass", pass);
//
//                //returing the response
//                return requestHandler.requestData(urlListMR, "POST", "application/json; charset=utf-8", "X-Api-Token",
//                        iniToken, "X-Px-Key", "", params);
//            }
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
////                progressBar = findViewById(R.id.progressBar);
////                progressBar.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
////                progressBar.setVisibility(View.GONE);
//
//                try {//converting response to json object
//                    JSONObject obj = new JSONObject(s);
//                    //if no error in response
//                    if(obj.getString("code").equals("500")){
//                        txt_infonull.setVisibility(View.VISIBLE);
//                        listKlinik.setVisibility(View.GONE);
//
//                    }else if(obj.getString("code").equals("200")){
//                        listKlinik.setVisibility(View.VISIBLE);
//                        JSONArray jr = obj.getJSONArray("res");
//                        for (int a = 0; a < jr.length(); a++) {
//                            txt_infonull.setVisibility(View.GONE);
//                            JSONObject jso = jr.getJSONObject(a);
//                            np = jso.getString("nama_perusahaan");
//                            alamat = jso.getString("alamat");
//                            idk = jso.getString("id");
//                            urllogo = jso.getString("logo_yankes");
//                            ArrayKlinik.add(new Klinik(idk,np,alamat,urllogo));
//                        }
//                        Klinikadapter.notifyDataSetChanged();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        masukPakEko pl = new masukPakEko();
//        pl.execute();
//    }


}

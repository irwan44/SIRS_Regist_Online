package averin.sirs.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import averin.sirs.com.Adapter.AntrianDSBAdapter;
import averin.sirs.com.Adapter.JadwalDokterDSBAdapter;
import averin.sirs.com.Adapter.KlinikDekatAdapter;
import averin.sirs.com.Adapter.KlinikshortAdapter;
import averin.sirs.com.Adapter.RequestHandler;
import averin.sirs.com.Adapter.SpinnerAdapter;
import averin.sirs.com.Model.Antrian;
import averin.sirs.com.Model.DokterPoli;
import averin.sirs.com.Model.Klinik;
import averin.sirs.com.Model.Login;
import averin.sirs.com.Model.Token;
import averin.sirs.com.Model.isiSpinner;
import averin.sirs.com.Ui.AppController;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    String no_ktp, val_token, np, alamat, idk, urllogo, url_fotoPasien, spnidKota,
            kode_rs, nama_rs, alamat_rs, code_menu="1";
    ConnectivityManager conMgr;
    TextView txt_login, txt_namaPasien, txt_noktp, txt_lbl, txt_null_antrian, txt_see_klinikterdekat,
            txt_see_jadwaldokter, txt_namars,txt_alamatrs, txt_registrs;
    CircleImageView fotoPasien;
    ImageButton btn_notif;
    ImageView img_antrian, img_user, img_mrpasien, img_registrs, img_null_antrian, img_logors;
    CardView cv_null_antrian;
    RelativeLayout txt_null_klinikdekat;
    SharedPreferences sharedpreferences;
    RecyclerView list_Klinikterdekat, list_Klinik, list_Antrian, list_Dokter;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    byte[] byt_foto;

    //Dialog Confirm
    AlertDialog.Builder dial_builder;
    AlertDialog dial_login;
    LayoutInflater inflater;
    View dialogView;
    public boolean doubleTapParam = false;

    //Model Array
    private ArrayList<Klinik> ArrayKlinik = new ArrayList<>();
    private ArrayList<Klinik> ArrayKlinikDekat = new ArrayList<>();
    private ArrayList<Antrian> ArrayAntrian = new ArrayList<>();
    private ArrayList<DokterPoli> ArrayDokterPoli = new ArrayList<>();

    //Adapter Content
    private KlinikshortAdapter klinikshortadapter;
    private KlinikDekatAdapter klinikdekatadapter;
    private AntrianDSBAdapter antrianDSBadapt;
    private JadwalDokterDSBAdapter jadwaldokterAdapter;

    //ELEMENT FOR SPINNER
    Spinner spnProv, spnKota;
    SpinnerAdapter spnProvadapter, spnKotaadapter;
    ArrayList<isiSpinner> listProv = new ArrayList<>();
    ArrayList<isiSpinner> listKota = new ArrayList<>();

    static DialogInterface alertToken = null;
    ProgressDialog pDialog;
    byte[] logo;

    //URL GET DATA FROM DB
    String APIurl =RequestHandler.APIdev;
    public String situsPerawan    = APIurl+"/api/v1/get-data-klinik.php";
    public String linkbokep       = APIurl+"/api/v1/get-token.php";
    public String urlKota         = APIurl+"/api/v1/get-data-kota.php";
    public String urlAntrian      = APIurl+"/api/v1/get-jadwal-px.php";
    public String urlGetDokter    = APIurl+"/api/v1/get-dokter-klinik-homepage.php";
    public String urlKlinikDetail = APIurl+"/api/v1/get-klinik-detail.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(

                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        // Your code goes here
                        // In this code, we are just
                        // changing the text in the textbox

                        // This line is important as it explicitly
                        // refreshes only once
                        // If "true" it implicitly refreshes forever
                        swipeRefreshLayout.setRefreshing(false);
                        swipeRefreshLayout.setColorSchemeResources(R.color.birufigma, R.color.birufigma, R.color.birufigma);
                    }
                }
        );

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        Login login = AppController.getInstance(this).getPasien();
        Token token = AppController.getInstance(this).isiToken();
        no_ktp          = String.valueOf(login.getKTP_pasien());
        url_fotoPasien  = String.valueOf(login.getFoto_pasien());
        val_token       = String.valueOf(token.gettoken());
        cekToken();

        //Dialog ask login
        dial_builder = new AlertDialog.Builder(MainActivity.this,R.style.CustomAlertDialog);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ViewGroup viewGroup = findViewById(com.google.android.material.R.id.content);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_ask_login, viewGroup, false);
        Button btn_act_login = dialogView.findViewById(R.id.btn_login);
        Button btn_act_cancel = dialogView.findViewById(R.id.btn_cancel);
        dial_builder.setView(dialogView);
        dial_login = dial_builder.create();
        dial_login.setCancelable(false);
        btn_act_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                i.putExtra("email_px", "");
                i.putExtra("pass_px", "");
                i.putExtra("last_menu", code_menu);
                startActivity(i);
                dial_login.dismiss();

            }
        });
        btn_act_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dial_login.dismiss();
            }
        });

        txt_null_antrian        = findViewById(R.id.txt_null_antrian);
        img_null_antrian        = findViewById(R.id.img_null_antrian);
        cv_null_antrian         = findViewById(R.id.cv_null_antrian);
        img_antrian             = findViewById(R.id.img_antrian);
        img_user                = findViewById(R.id.img_user);
        img_registrs            = findViewById(R.id.img_registrs);
        txt_registrs            = findViewById(R.id.txt_registrs);

//        img_logors = findViewById(R.id.img_logo_rs);
        txt_namars = findViewById(R.id.txt_nama_rs);
        txt_alamatrs = findViewById(R.id.txt_alamat_rs);

//        txt_null_klinikdekat    = findViewById(R.id.txt_null_klinikdekat);
        img_mrpasien            = findViewById(R.id.img_mrpasien);
//        txt_see_klinikterdekat  = findViewById(R.id.see_KlinikTerdekat);
        txt_see_jadwaldokter    = findViewById(R.id.see_JadwalDokter);

        //CEK SESSION LOGIN
        txt_login       = findViewById(R.id.txt_login);
        txt_namaPasien  = findViewById(R.id.namaPasien);
        txt_noktp       = findViewById(R.id.noKTP);
        txt_lbl         = findViewById(R.id.lbl_namapasien);
        fotoPasien      = findViewById(R.id.tb_poto);
        btn_notif       = findViewById(R.id.btn_notif);

        if (!AppController.getInstance(this).isLoggedIn()) {
            txt_login.setVisibility(View.VISIBLE);
            btn_notif.setVisibility(View.GONE);
            txt_lbl.setVisibility(View.GONE);
//            txt_null_klinikdekat.setVisibility(View.VISIBLE);
            txt_namaPasien.setVisibility(View.GONE);
            fotoPasien.setVisibility(View.INVISIBLE);
            txt_noktp.setVisibility(View.GONE);

//            txt_null_klinikdekat.setOnClickListener(new View.OnClickListener(){
//                public void onClick(View v){
//                    if(dialogView.getParent() != null){
//                        ((ViewGroup)dialogView.getParent()).removeView(dialogView);
//                    }
//                    dial_login.show();
//                }
//            });

            img_antrian.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
//                    if(dialogView.getParent() != null){
//                        ((ViewGroup)dialogView.getParent()).removeView(dialogView);
//                    }
                    dial_login.show();
                }
            });
            img_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if(dialogView.getParent() != null){
//                        ((ViewGroup)dialogView.getParent()).removeView(dialogView);
//                    }
                    dial_login.show();
                }
            });
            img_mrpasien.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dial_login.show();
                }
            });
            img_registrs.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
//                    if(dialogView.getParent() != null){
//                        ((ViewGroup)dialogView.getParent()).removeView(dialogView);
//                    }
                    dial_login.show();
                }
            });
            txt_registrs.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
//                    if(dialogView.getParent() != null){
//                        ((ViewGroup)dialogView.getParent()).removeView(dialogView);
//                    }
                    dial_login.show();
                }
            });
        }else{
            txt_login.setVisibility(View.GONE);
            //SET DATA T0 TOOLBAR
            txt_noktp.setText(String.valueOf(login.getKTP_pasien()));
            txt_namaPasien.setText(String.valueOf(login.getNama_pasien()));
        }
        if(no_ktp.equals("3174586231698546")){
            fotoPasien.setImageResource(R.drawable.foto_bos);
        }else {
            if (url_fotoPasien.equals("null")) {
                fotoPasien.setImageResource(R.drawable.profile_img_empty);
            } else {
                Glide.with(MainActivity.this).load(url_fotoPasien).into(fotoPasien);
            }
        }

//        txt_see_klinikterdekat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent aw = new Intent(MainActivity.this, RegistbyProvinsi.class);
//                startActivityForResult(aw, 2);
//            }
//        });
        //SPINNER KOTA
//        spnKota = findViewById(R.id.s_kota);
//        spnKotaadapter = new SpinnerAdapter(MainActivity.this, listKota);
//        spnKota.setAdapter(spnKotaadapter);
//        spnKota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                // TODO Auto-generated method stub
//                ArrayKlinikDekat.clear();
//                spnidKota =listKota.get(position).getId();
//                viewKlinikDekat(spnidKota);
//                viewDokter(spnidKota);
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // TODO Auto-generated method stub
//            }
//        });

        //Declare RecyleView
        list_Antrian = findViewById(R.id.RV_listAntrian);
//        list_Klinik = findViewById(R.id.RV_listklinik);
//        list_Klinikterdekat = findViewById(R.id.RV_Klinikterdekat);
        list_Dokter = findViewById(R.id.RV_listDokter);
        LinearLayoutManager horizondokter = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
//        LinearLayoutManager horizonKlinikDekat = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);

        list_Antrian.setLayoutManager(new LinearLayoutManager(this));
//        list_Klinik.setLayoutManager(new LinearLayoutManager(this));
//        list_Klinikterdekat.setLayoutManager(horizonKlinikDekat);
        list_Dokter.setLayoutManager(horizondokter);

//        klinikshortadapter = new KlinikshortAdapter(this,ArrayKlinik, this);
//        list_Klinik.setAdapter(klinikshortadapter);
//        klinikdekatadapter = new KlinikDekatAdapter(this, ArrayKlinikDekat, this);
//        list_Klinikterdekat.setAdapter(klinikdekatadapter);
        antrianDSBadapt = new AntrianDSBAdapter(this, ArrayAntrian);
        list_Antrian.setAdapter(antrianDSBadapt);
        jadwaldokterAdapter = new JadwalDokterDSBAdapter(this, ArrayDokterPoli);
        list_Dokter.setAdapter(jadwaldokterAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            //do the things u wanted
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        cekToken();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void cekToken() {
        //first getting the values
        final String isiToken    = val_token;
        final String ktp         = no_ktp;
        final String APIurl      = RequestHandler.APIdev;

        //if everything is fine
        class cekToken extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("no_ktp", ktp);

                //returing the response
                return requestHandler.requestData(APIurl+"/api/v1/cek-data-px.php", "POST", "application/json; charset=utf-8", "X-Api-Token",
                        isiToken, "X-Px-Key", "", params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                progressBar = (ProgressBar) findViewById(R.id.progressBar);
//                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                progressBar.setVisibility(View.GONE);

                try {//converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (obj.getString("code").equals("500")) {
                        ambilToken();
                    } else {
//                        GetspnKota();
//                        viewKlinik();
                        viewAntrian();
                        viewDetail();
                        viewDokter();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        cekToken pl = new cekToken();
        pl.execute();
    }

    public void ambilToken() {
        //first getting the values
        final String KodeApi    = "MUSA";
        final String KeyApi     = "@@TTWYYW";
        final String KeyCode    = "602f07f23fc390cfd4461b268f95eddfbd4fc87d9b313b64a943801b5e4c5b12";
        final String APIurl     = RequestHandler.APIdev;

//        final String KodeApi = "privy";
//        final String KeyApi  = "@nqgsMKf";
//        final String KeyCode = "befa14f43777a6fbb55bc6dc939784202da3a4c0f43c172ac636978aec117bad";

        //if everything is fine
        class ambilToken extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("KodeApi", KodeApi);
                params.put("KeyApi", KeyApi);
                params.put("KeyCode", KeyCode);

                //returing the response
                return requestHandler.reqToken(APIurl+"/api/v1/get-token.php", params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                progressBar = (ProgressBar) findViewById(R.id.progressBar);
//                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                progressBar.setVisibility(View.GONE);

                try {//converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (obj.getString("code").equals("200")) {
                        //getting the user from the response
//                        JSONObject loginJson = obj.getJSONObject("response");

                        //creating a new user object
                        Token token = new Token(
                                obj.getString("token")
                        );

                        //storing the user in shared preferences
                        AppController.getInstance(getApplicationContext()).getToken(token);
                        val_token = String.valueOf(obj.getString("token"));
//                        GetspnKota();
//                        viewKlinik();
                        viewAntrian();
                        viewDetail();
                        viewDokter();

                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        ambilToken pl = new ambilToken();
        pl.execute();
    }

    private void viewKlinik() {
        //first getting the values
        final String iniToken   = val_token;

        //if everything is fine
        class masukPakEko extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("idKota", "");

                //returing the response
                return requestHandler.requestData(situsPerawan, "POST", "application/json; charset=utf-8", "X-Api-Token",
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
                    if (obj.getString("code").equals("500")) {
                        Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                    } else {
//
//                        tes.setText("ini buat tes kata");
                        JSONArray jr = obj.getJSONArray("res");
                        ArrayKlinik.clear();
                        if(jr.length() > 5) {
                            for (int a = 0; a < 5; a++) {
                                JSONObject jso = jr.getJSONObject(a);
                                np = jso.getString("nama_perusahaan");
                                alamat = jso.getString("alamat");
                                idk = jso.getString("id");
                                urllogo = jso.getString("logo_yankes");
                                ArrayKlinik.add(new Klinik(idk, np, alamat, urllogo));
                            }
                        }else{
                            for (int a = 0; a < jr.length(); a++) {
                                JSONObject jso = jr.getJSONObject(a);
                                np = jso.getString("nama_perusahaan");
                                alamat = jso.getString("alamat");
                                idk = jso.getString("id");
                                urllogo = jso.getString("logo_yankes");
                                ArrayKlinik.add(new Klinik(idk, np, alamat, urllogo));
                            }
                        }
//                        klinikshortadapter.notifyDataSetChanged();
//                        klinikdekatadapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        masukPakEko pl = new masukPakEko();
        pl.execute();
    }

    private void viewKlinikDekat(final String idKota) {
        //first getting the values
        final String iniToken   = val_token;
//        ArrayKlinikDekat.clear();

        //if everything is fine
        class masukPakEko extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("idKota", idKota);
//                params.put("User", user);
//                params.put("Pass", pass);

                //returing the response
                return requestHandler.requestData(situsPerawan, "POST", "application/json; charset=utf-8", "X-Api-Token",
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
                    if (obj.getString("code").equals("200")) {
                        JSONArray jr = obj.getJSONArray("res");
//                        txt_null_klinikdekat.setVisibility(View.GONE);
//                        list_Klinikterdekat.setVisibility(View.VISIBLE);

//                        if (jr.length() > 5) {
                        for (int a = 0; a < jr.length(); a++) {
                            JSONObject jso = jr.getJSONObject(a);
                            np = jso.getString("nama_perusahaan");
                            alamat = jso.getString("alamat");
                            idk = jso.getString("id");
                            urllogo = jso.getString("logo_yankes");
//                            ArrayKlinikDekat.add(new Klinik(idk, np, alamat, urllogo));
                        }
//                        klinikdekatadapter.notifyDataSetChanged();

                    } else if(obj.getString("code").equals("500")) {
//                        list_Klinikterdekat.setVisibility(View.GONE);
//                        txt_null_klinikdekat.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        masukPakEko pl = new masukPakEko();
        pl.execute();
    }

    private void viewAntrian() {
        //first getting the values
        final String iniToken   = val_token;
        final String ktpPasien  = no_ktp;
        final String tgl_cari   = df.format(new Date());

        //if everything is fine
        class masukPakEko extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("no_ktp", ktpPasien);
                params.put("tgl", tgl_cari);
//                params.put("Pass", pass);

                //returing the response
                return requestHandler.requestData(urlAntrian, "POST", "application/json; charset=utf-8", "X-Api-Token",
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

                    JSONArray jr = obj.getJSONArray("list");
                    ArrayAntrian.clear();
                    if (jr.length() == 0) {
                        txt_null_antrian.setVisibility(View.VISIBLE);
                        img_null_antrian.setVisibility(View.VISIBLE);
                        cv_null_antrian.setVisibility(View.VISIBLE);

                    } else {
                        txt_null_antrian.setVisibility(View.GONE);
                        img_null_antrian.setVisibility(View.GONE);
                        cv_null_antrian.setVisibility(View.GONE);

                        for (int a = 0; a < jr.length(); a++) {
                            JSONObject jso = jr.getJSONObject(a);
                            String idReg = jso.getString("idReg");
                            String noAntri = jso.getString("no_antrian");
                            String nmDokter = jso.getString("nama_dokter");
                            String tgl = jso.getString("tgl");
                            String jamAwal = jso.getString("jam_awal");
                            String jamAkhir = jso.getString("jam_akhir");
                            String status = jso.getString("status");
                            String nmKlinik = jso.getString("nama_klinik");
                            String nmBagian = jso.getString("nama_bagian");
                            ArrayAntrian.add(new Antrian(idReg,noAntri,nmDokter,tgl,jamAwal,jamAkhir,status,nmKlinik,nmBagian));
                        }
                        antrianDSBadapt.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        masukPakEko pl = new masukPakEko();
        pl.execute();
    }

    private void GetspnKota() {
        //first getting the values
        final String iniToken     = val_token;
        listKota.clear();

//        pDialog = new ProgressDialog(MainActivity.this);
//        pDialog.setCancelable(false);
//        pDialog.setMessage("Loading...");
//        pDialog.setIcon(R.drawable.ic_circle_blue);
//        showDialog();

        //if everything is fine
        class losdoll extends AsyncTask<Void, Void, String> {

            private HashMap params;
            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();
                //creating request parameters
                params = new HashMap<String, HashMap<String, String>>();
                params.put("idProv", "");

                //returing the response
                return requestHandler.requestSpinner(urlKota, "POST", "application/json; charset=utf-8", "X-Api-Token",
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
//                    JSONObject obj = new JSONObject(s);
                    JSONArray jso = new JSONArray(s);
                    for (int a = 0; a < jso.length(); a++) {
                        isiSpinner item = new isiSpinner();
                        JSONObject jr = jso.getJSONObject(a);

                        item.setId(jr.getString("idKota"));
                        item.setKet(jr.getString("nama"));
                        listKota.add(item);
                    }
                    spnKotaadapter.notifyDataSetChanged();
//                    hideDialog();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        losdoll pl = new losdoll();
        pl.execute();
    }

    private void viewDokter() {
        //first getting the values
        final String iniToken   = val_token;

        //if everything is fine
        class masukPakEko extends AsyncTask<Void, Void, String> {

            private HashMap params;
            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                params = new HashMap<String, HashMap<String, String>>();
                params.put("idKota", "42");
                //returing the response
                return requestHandler.requestData(urlGetDokter, "POST", "application/json; charset=utf-8", "X-Api-Token",
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
//                    if (obj.getString("code").equals("500")) {
//                        Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
//                    } else {

                    JSONArray jr = obj.getJSONArray("items");
                    ArrayDokterPoli.clear();
                    for (int a = 0; a < jr.length(); a++) {
                        JSONObject jso = jr.getJSONObject(a);

                        String nm_dokter    = jso.getString("nama_pegawai");
                        String kd_dokter    = jso.getString("kode_dokter");
                        String idnya_dokter = jso.getString("id");
                        String kd_bag       = jso.getString("kode_bagian");
                        String bag          = jso.getString("nama_bagian");
                        String jam_mulai    = jso.getString("jam_mulai");
                        String jam_akhir    = jso.getString("jam_akhir");
                        String wkt_periksa  = jso.getString("waktu_periksa");
                        String sFoto        = jso.getString("foto");
                        ArrayDokterPoli.add(new DokterPoli(kode_rs, nama_rs, nm_dokter, kd_dokter, idnya_dokter, kd_bag, bag, jam_mulai, jam_akhir, wkt_periksa, sFoto));
                    }
                    jadwaldokterAdapter.notifyDataSetChanged();
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        masukPakEko pl = new masukPakEko();
        pl.execute();
    }

    private void viewDetail() {
        //first getting the values
        final String iniToken   = val_token;
        final String id_klinik  = "NDcy";

        //if everything is fine
        class masukPakEko extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("id", id_klinik);

                //returing the response
                return requestHandler.requestData(urlKlinikDetail, "POST", "application/json; charset=utf-8", "X-Api-Token",
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
                        txt_namars.setText(" - ");
                        txt_alamatrs.setText(" - ");

                    } else if(jr.getString("code").equals("200")){
                        JSONArray res = jr.getJSONArray("res");
                        for(int a = 0; a < jr.length(); a++) {
                            JSONObject obj = res.getJSONObject(a);
                            kode_rs = obj.getString("kode_klinik");
                            nama_rs = obj.getString("nama_perusahaan");
                            alamat_rs = obj.getString("alamat");
                            String mailKlinik = obj.getString("email");
                            txt_namars.setText(nama_rs);
                            txt_alamatrs.setText(alamat_rs);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        masukPakEko pl = new masukPakEko();
        pl.execute();
    }

    public void openvcs(View v) {
        dial_login.show();
    }

    public void actLogin(View v) {
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        i.putExtra("last_menu", "1");
        startActivity(i);
    }

    public void testspin(View v) {
        Intent i = new Intent(MainActivity.this, ScanQRCode.class);
        startActivity(i);
    }

    public void InfoDMedis(View view) {
        Intent i = new Intent(MainActivity.this, InfoDmedis.class);
        startActivity(i);
    }
    public void RegistRS(View view) {
        Intent i = new Intent(MainActivity.this, RegistPoli.class);
        i.putExtra("kde_Klinik", kode_rs);
        i.putExtra("kde_dokter", "");
        i.putExtra("nma_Klinik", nama_rs);
        i.putExtra("nma_dokter", "");
        startActivity(i);
    }
    public void MRpasien(View view) {
        Intent i = new Intent(MainActivity.this, MRpasienActivity.class);
        startActivity(i);
    }
    public void Antrian(View view) {
        Intent i = new Intent(MainActivity.this, AntrianActivity.class);
        startActivity(i);
    }
    public void registProvinsi(View view) {
        Intent i = new Intent(MainActivity.this, RegistbyProvinsi.class);
        startActivity(i);
    }

    public void profilePasien(View v){
        Intent i = new Intent(MainActivity.this, EditPasienLama.class);
        startActivity(i);
    }
    public void profilePasien2(View v){
        Intent i = new Intent(MainActivity.this, RegistPasienLama.class);
        startActivity(i);
    }

//    private void showDialog() {
//        if (!pDialog.isShowing())
//            pDialog.show();
//    }

//    private void hideDialog() {
//        if (pDialog.isShowing())
//            pDialog.dismiss();
//    }

}
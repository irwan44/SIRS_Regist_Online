package averin.sirs.com;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import averin.sirs.com.Adapter.DokterPoliAdapter;
import averin.sirs.com.Adapter.RequestHandler;
import averin.sirs.com.Adapter.SpinnerAdapter;
import averin.sirs.com.Model.Berita;
import averin.sirs.com.Model.DokterPoli;
import averin.sirs.com.Model.Klinik;
import averin.sirs.com.Model.Login;
import averin.sirs.com.Model.Token;
import averin.sirs.com.Model.isiSpinner;
import averin.sirs.com.Ui.AppController;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegistPoli extends AppCompatActivity {

    String val_token, kd_bagian, spnKodeBagian, kd_klinik, nm_klinik, nm_Dokter, nm_dokter, kd_dokter, idnya_dokter, kd_bag, bag, sFoto,
            jam_mulai, jam_akhir, wkt_periksa, idk, no_ktp, code_menu="1";
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    RecyclerView DokterPoliCycleview;
    Dialog dialog_regist_poli;
    TextView txt_login, txt_namaPasien, txt_noktp, txt_lbl, txt_cariDokter;
    CircleImageView fotoPasien;
    RelativeLayout txt_null;
    ImageButton btn_notif;
    ImageButton imgbtn_home;
    ConnectivityManager conMgr;
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

    //ELEMENT FOR SPINNER
//    Spinner spnPoli;
//    SpinnerAdapter spnPoliadapter;
//    ProgressDialog pDialog;
//    ArrayList<isiSpinner> listPoli = new ArrayList<>();

    private ArrayList<DokterPoli> ArrayDokter = new ArrayList<>();
    private ArrayList<DokterPoli> listDokter;
    private DokterPoliAdapter DokterpoliAdapter;
    String APIurl = RequestHandler.APIdev;

    public String urlKlinikDetail = APIurl+"/api/v1/get-klinik-detail.php";
    public String urlGetDokter    = APIurl+"/api/v1/get-dokter-poli.php";
    public String urlSpinner      = APIurl+"/api/v1/get-poli.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_poli);

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
        //TOOLBAR
        Toolbar LabToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(LabToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LabToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //CEK CONNECTION
        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        txt_cariDokter = findViewById(R.id.txt_caridokter);
        txt_null    = findViewById(R.id.info_null);
        imgbtn_home = findViewById(R.id.imgbtn_home);

        imgbtn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegistPoli.this, MainActivity.class);
                startActivity(i);
            }
        });

        //Dialog Alert
        dialog = new AlertDialog.Builder(RegistPoli.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_ask_login, null);
        dialog.setView(dialogView);

        //GET DATA FROM CONTROLLER
        Login login = AppController.getInstance(this).getPasien();
        Token token = AppController.getInstance(this).isiToken();
        val_token = (String.valueOf(token.gettoken()));
        Bundle kiriman = getIntent().getExtras();
        if (kiriman != null) {
            kd_klinik   = kiriman.get("kde_Klinik").toString();
            kd_dokter   = kiriman.get("kde_dokter").toString();
            nm_klinik   = kiriman.get("nma_Klinik").toString();
            nm_Dokter = kiriman.get("nma_dokter").toString();
            txt_cariDokter.setText(nm_Dokter);
//            GetSpnPoli(kd_klinik);
            viewDokter("",kd_dokter);
        }
        if(txt_cariDokter.equals("")){
            txt_cariDokter.setText("Cari dokter");
            cekToken();
        }

        //CEK SESSION LOGIN
//        txt_login       = findViewById(R.id.txt_login);
//        txt_namaPasien  = findViewById(R.id.namaPasien);
//        txt_noktp       = findViewById(R.id.noKTP);
//        txt_lbl         = findViewById(R.id.lbl_namapasien);
//        fotoPasien      = findViewById(R.id.tb_poto);
//        btn_notif       = findViewById(R.id.btn_notif);
        if (!AppController.getInstance(this).isLoggedIn()) {
//            txt_login.setVisibility(View.VISIBLE);
//            btn_notif.setVisibility(View.GONE);
            txt_lbl.setVisibility(View.GONE);
            txt_namaPasien.setVisibility(View.GONE);
            fotoPasien.setVisibility(View.GONE);
            txt_noktp.setVisibility(View.GONE);
        }else{
//            txt_login.setVisibility(View.GONE);
            //SET DATA T0 TOOLBAR
//            txt_noktp.setText(String.valueOf(login.getKTP_pasien()));
//            txt_namaPasien.setText(String.valueOf(login.getNama_pasien()));
        }
//        no_ktp = txt_noktp.getText().toString();

        //RECYLEVIEW DOKTER POLI
//        viewDetail();
        DokterpoliAdapter = new DokterPoliAdapter(ArrayDokter);
        DokterPoliCycleview = findViewById(R.id.DokterPoli_viewCycle);
        DokterPoliCycleview.setLayoutManager(new LinearLayoutManager(this));
        DokterPoliCycleview.setAdapter(DokterpoliAdapter);

        //SPINNER
//        spnPoli = findViewById(R.id.spnPoli);
//        spnPoliadapter = new SpinnerAdapter(RegistPoli.this, listPoli);
//        spnPoli.setAdapter(spnPoliadapter);
//        spnPoli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                // TODO Auto-generated method stub
//                spnKodeBagian =listPoli.get(position).getId();
//                ArrayDokter.isEmpty();
//                viewDokter(spnKodeBagian, "");
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // TODO Auto-generated method stub
//            }
//        });

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
//                        GetSpnPoli(kd_klinik);
                        viewDokter("","");
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
//                                loginJson.getString("nama_grup")
                                obj.getString("token")
//                                obj.getString("pengguna")
                        );

                        //storing the user in shared preferences
                        AppController.getInstance(getApplicationContext()).getToken(token);
//                        GetSpnPoli(kd_klinik);
                        viewDokter("","");

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

    private void viewDokter(final String filter_kdBagian, final String kde_Dokter) {
        //first getting the values
        final String kode_klinik  = kd_klinik;
        final String iniToken   = val_token;
        final String order      = "asc";
        final String offset     = "0";
        final String limit      = "100";
        ArrayDokter.clear();

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
                HashMap<String, String> val = new HashMap<String, String>();
                val.put("filter", filter_kdBagian);
                val.put("kdeDokter", kde_Dokter);
                val.put("order", order);
                val.put(offset, offset);
                val.put("limit", limit);
                params.put("kode_klinik", kode_klinik);
                params.put("src", val);
                //returing the response
                return requestHandler.requestData(urlGetDokter, "POST", "application/json; charset=utf-8", "X-Api-Token",
                        iniToken, "X-Px-Key", "", params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar.setVisibility(View.GONE);

                try {//converting response to json object
                    JSONObject obj = new JSONObject(s);
                    //if no error in response
                    if (obj.getString("count").equals("0")) {
                        txt_null.setVisibility(View.VISIBLE);
                    } else {
                        JSONArray jr = obj.getJSONArray("items");
                        for (int a = 0; a < jr.length(); a++) {
                            txt_null.setVisibility(View.GONE);
                            JSONObject jso = jr.getJSONObject(a);
                            nm_dokter    = jso.getString("nama_pegawai");
                            kd_dokter    = jso.getString("kode_dokter");
                            idnya_dokter = jso.getString("id");
                            kd_bag       = jso.getString("kode_bagian");
                            bag          = jso.getString("nama_bagian");
                            jam_mulai    = jso.getString("jam_mulai");
                            jam_akhir    = jso.getString("jam_akhir");
                            wkt_periksa  = jso.getString("waktu_periksa");
                            sFoto        = jso.getString("foto");

                            ArrayDokter.add(new DokterPoli(kd_klinik, nm_klinik, nm_dokter,
                                    kd_dokter, idnya_dokter, kd_bag, bag, jam_mulai, jam_akhir,
                                    wkt_periksa, sFoto));
                        }
                        DokterpoliAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        masukPakEko pl = new masukPakEko();
        pl.execute();
    }

    private void GetSpnPoli(final String kodeKlinik) {
        //first getting the values
        final String iniToken     = val_token;
//        listPoli.clear();

//        pDialog = new ProgressDialog(RegistPoli.this);
//        pDialog.setCancelable(false);
//        pDialog.setMessage("Loading...");
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
                params.put("kode_klinik", kodeKlinik);

                //returing the response
                return requestHandler.requestData(urlSpinner, "POST", "application/json; charset=utf-8", "X-Api-Token",
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
                    JSONArray jso = obj.getJSONArray("list");

                    for (int a = 0; a < jso.length(); a++) {
                        JSONObject jr = jso.getJSONObject(a);
                        isiSpinner item = new isiSpinner();
                        item.setId(jr.getString("kode_bagian"));
                        item.setKet(jr.getString("nama_bagian"));

//                        listPoli.add(item);
                    }
//                    spnPoliadapter.notifyDataSetChanged();
//                    hideDialog();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        losdoll pl = new losdoll();
        pl.execute();
    }

    public void openvcs(View v) {
        dialog.setCancelable(true);
        dialog.show();
    }

    public void actLogin(View v) {
        Intent i = new Intent(RegistPoli.this, LoginActivity.class);
        i.putExtra("last_menu", "3");
        startActivity(i);
    }

    public void cariDokter(View view) {
        Intent i = new Intent(RegistPoli.this, listcariDokter.class);
        i.putExtra("kodeKlinik", kd_klinik);
        i.putExtra("namaKlinik", nm_klinik);
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
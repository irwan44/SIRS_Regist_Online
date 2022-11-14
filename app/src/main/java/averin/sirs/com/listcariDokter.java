package averin.sirs.com;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import averin.sirs.com.Adapter.CariDokterAdapter;
import averin.sirs.com.Adapter.RequestHandler;
import averin.sirs.com.Model.CariDokter;
import averin.sirs.com.Model.Token;
import averin.sirs.com.Ui.AppController;

public class listcariDokter extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ProgressDialog pDialog;
    List<CariDokter> caridokter = new ArrayList<CariDokter>();
    CariDokterAdapter adapter;
    SwipeRefreshLayout swipe;
    ConnectivityManager conMgr;
    ListView list_view;
    androidx.appcompat.widget.SearchView search;
    String val_token, kodeKlinik, namaKlinik, idDokter, kdDokter, namaDokter;
    String APIurl = RequestHandler.APIdev;
    public String urlCariDokter = APIurl+"/api/v1/get-dokter-klinik.php";
    public String urlAllDokter = APIurl+"/api/v1/get-all-dokter-klinik.php";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dokter);
        swipe       = findViewById(R.id.swipe_refresh);
        list_view   = findViewById(R.id.lv_dokterPoli);
        search      = findViewById(R.id.searchDokter);
        adapter     = new CariDokterAdapter(listcariDokter.this, caridokter);
        list_view.setAdapter(adapter);
        swipe.setOnRefreshListener(this);
        search.setQuery("", true);
        search.setFocusable(true);
        search.setIconified(false);
        search.requestFocusFromTouch();

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

        Bundle data = getIntent().getExtras();
        if(data!=null){
            kodeKlinik = data.get("kodeKlinik").toString();
            namaKlinik = data.get("namaKlinik").toString();
        }

        Token tkn = AppController.getInstance(this).isiToken();
        val_token = String.valueOf(tkn.gettoken());

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(listcariDokter.this, RegistPoli.class);
                idDokter = caridokter.get(position).getIdDokter();
                kdDokter = caridokter.get(position).getkodeDokter();
                namaDokter = caridokter.get(position).getnamaDokter();
                i.putExtra("kde_Klinik",kodeKlinik);
                i.putExtra("kde_dokter", kdDokter);
                i.putExtra("nma_Klinik",namaKlinik);
                i.putExtra("nma_dokter", namaDokter);
                startActivity(i);
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String keyword) {
                cariData(keyword);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String keyword) {
                cariData(keyword);
                return false;
            }
        });

        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
                ListData();
            }
        });
    }

    private void cariData(String keyword) {
        //first getting the values
        final String filter    = keyword;
        final String iniToken  = val_token;
        final String kdeKlinik = kodeKlinik;

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
                val.put("filter", keyword);
                params.put("kode_klinik", kdeKlinik);
                params.put("src", val);
                //returing the response
                return requestHandler.requestData(urlCariDokter, "POST", "application/json; charset=utf-8", "X-Api-Token",
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
                        String pesan = obj.getString("msg");
                        caridokter.add(new CariDokter("","",pesan));
                    } else {
                        caridokter.clear();
                        adapter.notifyDataSetChanged();
                        JSONArray jr = obj.getJSONArray("res");
                        for (int a = 0; a < jr.length(); a++) {
                            JSONObject jso = jr.getJSONObject(a);
                            idDokter    = jso.getString("id_dokter");
                            kdDokter    = jso.getString("kode_dokter");
                            namaDokter  = jso.getString("nama_dokter");

                            caridokter.add(new CariDokter(idDokter,kdDokter,namaDokter));
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

    private void ListData() {
        //first getting the values
        final String iniToken  = val_token;
        final String kdeKlinik = kodeKlinik;

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
                params.put("kode_klinik", kdeKlinik);
                //returing the response
                return requestHandler.requestData(urlAllDokter, "POST", "application/json; charset=utf-8", "X-Api-Token",
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
                        String pesan = obj.getString("msg");
                        caridokter.add(new CariDokter("","",pesan));
                    } else {
                        caridokter.clear();
                        adapter.notifyDataSetChanged();
                        swipe.setRefreshing(true);
                        JSONArray jr = obj.getJSONArray("res");
                        for (int a = 0; a < jr.length(); a++) {
                            JSONObject jso = jr.getJSONObject(a);
                            idDokter    = jso.getString("id_dokter");
                            kdDokter    = jso.getString("kode_dokter");
                            namaDokter  = jso.getString("nama_dokter");
                            caridokter.add(new CariDokter(idDokter,kdDokter,namaDokter));
                        }
                    }
                    adapter.notifyDataSetChanged();
                    swipe.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        masukPakEko pl = new masukPakEko();
        pl.execute();
    }

    @Override
    public void onRefresh() {
        ListData();
    }
}

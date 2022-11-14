package averin.sirs.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraManager;
import android.os.AsyncTask;
import android.os.Bundle;

import averin.sirs.com.Adapter.RequestHandler;
import averin.sirs.com.Model.Login;
import averin.sirs.com.Model.Token;
import averin.sirs.com.Ui.AppController;

//LIBRARY FOR SCAN QR CODE
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanQRCode extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private CameraManager cm_flash;
    private String getCameraID, hsl_qrcode;
    private ToggleButton flashOnOff;

    ProgressDialog pDialog;
    String val_token, no_ktp, regId, tgl_antri, jam_awal, jam_akhir, status_antri, nm_klinik, nm_bag, jam_konvert;
    Dialog dialog_confirm;
    TextView txt_info;
    Button btn_oke;

    //URL GET DATA FROM DB
    String APIurl =RequestHandler.APIdev;
    public String situsPerawan    = APIurl+"/api/v1/get-data-klinik.php";
    public String linkbokep       = APIurl+"/api/v1/get-token.php";
    public String prosesScan      = APIurl+"/api/v1/get-scan_klinik.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);

        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
//        flashOnOff = findViewById(R.id.tgl_senter);
//        cm_flash = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA},
                PackageManager.PERMISSION_GRANTED);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            regId        = extras.get("regId").toString();
            tgl_antri    = extras.get("tgl_antri").toString();
            jam_awal     = extras.get("jam_awal").toString();
            jam_akhir    = extras.get("jam_akhir").toString();
            status_antri = extras.get("status_antri").toString();
            nm_klinik    = extras.get("nm_klinik").toString();
            nm_bag       = extras.get("nm_bag").toString();
        }

        //Dialog Confirm
        dialog_confirm = new Dialog(ScanQRCode.this);
        dialog_confirm.setContentView(R.layout.dialog_success_regist);
        dialog_confirm.setCancelable(false);
        txt_info = dialog_confirm.findViewById(R.id.txt_info_success);
        btn_oke = dialog_confirm.findViewById(R.id.btn_oke);

        btn_oke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_confirm.dismiss();
                Intent i = new Intent(ScanQRCode.this, AntrianDetail.class);
                i.putExtra("regID", regId);
                i.putExtra("tgl_antri", tgl_antri);
                i.putExtra("jam_awal", jam_awal);
                i.putExtra("jam_akhir", jam_akhir);
                i.putExtra("status_antri", status_antri);
                i.putExtra("nm_klinik", nm_klinik);
                i.putExtra("nm_bag", nm_bag);
                startActivity(i);
            }
        });

//        spinner_test = (Spinner) findViewById(R.id.test_spin);
        Token token = AppController.getInstance(this).isiToken();
        Login lg    = AppController.getInstance(this).getPasien();
        val_token = String.valueOf(token.gettoken());
        no_ktp    = String.valueOf(lg.getKTP_pasien());
        // Exception is handled, because to check whether
        // the camera resource is being used by another
        // service or not.
//        try {
//            // O means back camera unit,
//            // 1 means front camera unit
//            getCameraID = cm_flash.getCameraIdList()[0];
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


//    FUNGSI SCANNER QR CODE
    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.v("TAG", rawResult.getText()); // Prints scan results
        Log.v("TAG", rawResult.getBarcodeFormat().toString());
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Scan Result");
//        builder.setMessage(rawResult.getText());
//        AlertDialog alert1 = builder.create();
//        alert1.show();
        hsl_qrcode = rawResult.getText();
        dialog_confirm.show();
        txt_info.setText(hsl_qrcode);
        mScannerView.resumeCameraPreview(this);
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
                return requestHandler.requestData(APIurl+"/api/v1/get-data-px.php", "POST", "application/json; charset=utf-8", "X-Api-Token",
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
                        GetAntriPoli();
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
                        GetAntriPoli();

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

    private void GetAntriPoli() {
        //first getting the values
        final String iniToken   = val_token;
        final String kdKlinik = hsl_qrcode;
        final String noKTP = no_ktp;
        final String IDreg = regId;

        //if everything is fine
        class masukPakEko extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("kode_klinik", kdKlinik);
//                params.put("no_ktp", noKTP);
                params.put("regID", IDreg);

                //returing the response
                return requestHandler.requestData(prosesScan, "POST", "application/json; charset=utf-8", "X-Api-Token",
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
                        dialog_confirm.show();
                        txt_info.setText(obj.getString("msg"));
                    } else if(obj.getString("code").equals("300")) {
                        dialog_confirm.show();
                        txt_info.setText(obj.getString("msg"));
                    }else if(obj.getString("code").equals("500")) {
                        dialog_confirm.show();
                        txt_info.setText(obj.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        masukPakEko pl = new masukPakEko();
        pl.execute();
    }

    public void BalikAntrian(View v){
        Intent i = new Intent(ScanQRCode.this, AntrianDetail.class);
        i.putExtra("regID", regId);
        i.putExtra("tgl_antri", tgl_antri);
        i.putExtra("jam_awal", jam_awal);
        i.putExtra("jam_akhir", jam_akhir);
        i.putExtra("status_antri", status_antri);
        i.putExtra("nm_klinik", nm_klinik);
        i.putExtra("nm_bag", nm_bag);
        startActivity(i);

    }
}
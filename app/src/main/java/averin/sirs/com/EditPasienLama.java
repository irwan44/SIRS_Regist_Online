package averin.sirs.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fenchtose.nocropper.CropMatrix;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import averin.sirs.com.Adapter.RequestHandler;
import averin.sirs.com.Model.Login;
import averin.sirs.com.Model.Token;
import averin.sirs.com.Ui.AppController;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditPasienLama extends AppCompatActivity {

    String val_token, no_ktp, url_fotoPasien, nama, usia, gol_darah, alergi, alamat, gender, tgl_lahir,
            tmp_lahir, tgl_konvert, strTimeStamp, strImageName, strFilePath, strEncodedImage;
    EditText et_nama, et_usia, et_goldarah, et_alergi, et_alamat, et_tglLahir, et_tmptLahir, et_ktpPasien;
    TextView txt_info_success, txt_info_failed;
    Button btnSave, btnEditFoto, btn_ok_failed, btn_ok_success;
    ExtendedFloatingActionButton fabEdit;
//    ImageView img_fotoPasien;
    DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //BOTTOM SHEET EDIT FOTO
    CircleImageView img_fotoPasien, cb_editFoto;
    BottomSheetBehavior sheetBehavior;
    BottomSheetDialog sheetDialog;
    View  sheetView;

    SimpleDateFormat df = new SimpleDateFormat("YYY-MM-dd");
    ProgressDialog pDialog;
    ConnectivityManager conMgr;
    private Calendar mCalendar;
    private String currentFilePath = null;

    //FUNGSI OPEN CAMERA & SAVE IMAGE
    int REQ_CAMERA = 101;
    private byte[] byt_foto;
    byte[] imageBytes;
    File fileDirectoty, imageFilename;
    private HashMap<String, CropMatrix> matrixMap = new HashMap<>();

    //FUNGSI OPEN GALERY & SAVE IMAGE
    private static final int REQUEST_CODE_READ_PERMISSION = 22;
    private static final int pic_id = 123;
    public static final int REQUEST_PICK_PHOTO = 1;
    private static final int REQUEST_GALLERY = 21;

    //Spinner Element
    String[] gndr = new String[] {"Laki-laki", "Perempuan"};
    AutoCompleteTextView actGender;

    //Dialog Confirm
    AlertDialog.Builder builder_success, builder_failed, dial_builder;
    AlertDialog dial_success, dial_failed, dial_Foto;
    LayoutInflater inflater;
    View v_success_regist, v_failed_regist, v_delete_foto;

    //URL JSON
    String APIurl = RequestHandler.APIdev;
    public String url_getPasien = APIurl+"/api/v1/get-data-pasien.php";
    public String url_editPasien = APIurl+"/api/v1/edit_pasien_lama.php";

    //  =========================================== Start Content =================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pasien_lama);

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

        //GET DATA FROM CONTROLLER
        Login login = AppController.getInstance(this).getPasien();
        Token token = AppController.getInstance(this).isiToken();
        val_token = (String.valueOf(token.gettoken()));
        no_ktp = (String.valueOf(login.getKTP_pasien()));

        sheetView = findViewById(R.id.bottom_ubah_foto);
        sheetBehavior = BottomSheetBehavior.from(sheetView);
        cb_editFoto = findViewById(R.id.btn_openEditFoto);
        img_fotoPasien  = findViewById(R.id.fotoPasien);
        et_nama         = findViewById(R.id.et_namapasien);
        et_usia         = findViewById(R.id.et_usia);
        et_goldarah     = findViewById(R.id.et_goldarah);
        actGender      = findViewById(R.id.act_gender);
        et_alergi       = findViewById(R.id.et_alergi);
        et_alamat       = findViewById(R.id.et_alamat);
        et_tglLahir     = findViewById(R.id.et_tglLahir);
        et_tmptLahir    = findViewById(R.id.et_tmptLahir);
        et_ktpPasien    = findViewById(R.id.et_ktpPasien);
        fabEdit         = findViewById(R.id.fabEdit);
        getPasien();

        //Dialog Delete Foto
        dial_builder = new AlertDialog.Builder(EditPasienLama.this,R.style.CustomAlertDialog);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ViewGroup vg = findViewById(com.google.android.material.R.id.content);
        inflater = getLayoutInflater();
        v_delete_foto = inflater.inflate(R.layout.dialog_daftar_poli, vg, false);
        Button btn_act_yes = v_delete_foto.findViewById(R.id.btn_ya);
        Button btn_act_no = v_delete_foto.findViewById(R.id.btn_tidak);
        TextView txtAtas = v_delete_foto.findViewById(R.id.txt1);
        TextView txtBawah = v_delete_foto.findViewById(R.id.txt2);
        txtAtas.setText("Apakah anda yakin ingin menghapus foto profile anda ?");
        txtBawah.setVisibility(View.GONE);
        dial_builder.setView(v_delete_foto);
        dial_Foto = dial_builder.create();
        dial_Foto.setCancelable(false);
        btn_act_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                hapusFotoProfile();

            }
        });
        btn_act_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dial_Foto.dismiss();
            }
        });

        cb_editFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowEditFoto();
//                Intent i = new Intent(EditPasienLama.this, CropImage.class);
//                startActivity(i);
//                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(camera_intent, pic_id);
            }
        });


        et_tglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCalendar = Calendar.getInstance();
                new DatePickerDialog(EditPasienLama.this, R.style.DialogTheme, datestart, mCalendar.get(Calendar.YEAR),
                        mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEditProfile();
            }
        });

        //Dialog Confirm
        builder_success = new AlertDialog.Builder(EditPasienLama.this,R.style.CustomAlertDialog);
        builder_failed = new AlertDialog.Builder(EditPasienLama.this,R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        inflater = getLayoutInflater();
        v_success_regist = inflater.inflate(R.layout.dialog_success_regist, viewGroup, false);
        v_failed_regist = inflater.inflate(R.layout.dialog_failed_regist, viewGroup, false);
        btn_ok_success = v_success_regist.findViewById(R.id.btn_oke);
        btn_ok_failed = v_failed_regist.findViewById(R.id.btn_oke_failed);
        txt_info_success = v_success_regist.findViewById(R.id.txt_info_success);
        txt_info_failed = v_failed_regist.findViewById(R.id.txt_info_failed);
        builder_success.setView(v_success_regist);
        builder_failed.setView(v_failed_regist);
        dial_success = builder_success.create();
        dial_success.setCancelable(false);
        dial_failed = builder_failed.create();
        dial_failed.setCancelable(false);
        btn_ok_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPasien();
                dial_success.dismiss();

            }
        });
        btn_ok_failed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dial_failed.dismiss();
            }
        });

        ArrayAdapter<String> spnAdapter = new ArrayAdapter<>(this, R.layout.dialog_spinner, gndr);
        actGender.setAdapter(spnAdapter);
    }

    private void getPasien() {
        //first getting the values
        final String iniToken   = val_token;
        final String iniKTP  = no_ktp;

        //if everything is fine
        class masukPakEko extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("no_ktp", iniKTP);

                //returing the response
                return requestHandler.requestData(url_getPasien, "POST", "application/json; charset=utf-8", "X-Api-Token",
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
                    JSONObject jb = new JSONObject(s);
                    //if no error in response
                    if (jb.getString("code").equals("200")) {
                        JSONArray jr = jb.getJSONArray("res");
                        for (int a = 0; a < jr.length(); a++) {
                            JSONObject obj = jr.getJSONObject(a);
                            nama = obj.getString("nama_pasien");
                            usia = obj.getString("umur");
                            alergi = obj.getString("alergi");
                            gol_darah = obj.getString("gol_darah");
                            gender = obj.getString("gender");
                            alamat = obj.getString("alamat");
                            tgl_lahir = obj.getString("tgl_lhr");
                            tmp_lahir = obj.getString("tmp_lhr");
                            url_fotoPasien = obj.getString("foto_pasien");
                            et_ktpPasien.setText(no_ktp);
                            et_nama.setText(nama);
                            byt_foto = url_fotoPasien.getBytes(StandardCharsets.UTF_8);

                            if(no_ktp.equals("3174586231698546")) {
                                img_fotoPasien.setImageResource(R.drawable.foto_bos);
                            }else {
                                if (url_fotoPasien.equals("null")) {
                                    img_fotoPasien.setImageResource(R.drawable.profile_img_empty);
                                } else {
                                    Glide.with(EditPasienLama.this).load(url_fotoPasien).into(img_fotoPasien);
                                }
                            }

                            if(usia.equals("null")){
                                et_usia.setText("-");
                            }else {
                                et_usia.setText(usia);
                            }
                            if(gender.equals("null") || gender.equals("")){
                                actGender.setHint("Jenis Kelamin");
                            }else if(gender.equals("L")){
                                actGender.setHint("Laki-laki");
                            }else if(gender.equals("P")){
                                actGender.setHint("Perempuan");
                            }
                            if(alergi.equals("null")){
                                et_alergi.setText("-");
                            }else {
                                et_alergi.setText(alergi);
                            }
                            if(alamat.equals("null")){
                                et_alamat.setText("-");
                            }else {
                                et_alamat.setText(alamat);
                            }
                            if(gol_darah.equals("null")){
                                et_goldarah.setText("-");
                            }else {
                                et_goldarah.setText(gol_darah);
                            }
                            if(tgl_lahir.equals("null") || tgl_lahir.equals("")){
                                et_tglLahir.setText("-");
                            }else {
                                Date tgl = null;
                                try {
                                    tgl = inputFormat.parse(tgl_lahir);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                tgl_konvert = outputFormat.format(tgl);
                                et_tglLahir.setText(tgl_konvert);
                            }
                            if(tmp_lahir.equals("null")){
                                et_tmptLahir.setText("-");
                            }else {
                                et_tmptLahir.setText(tmp_lahir);
                            }
                        }
                    }else if(jb.getString("code").equals("500")){
                        dial_failed.show();
                        img_fotoPasien.setImageResource(R.drawable.profile_img_empty);
                        actGender.setHint("Jenis Kelamin");
                        et_nama.setText("Data not found");
                        et_usia.setText("Data not found");
                        et_alergi.setText("Data not found");
                        et_alamat.setText("Data not found");
                        et_goldarah.setText("Data not found");
                        et_tglLahir.setText("Data not found");
                        et_tmptLahir.setText("Data not found");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        masukPakEko pl = new masukPakEko();
        pl.execute();
    }

    private void ShowEditFoto(){
        View v = getLayoutInflater().inflate(R.layout.swipe_ganti_foto, null);
        if(sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        (v.findViewById(R.id.cv_hapusFoto)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dial_Foto.show();
            }
        });

       (v.findViewById(R.id.cv_open_cam)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ci = new Intent(EditPasienLama.this, CropImage.class);
                ci.putExtra("pick_kode", 1);
                ci.putExtra("urlFoto", "");
                startActivity(ci);
            }
        });

        (v.findViewById(R.id.cv_open_galery)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ci = new Intent(EditPasienLama.this, CropImage.class);
                ci.putExtra("pick_kode", 2);
                ci.putExtra("urlFoto", "");
                startActivity(ci);
            }
        });

        sheetDialog = new BottomSheetDialog(this);
        sheetDialog.setContentView(v);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            sheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        sheetDialog.show();
        sheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                sheetDialog = null;
            }
        });
    }

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {
            String absPath = BitmapUtil.getFilePathFromUri(this, data.getData());
            Intent ci = new Intent(EditPasienLama.this, CropImage.class);
            ci.putExtra("pick_kode", 2);
            ci.putExtra("urlFoto", absPath);
            startActivity(ci);
        }
    }*/

    private void convertImage(String imageFilePath) {
        File imageFile = new File(imageFilePath);
        if (imageFile.exists()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            final Bitmap bitmap = BitmapFactory.decodeFile(strFilePath, options);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            Glide.with(this)
                    .load(bitmap)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.profile_img_empty)
                    .into(img_fotoPasien);
            imageBytes = baos.toByteArray();
            strEncodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        }
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (on) {
            layoutParams.flags |= bits;
        } else {
            layoutParams.flags &= ~bits;
        }
        window.setAttributes(layoutParams);
    }

    private boolean hasGalleryPermission() {
        return ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void askForGalleryPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_CODE_READ_PERMISSION);
    }

    private final DatePickerDialog.OnDateSetListener datestart = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, monthOfYear);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            et_tglLahir.setText(df.format(mCalendar.getTime()));
        }
    };

    private void sendEditProfile() {

        final String isiToken       = val_token;
        final String iniKTP         = no_ktp;
        final String namaPasien     = et_nama.getText().toString();
        final String usiaPasien     = et_usia.getText().toString();
        final String golDarah       = et_goldarah.getText().toString();
        final String alergiPasien   = et_alergi.getText().toString();
        final String alamatPasien   = et_alamat.getText().toString();
        final String jekel          = actGender.getText().toString();
        final String tglLahir       = et_tglLahir.getText().toString();
        final String tmpLahir       = et_tmptLahir.getText().toString();

        //if everything is fine
        class masukPakEko extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("no_ktp", iniKTP);
//                params.put("namaPasien", namaPasien);
                params.put("umurPasien", usiaPasien);
                params.put("goldarah", golDarah);
                params.put("tanggal_lhr", tglLahir);
                params.put("tempat_lhr", tmpLahir);
                params.put("gender", jekel);
                params.put("alergi", alergiPasien);
                params.put("alamat", alamatPasien);

                //returing the response
                return requestHandler.requestData(url_editPasien, "POST", "application/json; charset=utf-8", "X-Api-Token",
                        isiToken, "X-Px-Key", "", params);
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
                        dial_success.show();
                        txt_info_success.setText(obj.getString("msg"));
                        getPasien();
                    } else {
                        dial_failed.show();
                        txt_info_failed.setText(obj.getString("msg"));
                        getPasien();
//                        hideDialog();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        masukPakEko pl = new masukPakEko();
        pl.execute();
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
package averin.sirs.com.Adapter;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import averin.sirs.com.DaftarPoli;
import averin.sirs.com.MainActivity;
import averin.sirs.com.Model.DokterPoli;
import averin.sirs.com.R;
import android.util.Base64;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class JadwalDokterDSBAdapter extends RecyclerView.Adapter<JadwalDokterDSBAdapter.JadwalDokterViewHolder> {
    private ArrayList<DokterPoli> list;
    public Dialog dialog_regist_poli;
    public Button btnBatal, btnKirim;
    private String strFoto;
    private byte[] byt_Foto;

    public JadwalDokterDSBAdapter(MainActivity ma, ArrayList<DokterPoli> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public JadwalDokterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_jadwal_dokter, parent, false);
        return new JadwalDokterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JadwalDokterViewHolder holder, int position) {
        holder.txt_namaDokter.setText(list.get(position).getNama_dokter());
        holder.txt_JenisPoli.setText(list.get(position).getBagian());
        holder.txt_kddokter.setText(list.get(position).getKode_dokter());
        holder.txt_idnyadokter.setText(list.get(position).getIdnya_dokter());
        holder.txt_kdbag.setText(list.get(position).getKode_bag());
        holder.txt_kdklinik.setText(list.get(position).getKd_klinik());
        holder.txt_nmklinik.setText(list.get(position).getNm_klinik());
        holder.txt_wktperiksa.setText(list.get(position).getWaktu_periksa());
        holder.txt_tglPeriksa.setText(list.get(position).getJam_mulai() +" WIB");

        //DECODE FOTO DOKTER
        holder.txt_sFoto.setText(list.get(position).getFoto_dokter());
        strFoto = holder.txt_sFoto.getText().toString();
        if (strFoto.equals("")) {
            holder.img_Dokter.setImageResource(R.drawable.ic__01_boy);
        } else {
            byt_Foto = Base64.decode(strFoto, Base64.DEFAULT);
            Bitmap bitmapDokter = BitmapFactory.decodeByteArray(byt_Foto, 0, byt_Foto.length);
            holder.img_Dokter.setImageBitmap(bitmapDokter);
        }
    }

    public class JadwalDokterViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_kdbag, txt_kdklinik, txt_kddokter, txt_idnyadokter, txt_namaDokter,
                txt_JenisPoli, txt_nmklinik, txt_wktperiksa,
                txt_tglPeriksa, txt_sFoto;
        public ImageView img_Dokter;

        public JadwalDokterViewHolder(View itemView) {
            super(itemView);

            txt_namaDokter = itemView.findViewById(R.id.txt_nmDokter);
            txt_JenisPoli = itemView.findViewById(R.id.txt_nmPoli);
            txt_kddokter = itemView.findViewById(R.id.kd_dokter);
            txt_idnyadokter = itemView.findViewById(R.id.idnya_dokter);
            txt_kdbag = itemView.findViewById(R.id.kd_bag);
            txt_kdklinik = itemView.findViewById(R.id.kd_klinik);
            txt_nmklinik = itemView.findViewById(R.id.txt_namaklinik);
            txt_wktperiksa = itemView.findViewById(R.id.wkt_periksa);
            txt_tglPeriksa = itemView.findViewById(R.id.txt_tglPeriksa);
            txt_sFoto = itemView.findViewById(R.id.sFoto);
            img_Dokter = itemView.findViewById(R.id.img_dokter);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent i = new Intent(itemView.getContext(), DaftarPoli.class);
                    String urlFoto = txt_sFoto.getText().toString();
                    String kode_dokter = txt_kddokter.getText().toString();
                    String idnya_dokter = txt_idnyadokter.getText().toString();
                    String nama_pegawai = txt_namaDokter.getText().toString();
                    String kode_bagian = txt_kdbag.getText().toString();
                    String nama_bagian = txt_JenisPoli.getText().toString();
                    String kd_klinik = txt_kdklinik.getText().toString();
                    String nm_klinik = txt_nmklinik.getText().toString();
                    String durasi = txt_wktperiksa.getText().toString();
                    i.putExtra("urlFoto", urlFoto);
                    i.putExtra("kd_dokter", kode_dokter);
                    i.putExtra("idnya_dokter", idnya_dokter);
                    i.putExtra("nm_dokter", nama_pegawai);
                    i.putExtra("kd_bag", kode_bagian);
                    i.putExtra("nm_bag", nama_bagian);
                    i.putExtra("kd_klinik", kd_klinik);
                    i.putExtra("nm_klinik", nm_klinik);
                    i.putExtra("durasi", durasi);
                    itemView.getContext().startActivity(i);

                }
            });
        }
    }
}



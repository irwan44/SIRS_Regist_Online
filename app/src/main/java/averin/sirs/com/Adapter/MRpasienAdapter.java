package averin.sirs.com.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import averin.sirs.com.AntrianDetail;
import averin.sirs.com.DetailMR;
import averin.sirs.com.MRDetail;
import averin.sirs.com.Model.MRpasien;
import averin.sirs.com.R;

public class MRpasienAdapter extends RecyclerView.Adapter<MRpasienAdapter.MRpasienViewHolder> {
    private ArrayList<MRpasien> list;
    DateFormat outputFormat = new SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault());
    DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DateFormat outputwaktu = new SimpleDateFormat("HH:mm", Locale.getDefault());
    DateFormat inputwaktu = new SimpleDateFormat("HH:mm:ss");
    public MRpasienAdapter(ArrayList<MRpasien> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public MRpasienViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_mr, parent, false);
        return new MRpasienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MRpasienViewHolder holder, int position) {
        Date tgl = null;
        Date wkt = null;
        String tgl_daft, wkt_daft;

        holder.txt_idreg.setText((list.get(position).getId_regist()));
        holder.txt_kodeklinik.setText(list.get(position).getKode_klinik());
        holder.txt_tgldaftar.setText(list.get(position).getTgl_periksa());
        holder.txt_wktdaftar.setText(list.get(position).getJam_periksa());
        tgl_daft = holder.txt_tgldaftar.getText().toString();
        wkt_daft = holder.txt_wktdaftar.getText().toString();

        try {
            tgl = inputFormat.parse(tgl_daft);
            wkt = inputwaktu.parse(wkt_daft);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String tglKonvert = outputFormat.format(tgl);
        String jam_konvert = outputwaktu.format(wkt);

        holder.tv_namadokter.setText(list.get(position).getNama_dokter());
        holder.tv_namabagian.setText(list.get(position).getNama_bagian());
        holder.tv_tglperiksa.setText(tglKonvert+" "+jam_konvert);
//        holder.txt_namaKlinik.setText(list.get(position).getNama_klinik());

    }

    public class MRpasienViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_tgldaftar, txt_kodeklinik, txt_wktdaftar, txt_idreg,
                tv_namaklinik,tv_namadokter, tv_namabagian, tv_tglperiksa;

        public MRpasienViewHolder(View itemView) {
            super(itemView);
            txt_kodeklinik = (TextView) itemView.findViewById(R.id.txt_kodeklinik);
            txt_idreg = (TextView) itemView.findViewById(R.id.txt_idReg);
            txt_tgldaftar = (TextView) itemView.findViewById(R.id.txt_tgl_daftar);
            txt_wktdaftar = (TextView) itemView.findViewById(R.id.txt_wkt_daftar);

            tv_namadokter = (TextView) itemView.findViewById(R.id.txt_namaDokter);
            tv_namabagian = (TextView) itemView.findViewById(R.id.txt_namabagian);
            tv_tglperiksa = (TextView) itemView.findViewById(R.id.txt_tglPeriksa);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(itemView.getContext(), DetailMR.class);
                    String kde_klinik= txt_kodeklinik.getText().toString();
                    String idreg = txt_idreg.getText().toString();
//                    Put to parsing
                    i.putExtra("idRegKlinik", idreg);
                    i.putExtra("kd_klinik", kde_klinik);
                    itemView.getContext().startActivity(i);
                }
            });
        }
    }
}




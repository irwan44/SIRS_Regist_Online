package averin.sirs.com.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import averin.sirs.com.AntrianDetail;
import averin.sirs.com.MRDetail;
import averin.sirs.com.Model.MRpasien;
import averin.sirs.com.R;

public class MRpasienAdapter extends RecyclerView.Adapter<MRpasienAdapter.MRpasienViewHolder> {
    private ArrayList<MRpasien> list;
    private String keadaan_umum, tekanan_darah, suhu, tinggi_badan,
            kesadaran, nadi, pernafasan, bb, tindakan, nama_icd10, kd_resep;
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
//        holder.txt_idreg.setText((list.get(position).getIdreg()));
//        holder.txt_noktp.setText((list.get(position).getnoKTP()));
//        holder.txt_kdKlinik.setText((list.get(position).getKode_Klinik()));
        holder.txt_tglPeriksa.setText((list.get(position).getTgl_periksa()));
        holder.txt_tglPeriksa.setText((list.get(position).getTgl_periksa()));
        holder.txt_namaDokter.setText(list.get(position).getNama_dokter());
        holder.txt_JenisPoli.setText(list.get(position).getJenis_poli());
//        holder.txt_namaKlinik.setText(list.get(position).getNama_klinik());
        holder.txt_tglPeriksa.setText((list.get(position).getTgl_periksa()));
        keadaan_umum = list.get(position).getKeadaan_umum();
        tekanan_darah = list.get(position).getTekanan_darah();
        suhu = list.get(position).getSuhu();
        tinggi_badan = list.get(position).getTinggi_badan();
        kesadaran = list.get(position).getKesadaran();
        nadi = list.get(position).getNadi();
        pernafasan = list.get(position).getPernafasan();
        bb = list.get(position).getBb();
        tindakan = list.get(position).getTindakan();
        nama_icd10 = list.get(position).getNama_icd10();
        kd_resep = list.get(position).getKd_resep();

    }

    public class MRpasienViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_idreg, txt_noktp, txt_kdKlinik, txt_namaDokter,
                txt_JenisPoli, txt_namaKlinik, txt_tglPeriksa;

        public MRpasienViewHolder(View itemView) {
            super(itemView);
            txt_idreg = (TextView) itemView.findViewById(R.id.txt_idReg);
            txt_noktp = (TextView) itemView.findViewById(R.id.txt_noktp);
            txt_kdKlinik = (TextView) itemView.findViewById(R.id.txt_kdklinik);
            txt_namaDokter = (TextView) itemView.findViewById(R.id.namaDokter);
            txt_JenisPoli = (TextView) itemView.findViewById(R.id.jenisPoli);
            txt_tglPeriksa = (TextView) itemView.findViewById(R.id.tglPeriksa);
            txt_namaKlinik = (TextView) itemView.findViewById(R.id.namaKlinik);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(itemView.getContext(), MRDetail.class);
                    String namaDokter = txt_namaDokter.getText().toString();
                    String tglPeriksa = txt_tglPeriksa.getText().toString();
                    String nmPoli = txt_JenisPoli.getText().toString();
//                    Put to parsing
                    i.putExtra("nama_dokter", namaDokter);
                    i.putExtra("tgl_periksa", tglPeriksa);
                    i.putExtra("nama_poli", nmPoli);
                    i.putExtra("keadaan_umum", keadaan_umum);
                    i.putExtra("tekanan_darah", tekanan_darah);
                    i.putExtra("suhu", suhu);
                    i.putExtra("tinggi_badan", tinggi_badan);
                    i.putExtra("kesadaran", kesadaran);
                    i.putExtra("nadi", nadi);
                    i.putExtra("pernafasan", pernafasan);
                    i.putExtra("bb", bb);
                    i.putExtra("tindakan", tindakan);
                    i.putExtra("nama_icd10", nama_icd10);
                    i.putExtra("kd_resep", kd_resep);
                    itemView.getContext().startActivity(i);
                }
            });
        }
    }
}




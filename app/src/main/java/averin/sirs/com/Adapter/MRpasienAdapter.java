package averin.sirs.com.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import averin.sirs.com.AntrianDetail;
import averin.sirs.com.DetailMR;
import averin.sirs.com.MRDetail;
import averin.sirs.com.Model.MRpasien;
import averin.sirs.com.R;

public class MRpasienAdapter extends RecyclerView.Adapter<MRpasienAdapter.MRpasienViewHolder> {
    private ArrayList<MRpasien> list;
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
        holder.txt_tglPeriksa.setText(list.get(position).getTgl_periksa());
        holder.txt_tglPeriksa.setText(list.get(position).getTgl_periksa());
        holder.txt_namaDokter.setText(list.get(position).getNama_dokter());
        holder.txt_JenisPoli.setText(list.get(position).getJenis_poli());
//        holder.txt_namaKlinik.setText(list.get(position).getNama_klinik());
        holder.txt_tglPeriksa.setText(list.get(position).getTgl_periksa());
        holder.keadaan_umum.setText(list.get(position).getKeadaan_umum());
        holder.tekanan_darah.setText(list.get(position).getTekanan_darah());
        holder.suhu.setText(list.get(position).getSuhu());
        holder.tinggi_badan.setText(list.get(position).getTinggi_badan());
        holder.kesadaran.setText(list.get(position).getKesadaran());
        holder.nadi.setText(list.get(position).getNadi());
        holder.pernafasan.setText(list.get(position).getPernafasan());
        holder.bb.setText(list.get(position).getBb());
        holder.tindakan.setText(list.get(position).getTindakan());
        holder.nama_icd10.setText(list.get(position).getNama_icd10());
        holder.kd_resep.setText(list.get(position).getKd_resep());

    }

    public class MRpasienViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_idreg, txt_noktp, txt_kdKlinik, txt_namaDokter,
                txt_JenisPoli, txt_namaKlinik, txt_tglPeriksa, keadaan_umum, tekanan_darah, suhu, tinggi_badan,
                kesadaran, nadi, pernafasan, bb, tindakan, nama_icd10, kd_resep;

        public MRpasienViewHolder(View itemView) {
            super(itemView);
            txt_idreg = (TextView) itemView.findViewById(R.id.txt_idReg);
            txt_noktp = (TextView) itemView.findViewById(R.id.txt_noktp);
            txt_kdKlinik = (TextView) itemView.findViewById(R.id.txt_kdklinik);
            txt_namaDokter = (TextView) itemView.findViewById(R.id.namaDokter);
            txt_JenisPoli = (TextView) itemView.findViewById(R.id.jenisPoli);
            txt_tglPeriksa = (TextView) itemView.findViewById(R.id.tglPeriksa);
            txt_namaKlinik = (TextView) itemView.findViewById(R.id.namaKlinik);

            keadaan_umum = (TextView) itemView.findViewById(R.id.txt_keadaanumum);
            tekanan_darah = (TextView) itemView.findViewById(R.id.txt_TekananDarah);
            suhu = (TextView) itemView.findViewById(R.id.txt_suhu);
            tinggi_badan = (TextView) itemView.findViewById(R.id.txt_TinggiBadan);
            kesadaran = (TextView) itemView.findViewById(R.id.txt_Kesadaran);
            nadi = (TextView) itemView.findViewById(R.id.txt_Nadi);
            pernafasan = (TextView) itemView.findViewById(R.id.txt_Pernafasan);
            bb = (TextView) itemView.findViewById(R.id.txt_bb);
            tindakan = (TextView) itemView.findViewById(R.id.txt_tindakan);
            nama_icd10 = (TextView) itemView.findViewById(R.id.txt_icd10);
            kd_resep = (TextView) itemView.findViewById(R.id.txt_kdresep);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(itemView.getContext(), DetailMR.class);
                    String namaDokter = txt_namaDokter.getText().toString();
                    String tglPeriksa = txt_tglPeriksa.getText().toString();
                    String nmPoli = txt_JenisPoli.getText().toString();

                    String dtl_keadaanumum = keadaan_umum.getText().toString();
                    String dtl_tekanan_darah = tekanan_darah.getText().toString();
                    String dtl_suhu = suhu.getText().toString();
                    String dtl_tinggi = tinggi_badan.getText().toString();
                    String dtl_kesadaran = kesadaran.getText().toString();
                    String dtl_nadi = nadi.getText().toString();
                    String dtl_pernafasan = pernafasan.getText().toString();
                    String dtl_bb = bb.getText().toString();
                    String dtl_tindakan = tindakan.getText().toString();
                    String dtl_nama_icd10 = nama_icd10.getText().toString();
                    String dtl_kdresep = kd_resep.getText().toString();
//                    Put to parsing
                    i.putExtra("nama_dokter", namaDokter);
                    i.putExtra("tgl_periksa", tglPeriksa);
                    i.putExtra("nama_poli", nmPoli);
                    i.putExtra("keadaan_umum", dtl_keadaanumum);
                    i.putExtra("tekanan_darah", dtl_tekanan_darah);
                    i.putExtra("suhu", dtl_suhu);
                    i.putExtra("tinggi_badan", dtl_tinggi);
                    i.putExtra("kesadaran", dtl_kesadaran);
                    i.putExtra("nadi", dtl_nadi);
                    i.putExtra("pernafasan", dtl_pernafasan);
                    i.putExtra("bb", dtl_bb);
                    i.putExtra("tindakan", dtl_tindakan);
                    i.putExtra("nama_icd10", dtl_nama_icd10);
                    i.putExtra("kd_resep", dtl_kdresep);
                    itemView.getContext().startActivity(i);
                }
            });
        }
    }
}




package averin.sirs.com.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
        holder.txt_tglPeriksa.setText((list.get(position).getTgl_periksa()));
        holder.txt_tglPeriksa.setText((list.get(position).getTgl_periksa()));
        holder.txt_namaDokter.setText(list.get(position).getNama_dokter());
        holder.txt_JenisPoli.setText(list.get(position).getJenis_poli());
        holder.txt_namaKlinik.setText(list.get(position).getNama_klinik());
        holder.txt_tglPeriksa.setText((list.get(position).getTgl_periksa()));
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
        }
    }
}




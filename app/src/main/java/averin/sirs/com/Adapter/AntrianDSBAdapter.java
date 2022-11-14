package averin.sirs.com.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import averin.sirs.com.AntrianDetail;
import averin.sirs.com.MainActivity;
import averin.sirs.com.Model.Antrian;
import averin.sirs.com.R;

public class AntrianDSBAdapter extends RecyclerView.Adapter<AntrianDSBAdapter.AntrianDSBViewHolder> {
    private ArrayList<Antrian> list;


    public AntrianDSBAdapter(MainActivity ma, ArrayList<Antrian> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public AntrianDSBViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_antrian_homepage, parent, false);
        return new AntrianDSBViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AntrianDSBViewHolder holder, int position) {
        holder.txt_idReg.setText(list.get(position).getIdReg_antrian());
        holder.txt_noantrian.setText(list.get(position).getNo_antrian());
        holder.txt_tglAntrian.setText(list.get(position).getTgl_antrian());
        holder.txt_nmDokter.setText(list.get(position).getnmDokter_antrian());
        holder.txt_jamAwal.setText(list.get(position).getJamAwal_antrian());
        holder.txt_jamAkhir.setText(list.get(position).getJamAkhir_antrian());
        holder.txt_status.setText(list.get(position).getStatus_antrian());
        holder.txt_nmKlinik.setText(list.get(position).getnmKlinik_antrian());
        holder.txt_nmPoli.setText(list.get(position).getnmBagian_antrian());
    }

    public class AntrianDSBViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_idReg, txt_jamAkhir, txt_noantrian, txt_nmKlinik, txt_nmDokter,
                txt_nmPoli, txt_tglAntrian, txt_jamAwal, txt_status;

        public AntrianDSBViewHolder(View itemView) {
            super(itemView);
            txt_noantrian     = itemView.findViewById(R.id.txt_noantrian);
            txt_nmKlinik      = itemView.findViewById(R.id.txt_nmKlinik);
            txt_nmDokter      = itemView.findViewById(R.id.txt_nmDokter);
            txt_nmPoli        = itemView.findViewById(R.id.txt_nmPoli);
            txt_tglAntrian    = itemView.findViewById(R.id.txt_tglAntrian);
            txt_jamAwal       = itemView.findViewById(R.id.txt_jamAwal);
            txt_jamAkhir      = itemView.findViewById(R.id.txt_jamAkhir);
            txt_idReg         = itemView.findViewById(R.id.txt_idReg);
            txt_status         = itemView.findViewById(R.id.txt_status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(itemView.getContext(), AntrianDetail.class);
//                    Declarate String
                    String regId        = txt_idReg.getText().toString();
                    String no_antri     = txt_noantrian.getText().toString();
                    String nmaDokter    = txt_nmDokter.getText().toString();
                    String tgl_dtl      = txt_tglAntrian.getText().toString();
                    String jawal_dtl    = txt_jamAwal.getText().toString();
                    String jakhir_dtl   = txt_jamAkhir.getText().toString();
                    String status_dtl   = txt_status.getText().toString();
                    String nmKlinik_dtl = txt_nmKlinik.getText().toString();
                    String nmbagian_dtl = txt_nmPoli.getText().toString();
//                    Put to parsing
                    i.putExtra("regId", regId);
                    i.putExtra("noAntrian", no_antri);
                    i.putExtra("nma_dokter", nmaDokter);
                    i.putExtra("tgl_antri", tgl_dtl);
                    i.putExtra("jam_awal", jawal_dtl);
                    i.putExtra("jam_akhir", jakhir_dtl);
                    i.putExtra("status_antri", status_dtl);
                    i.putExtra("nm_klinik", nmKlinik_dtl);
                    i.putExtra("nm_bag", nmbagian_dtl);
                    itemView.getContext().startActivity(i);
                }
            });
        }
    }
}
package averin.sirs.com.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import averin.sirs.com.AntrianActivity;
import averin.sirs.com.AntrianDetail;
import averin.sirs.com.Model.Antrian;
import averin.sirs.com.R;

public class AntrianAdapter extends RecyclerView.Adapter<AntrianAdapter.AntrianViewHolder> {
    private ArrayList<Antrian> list;


    public AntrianAdapter(AntrianActivity antrianActivity, ArrayList<Antrian> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public AntrianViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_antrian, parent, false);
        return new AntrianAdapter.AntrianViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AntrianViewHolder holder, int position) {
        holder.idReg.setText(list.get(position).getIdReg_antrian());
        holder.no_antri.setText(list.get(position).getNo_antrian());
        holder.nm_dokter.setText(list.get(position).getnmDokter_antrian());
        holder.tglAntri.setText(list.get(position).getTgl_antrian());
        holder.jamAwal.setText(list.get(position).getJamAwal_antrian());
        holder.jamAkhir.setText(list.get(position).getJamAkhir_antrian());
        holder.status.setText(list.get(position).getStatus_antrian());
        holder.nmKlinik.setText(list.get(position).getnmKlinik_antrian());
//        holder.nmKlinik.setText(list.get(position).getnmDokter_antrian());
        holder.nmBagian.setText(list.get(position).getnmBagian_antrian());
        holder.ketKlinik.setText(list.get(position).getKet_antrian());
    }


    public class AntrianViewHolder extends RecyclerView.ViewHolder {
        private TextView tglAntri, nmKlinik, nmBagian, idReg, no_antri, nm_dokter, jamAwal, jamAkhir, status, ketKlinik;
//        private ImageView img_fotoPasien;

        public AntrianViewHolder(View itemView) {
            super(itemView);
            no_antri = itemView.findViewById(R.id.no_antrian);
            nm_dokter = itemView.findViewById(R.id.nm_Dokter);
            nmKlinik = itemView.findViewById(R.id.txt_nmDokter);
            nmBagian = itemView.findViewById(R.id.txt_nmPoli);
            tglAntri = itemView.findViewById(R.id.txt_tglAntrian);
            idReg    = itemView.findViewById(R.id.idReg);
            jamAwal  = itemView.findViewById(R.id.jamAwal);
            jamAkhir = itemView.findViewById(R.id.jamAkhir);
            status   = itemView.findViewById(R.id.status);
            ketKlinik   = itemView.findViewById(R.id.stat_px);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(itemView.getContext(), AntrianDetail.class);
//                    Declarate String
                    String regId = idReg.getText().toString();
//                    Put to parsing
                    i.putExtra("regId", regId);
                    itemView.getContext().startActivity(i);
                }
            });
        }
    }
}



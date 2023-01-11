package averin.sirs.com.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import averin.sirs.com.AntrianActivity;
import averin.sirs.com.AntrianDetail;
import averin.sirs.com.Model.Antrian;
import averin.sirs.com.Model.DokterPoli;
import averin.sirs.com.R;
import averin.sirs.com.RegistPasienLama;

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
        holder.nmBagian.setText(list.get(position).getnmBagian_antrian());
    }


    public class AntrianViewHolder extends RecyclerView.ViewHolder {
        private TextView tglAntri, nmKlinik, nmBagian, idReg, no_antri, nm_dokter, jamAwal, jamAkhir, status;
//        private ImageView img_fotoPasien;

        public AntrianViewHolder(View itemView) {
            super(itemView);
            no_antri = itemView.findViewById(R.id.no_antrian);
            nm_dokter = itemView.findViewById(R.id.nm_Dokter);
            nmKlinik = itemView.findViewById(R.id.txt_nmKlinik);
            nmBagian = itemView.findViewById(R.id.txt_nmPoli);
            tglAntri = itemView.findViewById(R.id.txt_tglAntrian);
            idReg    = itemView.findViewById(R.id.idReg);
            jamAwal  = itemView.findViewById(R.id.jamAwal);
            jamAkhir = itemView.findViewById(R.id.jamAkhir);
            status   = itemView.findViewById(R.id.status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(itemView.getContext(), AntrianDetail.class);
//                    Declarate String
                    String regId        = idReg.getText().toString();
                    String no_antrian   = no_antri.getText().toString();
                    String nmDokter     = nm_dokter.getText().toString();
                    String tgl_dtl      = tglAntri.getText().toString();
                    String jawal_dtl    = jamAwal.getText().toString();
                    String jakhir_dtl   = jamAkhir.getText().toString();
                    String status_dtl   = status.getText().toString();
                    String nmKlinik_dtl = nmKlinik.getText().toString();
                    String nmbagian_dtl = nmBagian.getText().toString();
//                    Put to parsing
                    i.putExtra("regId", regId);
                    i.putExtra("noAntrian", no_antrian);
                    i.putExtra("nma_dokter", nmDokter);
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



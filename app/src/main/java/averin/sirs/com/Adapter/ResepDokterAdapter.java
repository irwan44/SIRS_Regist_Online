package averin.sirs.com.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import averin.sirs.com.Model.ResepObat;
import averin.sirs.com.Model.isiSpinner;
import averin.sirs.com.R;

public class ResepDokterAdapter extends RecyclerView.Adapter<ResepDokterAdapter.ResepDokterViewHolder> {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ResepObat> list;

    public ResepDokterAdapter(Activity activity, List<ResepObat> list) {
        this.activity = activity;
        this.list = list;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public ResepDokterAdapter.ResepDokterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_resep_dokter, parent, false);
        return new ResepDokterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResepDokterAdapter.ResepDokterViewHolder holder, int position) {

        holder.nama_obat.setText(list.get(position).getNamaobat());
        holder.jumlah_obat.setText(list.get(position).getJumlahobat());
        holder.note_obat.setText(list.get(position).getNoteobat());
        holder.aturan_pakai.setText((list.get(position).getNamadosis()));
        holder.ket_obat.setText(list.get(position).getKetobat());

    }

    public class ResepDokterViewHolder extends RecyclerView.ViewHolder {
        private TextView nama_obat, jumlah_obat, note_obat, aturan_pakai, ket_obat;

        public ResepDokterViewHolder(View itemView) {
            super(itemView);
            nama_obat = (TextView) itemView.findViewById(R.id.nmobat);
            jumlah_obat = (TextView) itemView.findViewById(R.id.jmlobat);
            note_obat = (TextView) itemView.findViewById(R.id.noteobat);
            aturan_pakai = (TextView) itemView.findViewById(R.id.aturanpakai);
            ket_obat =  (TextView) itemView.findViewById(R.id.ketobat);

        }
    }
}

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

public class ResepDokterAdapter extends RecyclerView.Adapter<ResepDokterAdapter.MyView> {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ResepObat> list;

//    public ResepDokterAdapter(Activity activity, List<ResepObat> list) {
//        this.activity = activity;
//        this.list = list;
//    }
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    @Override
//    public ResepDokterAdapter.ResepDokterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View view = layoutInflater.inflate(R.layout.list_item_resep_dokter, parent, false);
//        return new ResepDokterViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ResepDokterAdapter.ResepDokterViewHolder holder, int position) {
//
//        holder.nama_obat.setText(list..getNamaobat());
//        holder.jumlah_obat.setText(list..getJumlahobat());
//        holder.note_obat.setText(list..getNoteobat());
//        holder.aturan_pakai.setText((list..getNamadosis()));
//        holder.ket_obat.setText(list..getKetobat());
//
//    }
//
//    public class ResepDokterViewHolder extends RecyclerView.ViewHolder {
//        private TextView nama_obat, jumlah_obat, note_obat, aturan_pakai, ket_obat;
//
//        public ResepDokterViewHolder(View itemView) {
//            super(itemView);
//            nama_obat = (TextView) itemView.findViewById(R.id.nmobat);
//            jumlah_obat = (TextView) itemView.findViewById(R.id.jmlobat);
//            note_obat = (TextView) itemView.findViewById(R.id.noteobat);
//            aturan_pakai = (TextView) itemView.findViewById(R.id.aturanpakai);
//            ket_obat =  (TextView) itemView.findViewById(R.id.ketobat);
//
//        }
//    }

    public class MyView extends RecyclerView.ViewHolder {
        private TextView nama_obat, jumlah_obat, note_obat, aturan_pakai, ket_obat;

        public MyView(View view) {
            super(view);

            nama_obat = (TextView) itemView.findViewById(R.id.nmobat);
            jumlah_obat = (TextView) itemView.findViewById(R.id.jmlobat);
            note_obat = (TextView) itemView.findViewById(R.id.noteobat);
            aturan_pakai = (TextView) itemView.findViewById(R.id.aturanpakai);
            ket_obat =  (TextView) itemView.findViewById(R.id.ketobat);
        }
    }

    public ResepDokterAdapter(List<ResepObat> horizontalList)
    {
        this.list = horizontalList;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_resep_dokter, parent, false);

        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        ResepObat data;
        data = list.get(position);
        holder.nama_obat.setText(data.getNamaobat());
        holder.jumlah_obat.setText(data.getJumlahobat());
        holder.note_obat.setText(data.getNoteobat());
        holder.aturan_pakai.setText((data.getNamadosis()));
        holder.ket_obat.setText(data.getKetobat());
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}

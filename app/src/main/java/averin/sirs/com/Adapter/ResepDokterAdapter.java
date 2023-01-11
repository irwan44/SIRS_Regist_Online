package averin.sirs.com.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import averin.sirs.com.Model.ResepObat;
import averin.sirs.com.Model.isiSpinner;
import averin.sirs.com.R;

public class ResepDokterAdapter extends RecyclerView.Adapter<ResepDokterAdapter.MyView> {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ResepObat> list;

    public class MyView extends RecyclerView.ViewHolder {
        private TextView nourut, nama_obat, jumlah_obat, note_obat, aturan_pakai, ket_obat;

        public MyView(View view) {
            super(view);

            nourut = (TextView) itemView.findViewById(R.id.nourut);
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
        holder.nourut.setText(data.getNourut());
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

package averin.sirs.com.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import averin.sirs.com.Model.CariDokter;
import averin.sirs.com.R;

public class CariDokterAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<CariDokter> item;

    public CariDokterAdapter(Activity activity, List<CariDokter> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int location) {
        return item.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_dokter_klinik, null);

        TextView namadokter = (TextView) convertView.findViewById(R.id.txt_result);
        String nama_dokter = item.get(position).getnamaDokter();
        namadokter.setText(nama_dokter);

        return convertView;
    }
}

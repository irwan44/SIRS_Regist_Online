package averin.sirs.com.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import averin.sirs.com.DaftarPoli;
import averin.sirs.com.Model.CariDokter;
import averin.sirs.com.Model.DokterPoli;
import averin.sirs.com.R;

public class CariDokterAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<CariDokter> item;
    private String strFoto;
    private byte[] byt_Foto;

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
    public View getView(int position, View itemView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        if (convertView == null)
//            convertView = inflater.inflate(R.layout.list_item_dokter_poli, null);

        if (itemView == null)
            itemView = inflater.inflate(R.layout.list_dokter_klinik, null);

        TextView namadokter = (TextView) itemView.findViewById(R.id.txt_result);
        String nama_dokter = item.get(position).getnamaDokter();
        namadokter.setText(nama_dokter);

//        TextView txt_namaDokter = (TextView) itemView.findViewById(R.id.namaDokter);
//        TextView txt_JenisPoli = (TextView) itemView.findViewById(R.id.txtJenisPoli);
//        TextView txt_kddokter = (TextView) itemView.findViewById(R.id.kd_dokter);
//        TextView txt_idnyadokter = (TextView) itemView.findViewById(R.id.idnya_dokter);
//        TextView txt_kdbag = (TextView) itemView.findViewById(R.id.kd_bag);
//        TextView txt_kdklinik = (TextView) itemView.findViewById(R.id.kd_klinik);
//        TextView txt_nmklinik = (TextView) itemView.findViewById(R.id.nm_klinik);
//        TextView txt_wktperiksa = (TextView) itemView.findViewById(R.id.wkt_periksa);
//        TextView txt_jamPeriksa = (TextView) itemView.findViewById(R.id.jamPeriksa);
//        TextView txt_sFoto = (TextView) itemView.findViewById(R.id.sFoto);
//        ImageView img_Dokter = (ImageView) itemView.findViewById(R.id.img_Dokter);

        //SET TEXT TO LIST ITEM
//        txt_namaDokter.setText(item.get(position).getNama_dokter());
//        txt_JenisPoli.setText(item.get(position).getBagian());
//        txt_kddokter.setText(item.get(position).getKode_dokter());
//        txt_idnyadokter.setText(item.get(position).getIdnya_dokter());
//        txt_kdbag.setText(item.get(position).getKode_bag());
//        txt_kdklinik.setText(item.get(position).getKd_klinik());
//        txt_nmklinik.setText(item.get(position).getNm_klinik());
//        txt_wktperiksa.setText(item.get(position).getWaktu_periksa());
//        txt_jamPeriksa.setText(item.get(position).getJam_mulai()+" - "+item.get(position).getJam_akhir());
//        txt_sFoto.setText(item.get(position).getFoto_dokter());
//        strFoto = txt_sFoto.getText().toString();
//        if (strFoto.equals("")) {
//            img_Dokter.setImageResource(R.drawable.ic__01_boy);
//        } else {
//            byt_Foto = Base64.decode(strFoto, Base64.DEFAULT);
//            Bitmap bitmapDokter = BitmapFactory.decodeByteArray(byt_Foto, 0, byt_Foto.length);
//            img_Dokter.setImageBitmap(bitmapDokter);
//        }
        return itemView;
    }
}

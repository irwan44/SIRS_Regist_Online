package averin.sirs.com.Model;

public class MRpasien {
    private String namaKlinik, namaDokter, jenisPoli, tglPeriksa, keadaan_umum, tekanan_darah, suhu, tinggi_badan,
    kesadaran, nadi, pernafasan, bb, tindakan, nama_icd10, kd_resep;

    public MRpasien(String namaDokter, String jenisPoli, String tglPeriksa, String keadaan_umum, String tekanan_darah,
                    String suhu, String tinggi_badan, String kesadaran, String nadi, String pernafasan, String bb, String tindakan, String nama_icd10,
                    String kd_resep) {
        this.namaDokter = namaDokter;
        this.jenisPoli = jenisPoli;
//        this.namaKlinik = namaKlinik;
        this.tglPeriksa = tglPeriksa;
        this.keadaan_umum = keadaan_umum;
        this.tekanan_darah = tekanan_darah;
        this.suhu = suhu;
        this.tinggi_badan = tinggi_badan;
        this.kesadaran = kesadaran;
        this.nadi = nadi;
        this.pernafasan = pernafasan;
        this.bb = bb;
        this.tindakan = tindakan;
        this.nama_icd10 = nama_icd10;
        this.kd_resep = kd_resep;
    }

    public String getNama_dokter() {
        return namaDokter;
    }
    public String getJenis_poli() {
        return jenisPoli;
    }
    public String getTgl_periksa() {
        return tglPeriksa;
    }
//    public String getNama_klinik() {
//        return namaKlinik;
//    }
    public String getKeadaan_umum() {
        return keadaan_umum;
    }
    public String getTekanan_darah() {
        return tekanan_darah;
    }
    public String getSuhu() {
        return suhu;
    }
    public String getTinggi_badan() {
        return tinggi_badan;
    }
    public String getKesadaran() {
        return kesadaran;
    }
    public String getNadi() {
        return nadi;
    }
    public String getPernafasan() {
        return pernafasan;
    }
    public String getBb() {
        return bb;
    }
    public String getTindakan() {
        return tindakan;
    }
    public String getNama_icd10() {
        return nama_icd10;
    }
    public String getKd_resep() {
        return kd_resep;
    }


}
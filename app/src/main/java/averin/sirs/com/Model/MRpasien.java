package averin.sirs.com.Model;

public class MRpasien {
    private String namaKlinik, namaDokter, jenisPoli, tglPeriksa;

    public MRpasien(String namaKlinik, String namaDokter, String jenisPoli, String tglPeriksa) {
        this.namaDokter = namaDokter;
        this.jenisPoli = jenisPoli;
        this.namaKlinik = namaKlinik;
        this.tglPeriksa = tglPeriksa;
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

    public String getNama_klinik() {
        return namaKlinik;
    }

    public void setNama_dokter(String namaDokter) {
        this.namaDokter = namaDokter;
    }

    public void setJenis_poli(String jenisPoli) {
        this.jenisPoli = jenisPoli;
    }

    public void setTgl_periksa(String tglPeriksa) {
        this.tglPeriksa = tglPeriksa;
    }

    public void setNama_klinik(String namaKlinik) {
        this.namaKlinik = namaKlinik;
    }
}
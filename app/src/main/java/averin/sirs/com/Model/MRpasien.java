package averin.sirs.com.Model;

public class MRpasien {
    private String idReg, kd_klinik, nama_klinik, nama_dokter, nama_bagian, tgl_periksa, jam_periksa, umur_px, gender_px, goldarah_px;

    public MRpasien(String idReg, String kd_klinik, String nama_klinik, String nama_dokter, String nama_bagian,
                    String tgl_periksa, String jam_periksa, String gender_px, String umur_px, String goldarah_px) {
        this.idReg = idReg;
        this.kd_klinik = kd_klinik;
        this.nama_klinik = nama_klinik;
        this.nama_dokter = nama_dokter;
        this.nama_bagian = nama_bagian;
        this.tgl_periksa = tgl_periksa;
        this.jam_periksa = jam_periksa;

        this.gender_px = gender_px;
        this.umur_px = umur_px;
        this.goldarah_px = goldarah_px;
    }
    public String getId_regist() { return  idReg;}
    public String getKode_klinik() { return kd_klinik;}
    public String getNama_klinik() { return nama_klinik;}
    public String getNama_dokter() {
        return nama_dokter;
    }
    public String getNama_bagian() {
        return nama_bagian;
    }
    public String getTgl_periksa() {
        return tgl_periksa;
    }
    public String getJam_periksa() {
        return jam_periksa;
    }

    public String getGender_px() {
        return gender_px;
    }
    public String getUmur_px() {
        return umur_px;
    }
    public String getGoldarah_px() {
        return goldarah_px;
    }


}
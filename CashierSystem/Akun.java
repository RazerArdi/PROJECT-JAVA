package CashierSystem;

class Akun {
    private String nama;
    private String alamat;
    private String nomorTelepon;
    private String itemTerakhirDibeli;

    public Akun(String nama, String alamat, String nomorTelepon, String itemTerakhirDibeli) {
        this.nama = nama;
        this.alamat = alamat;
        this.nomorTelepon = nomorTelepon;
        this.itemTerakhirDibeli = itemTerakhirDibeli;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public String getItemTerakhirDibeli() {
        return itemTerakhirDibeli;
    }

    public void setItemTerakhirDibeli(String itemTerakhirDibeli) {
        this.itemTerakhirDibeli = itemTerakhirDibeli;
    }
}

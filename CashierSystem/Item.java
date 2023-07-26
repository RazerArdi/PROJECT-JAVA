package CashierSystem;

class Item {
    private String nama;
    private int harga;
    private int stock;

    public Item(String nama, int harga, int stock) {
        this.nama = nama;
        this.harga = harga;
        this.stock = stock;
    }

    public String getNama() {
        return nama;
    }

    public int getHarga() {
        return harga;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}

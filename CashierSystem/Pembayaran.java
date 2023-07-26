package CashierSystem;

import java.util.List;

class Pembayaran {
    public static void generateStruk(List<Item> items, int totalHarga, String metodePembayaran) {
        System.out.println("=== Struk Pembayaran ===");
        System.out.println("Nama Toko: Toko XYZ");
        System.out.println("Detail Pembelian:");
        for (Item item : items) {
            System.out.println("- " + item.getNama() + ": Rp" + item.getHarga());
        }
        System.out.println("Total Harga: Rp" + totalHarga);
        System.out.println("Metode Pembayaran: " + metodePembayaran);
        System.out.println("=======================");
    }
}

package CashierSystem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Kasir {
    private static final String AKUN_FILE = "akun.txt";
    private static final String RIWAYAT_FILE = "riwayat.txt";

    private List<Akun> akunList;
    private List<String> riwayatPembelian;
    private List<Gorengan> gorengan;
    private List<Milkshake> milkshake;
    private List<Desserts> desserts;
    private List<Beer> beer;

    public Kasir() {
        akunList = new ArrayList<>();
        riwayatPembelian = new ArrayList<>();
        gorengan = new ArrayList<>();
        milkshake = new ArrayList<>();
        desserts = new ArrayList<>();
        beer = new ArrayList<>();
        loadAkunData();
        loadRiwayatPembelian();
        initItems();
    }

    public void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Selamat Datang ===");
        System.out.println("Pilih opsi:");
        System.out.println("1. Login");
        System.out.println("2. Registrasi");
        System.out.print("Pilihan Anda: ");
        int pilihan = scanner.nextInt();

        switch (pilihan) {
            case 1:
                System.out.println("=== Login ===");
                System.out.print("Username: ");
                String username = scanner.next();
                System.out.print("Password: ");
                String password = scanner.next();

                if (username.equals("admin") && password.equals("admin")) {
                    adminMenu();
                } else {
                    Akun akun = findAkunByUsername(username);
                    if (akun != null && akun.getNama().equals(username)) {
                        costumerMenu(akun);
                    } else {
                        System.out.println("Login gagal. Username atau password salah.");
                    }
                }
                break;
            case 2:
                System.out.println("=== Registrasi ===");
                System.out.print("Nama: ");
                String nama = scanner.next();
                System.out.print("Alamat: ");
                String alamat = scanner.next();
                System.out.print("Nomor Telepon: ");
                String nomorTelepon = scanner.next();

                Akun akunBaru = new Akun(nama, alamat, nomorTelepon, "");
                akunList.add(akunBaru);
                saveAkunData();
                costumerMenu(akunBaru);
                break;
            default:
                System.out.println("Pilihan tidak valid.");
                break;
        }
    }

    private void adminMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Menu Admin ===");
        System.out.println("Pilih opsi:");
        System.out.println("1. Ubah Jumlah Stock");
        System.out.println("0. Logout");
        System.out.print("Pilihan Anda: ");
        int pilihan = scanner.nextInt();

        switch (pilihan) {
            case 1:
                System.out.println("=== Ubah Jumlah Stock ===");
                System.out.println("Pilih jenis item:");
                System.out.println("1. Gorengan");
                System.out.println("2. Milkshake");
                System.out.println("3. Desserts");
                System.out.println("4. Beer");
                System.out.print("Pilihan Anda: ");
                int jenisItem = scanner.nextInt();

                switch (jenisItem) {
                    case 1:
                        ubahJumlahStock(gorengan);
                        break;
                    case 2:
                        ubahJumlahStock(milkshake);
                        break;
                    case 3:
                        ubahJumlahStock(desserts);
                        break;
                    case 4:
                        ubahJumlahStock(beer);
                        break;
                    default:
                        System.out.println("Pilihan tidak valid.");
                        break;
                }
                break;
            case 0:
                System.out.println("Logout berhasil.");
                login();
                break;
            default:
                System.out.println("Pilihan tidak valid.");
                break;
        }
    }

    private void ubahJumlahStock(List<? extends Item> items) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Ubah Jumlah Stock ===");
        System.out.println("Pilih item:");
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            System.out.println((i + 1) + ". " + item.getNama() + " (Stock: " + item.getStock() + ")");
        }
        System.out.print("Pilihan Anda: ");
        int pilihan = scanner.nextInt();

        if (pilihan > 0 && pilihan <= items.size()) {
            Item item = items.get(pilihan - 1);
            System.out.print("Jumlah Stock Baru: ");
            int jumlahStockBaru = scanner.nextInt();
            item.setStock(jumlahStockBaru);
            saveRiwayatPembelian();
            System.out.println("Jumlah stock berhasil diubah.");
        } else {
            System.out.println("Pilihan tidak valid.");
        }
    }

    private void costumerMenu(Akun akun) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Menu Costumer ===");
        System.out.println("Pilih opsi:");
        System.out.println("1. Beli Item");
        System.out.println("0. Logout");
        System.out.print("Pilihan Anda: ");
        int pilihan = scanner.nextInt();

        switch (pilihan) {
            case 1:
                System.out.println("=== Beli Item ===");
                System.out.println("Pilih jenis item:");
                System.out.println("1. Gorengan");
                System.out.println("2. Milkshake");
                System.out.println("3. Desserts");
                System.out.println("4. Beer");
                System.out.print("Pilihan Anda: ");
                int jenisItem = scanner.nextInt();

                switch (jenisItem) {
                    case 1:
                        beliItem(gorengan, akun);
                        break;
                    case 2:
                        beliItem(milkshake, akun);
                        break;
                    case 3:
                        beliItem(desserts, akun);
                        break;
                    case 4:
                        beliItem(beer, akun);
                        break;
                    default:
                        System.out.println("Pilihan tidak valid.");
                        break;
                }
                break;
            case 0:
                System.out.println("Logout berhasil.");
                login();
                break;
            default:
                System.out.println("Pilihan tidak valid.");
                break;
        }
    }

    private void beliItem(List<? extends Item> items, Akun akun) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Beli Item ===");
        System.out.println("Pilih item:");
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            System.out.println((i + 1) + ". " + item.getNama() + " (Harga: Rp" + item.getHarga() + ", Stock: " + item.getStock() + ")");
        }
        System.out.print("Pilihan Anda: ");
        int pilihan = scanner.nextInt();

        if (pilihan > 0 && pilihan <= items.size()) {
            Item item = items.get(pilihan - 1);
            if (item.getStock() > 0) {
                List<Item> pembelian = new ArrayList<>();
                pembelian.add(item);
                int totalHarga = item.getHarga();

                System.out.print("Metode Pembayaran (Cash/Card): ");
                String metodePembayaran = scanner.next();

                System.out.print("Feedback: ");
                String feedback = scanner.next();

                System.out.print("Pilih (Take Away/Dine-in): ");
                String pilihanTakeAway = scanner.next();
                String nomorMeja = "";

                if (pilihanTakeAway.equalsIgnoreCase("Dine-in")) {
                    System.out.print("Pilih Meja (1-15): ");
                    nomorMeja = scanner.next();
                }

                item.setStock(item.getStock() - 1);
                saveRiwayatPembelian();
                Pembayaran.generateStruk(pembelian, totalHarga, metodePembayaran);

                akun.setItemTerakhirDibeli(item.getNama());
                saveAkunData();

                System.out.println("Pembelian berhasil.");
            } else {
                System.out.println("Stock item habis.");
            }
        } else {
            System.out.println("Pilihan tidak valid.");
        }
    }

    private void initItems() {
        gorengan.add(new Gorengan("Pisang Goreng", 5000, 10));
        gorengan.add(new Gorengan("Kentang Goreng", 7000, 15));
        gorengan.add(new Gorengan("Tahu Bakso pedas", 6000, 8));

        milkshake.add(new Milkshake("Coklat", 10000, 12));
        milkshake.add(new Milkshake("Red Velvet", 12000, 10));
        milkshake.add(new Milkshake("Banana-Vanila", 11000, 8));

        desserts.add(new Desserts("Gelato", 15000, 6));
        desserts.add(new Desserts("Biskvitena Torta", 18000, 5));
        desserts.add(new Desserts("VolenBin", 16000, 7));

        beer.add(new Beer("MixPower", 20000, 10));
        beer.add(new Beer("Whiskey", 25000, 8));
        beer.add(new Beer("Vodka", 22000, 12));
    }

    private Akun findAkunByUsername(String username) {
        for (Akun akun : akunList) {
            if (akun.getNama().equals(username)) {
                return akun;
            }
        }
        return null;
    }

    private void loadAkunData() {
        try {
            FileReader fileReader = new FileReader(AKUN_FILE);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                String nama = data[0];
                String alamat = data[1];
                String nomorTelepon = data[2];
                String itemTerakhirDibeli = data[3];

                Akun akun = new Akun(nama, alamat, nomorTelepon, itemTerakhirDibeli);
                akunList.add(akun);
            }

            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Gagal memuat data akun.");
        }
    }

    private void saveAkunData() {
        try {
            FileWriter fileWriter = new FileWriter(AKUN_FILE);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Akun akun : akunList) {
                bufferedWriter.write(akun.getNama() + "," + akun.getAlamat() + "," + akun.getNomorTelepon() + "," + akun.getItemTerakhirDibeli());
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data akun.");
        }
    }

    private void loadRiwayatPembelian() {
        try {
            FileReader fileReader = new FileReader(RIWAYAT_FILE);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                riwayatPembelian.add(line);
            }

            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Gagal memuat riwayat pembelian.");
        }
    }

    private void saveRiwayatPembelian() {
        try {
            FileWriter fileWriter = new FileWriter(RIWAYAT_FILE);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (String pembelian : riwayatPembelian) {
                bufferedWriter.write(pembelian);
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Gagal menyimpan riwayat pembelian.");
        }
    }
}
